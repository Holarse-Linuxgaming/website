package de.holarse.rest.importer;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.types.AttachmentDataType;
import de.holarse.backend.db.ContentType;
import de.holarse.backend.db.NodeType;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.AttachmentRepository;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.backend.db.types.AttachmentGroup;
import de.holarse.backend.db.types.AttachmentType;
import de.holarse.search.SearchEngine;
import de.holarse.services.NodeService;
import de.holarse.services.TagService;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/import/articles")
public class ImportArticles {
 
    Logger log = LoggerFactory.getLogger(ImportArticles.class);
    
    @Autowired 
    ArticleRepository ar;

    @Autowired 
    TagRepository tr;
    
    @Autowired
    AttachmentRepository attr;
    
    @Autowired 
    TagService tagService;
    
    @Autowired
    NodeService nodeService;
    
    @Autowired
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
        // Bstehenden Drupal-Import finden oder neuen anlegen
        final Article article = ar.findByOldId(importArticle.getUid()).orElseGet(() -> new Article());
        // Im Drupal gab es noch keine multiplen Titel       
        article.setCreated(OffsetDateTime.ofInstant(importArticle.getCreated().toInstant(), ZoneOffset.UTC));
        
        article.setTitle(importArticle.getTitles().get(0).getValue());
        article.setContent(importArticle.getContent().getValue());
        article.setContentType(ContentType.WIKI);
        article.setOldId(importArticle.getUid());
        article.setBranch("master");
                
        de.holarse.backend.export.State importState = importArticle.getState();
        
        article.setDeleted(importState.getDeleted());
        article.setDraft(importState.getDraft());
        article.setArchived(importState.getArchived());
        article.setLocked(importState.getLocked());
        article.setPublished(importState.getPublished());
        article.setCommentable(importState.getCommentable());

        // Tags
        Set<Tag> tags = tagService.listToTags(importArticle.getTags());
        tr.saveAll(tags);

        article.getTags().clear();
        article.getTags().addAll(tags);

        // Slug-Erstellung
        article.setSlug(nodeService.findNextSlug(article.getTitle(), NodeType.ARTICLE));

        ar.save(article);
        
        // Attachments
        List<Attachment> attachments = new ArrayList<>();
        for (de.holarse.backend.export.Attachment importAttachment : importArticle.getAttachments()) {
            final Attachment attachment = new Attachment();
            attachment.setNodeId(article.getNodeId());
            attachment.setAttachmentGroup(AttachmentGroup.valueOf(importAttachment.getGroup()));
            attachment.setAttachmentType(AttachmentType.lookup(importAttachment.getType(), importAttachment.getGroup()));
            attachment.setAttachmentDataType(AttachmentDataType.URI);
            attachment.setCreated(OffsetDateTime.now());
            attachment.setOrdering(importAttachment.getPrio() != null ? importAttachment.getPrio() : 0l );
            attachment.setAttachmentData(importAttachment.getValue());
            
            attachments.add(attachment);
        }        
        attr.saveAll(attachments);  // Über die NodeID sind die dann direkt verbunden
        
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
