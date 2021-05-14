package de.holarse.rest.importer;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.types.AttachmentDataType;
import de.holarse.backend.db.types.ContentType;
import de.holarse.backend.db.NodeType;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.AttachmentRepository;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.db.types.AttachmentGroup;
import de.holarse.backend.export.Title;
import de.holarse.backend.export.TitleType;
import de.holarse.search.SearchEngine;
import de.holarse.services.NodeService;
import de.holarse.services.TagService;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Secured({"ROLE_API_IMPORT", "ROLE_API_ADMIN"})
@RestController
@RequestMapping("/api/import/articles")
public class ImportArticles {
 
    Logger log = LoggerFactory.getLogger(ImportArticles.class);
    
    @Autowired 
    ArticleRepository ar;
    
    @Autowired
    UserRepository ur;

    @Autowired 
    TagRepository tr;
    
    @Autowired
    AttachmentRepository attr;
    
    @Autowired 
    TagService tagService;
    
    @Autowired
    NodeService nodeService;
    
    @Autowired
    @Qualifier("pgsql")            
    SearchEngine searchEngine;   

    
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public List<String> all() {
        final List<String> data = new ArrayList<>();
        ar.findAll().forEach(a -> data.add(a.getTitle()));

        return data;
    }

    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> upload(@RequestBody final de.holarse.backend.export.Article importArticle) throws Exception {
        // TODO: in einen eigenen Service und dann in die Queue-Verarbeitung verschieben
        
        // Bstehenden Drupal-Import finden oder neuen anlegen
        final Article article = ar.findByOldId(importArticle.getUid()).orElseGet(() -> new Article());
        // Im Drupal gab es noch keine multiplen Titel       
        article.setCreated(OffsetDateTime.ofInstant(importArticle.getCreated().toInstant(), ZoneOffset.UTC));
     
        // Haupttitel
        final Title mainTitle = importArticle.getTitles().stream()
                                                          .filter(t -> t.getType().equalsIgnoreCase(TitleType.MAIN) && StringUtils.isNotBlank(t.getValue()))
                                                          .findFirst()
                                                          .orElseThrow(() -> new IllegalArgumentException("Kein Main-Title vorhanden"));
        article.setTitle(mainTitle.getValue());
        
        // Gesetze Alternativtitel raussuchen
        final List<String> alternativeTitles = importArticle.getTitles().stream()
                                                                        .filter(t -> t.getType().equalsIgnoreCase(TitleType.ALTERNATIVE) && StringUtils.isNotBlank(t.getValue()))
                                                                        .map(t -> t.getValue())
                                                                        .collect(Collectors.toList());
        
        // Alternativtitel setzen, aber nicht mehr als 3
        for (int i=0; i < Math.min(3, alternativeTitles.size()); i++) {
            switch (i) {
                case 0:
                    article.setAlternativeTitle1(alternativeTitles.get(i));
                    break;
                case 1:
                    article.setAlternativeTitle2(alternativeTitles.get(i));
                    break;
                case 2:
                    article.setAlternativeTitle3(alternativeTitles.get(i));
                    break;                    
            }
        }
        
        article.setContent(importArticle.getContent().getValue());
        article.setContentType(ContentType.WIKI);
        article.setOldId(importArticle.getUid());
        article.setBranch("master");
        
        article.setAuthor(ur.findByLogin(importArticle.getRevision().getAuthor()));
                
        de.holarse.backend.export.State importState = importArticle.getState();
        
        article.setDeleted(importState.getDeleted());
        article.setArchived(importState.getArchived());
        article.setLocked(importState.getLocked());
        article.setPublished(importState.getPublished());
        article.setCommentable(importState.getCommentable());

        // Tags
        final Set<Tag> tags = tagService.listToTags(importArticle.getTags());
        tr.saveAll(tags);

        article.getTags().clear();
        article.getTags().addAll(tags);

        // Slug-Erstellung
        if (article.getSlug() == null) {
            article.setSlug(nodeService.findNextSlug(article.getTitle(), NodeType.ARTICLE));
        }

        // Attachments
        attr.deleteByNodeId(article.getNodeId());
        
        if (importArticle.getAttachments() != null) {
            final List<Attachment> attachments = new ArrayList<>();
            for (de.holarse.backend.export.Attachment importAttachment : importArticle.getAttachments()) {
                final Attachment attachment = new Attachment();
                attachment.setNodeId(article.getNodeId());
                attachment.setAttachmentGroup(AttachmentGroup.valueOf(importAttachment.getGroup()));
                attachment.setAttachmentDataType(AttachmentDataType.URI);
                attachment.setCreated(OffsetDateTime.now());
                attachment.setOrdering(importAttachment.getPrio() != null ? importAttachment.getPrio() : 0l );
                attachment.setAttachmentData(importAttachment.getContent());
                attachment.setDescription(importAttachment.getDescription());

                attachments.add(attachment);
            } 
            //article.setAttachments(attachments);
            attr.saveAll(attachments);  // Über die NodeID sind die dann direkt verbunden
        }
        ar.save(article);        
        
        // Such-Update
        searchEngine.update(article);        
        
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }
    
//    @GetMapping(path = "export", produces = MediaType.APPLICATION_XML_VALUE)
//    public ResponseEntity<de.holarse.backend.export.Article> export() {
//        de.holarse.backend.export.Article a = new de.holarse.backend.export.Article();
//        de.holarse.backend.export.Title t = new de.holarse.backend.export.Title();
//        t.setType("MAIN");
//        t.setValue(("Hallo Titel"));
//        a.getTitles().add(t);
//        
//        de.holarse.backend.export.Content c = new de.holarse.backend.export.Content();
//        c.setFormat("WIKI");
//        c.setValue("Ich bin ein Text.");
//        a.setContent(c);
//        
//        de.holarse.backend.export.State s = new de.holarse.backend.export.State();
//        s.setArchived(Boolean.TRUE);
//        s.setDeleted(Boolean.FALSE);
//        s.setLocked(Boolean.TRUE);
//        a.setState(s);
//        
//        de.holarse.backend.export.Tag x = new de.holarse.backend.export.Tag();
//        x.setValue("native");
//        
//        de.holarse.backend.export.Tag y = new de.holarse.backend.export.Tag();
//        y.setValue("Spiele");        
//
//        a.getTags().add(x);
//        a.getTags().add(y);
//        
//        return new ResponseEntity<>(a, HttpStatus.CREATED);
//    }
//    
//    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
//    public ResponseEntity<String> upload(@RequestBody Example example) {
//        log.debug(example.toString());
//        log.debug(example.getName());
//        return new ResponseEntity<>("OK", HttpStatus.CREATED);
//    }    
    
}
