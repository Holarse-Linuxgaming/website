package de.holarse.rest.importer;

import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.ContentType;
import de.holarse.backend.db.News;
import de.holarse.backend.db.NewsCategory;
import de.holarse.backend.db.NodeType;
import de.holarse.backend.db.repositories.AttachmentRepository;
import de.holarse.backend.db.repositories.NewsRepository;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.db.types.AttachmentDataType;
import de.holarse.backend.db.types.AttachmentGroup;
import de.holarse.backend.db.types.AttachmentType;
import de.holarse.search.SearchEngine;
import de.holarse.services.NodeService;
import de.holarse.services.TagService;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Secured({"ROLE_API_IMPORT", "ROLE_API_ADMIN"})
@RestController
@RequestMapping("/api/import/news")
public class ImportNews {
    
    Logger log = LoggerFactory.getLogger(ImportNews.class);
    
    @Autowired 
    NewsRepository nr;
    
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
    SearchEngine searchEngine;     
    
    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> upload(@RequestBody final de.holarse.backend.export.News importNews) throws Exception {
        // Bstehenden Drupal-Import finden oder neuen anlegen
        final News node = nr.findByOldId(importNews.getUid()).orElseGet(() -> new News());
        // Im Drupal gab es noch keine multiplen Titel       
        node.setCreated(OffsetDateTime.ofInstant(importNews.getCreated().toInstant(), ZoneOffset.UTC));
        
        node.setTitle(importNews.getTitle());
        node.setContent(importNews.getContent().getValue());
        node.setContentType(ContentType.WIKI);
        node.setOldId(importNews.getUid());
        node.setBranch("master");
        node.setCategory(NewsCategory.valueOf(importNews.getCategory()));
        
        node.setAuthor(ur.findByLogin(importNews.getRevision().getAuthor()));
                
        de.holarse.backend.export.State importState = importNews.getState();
        
        node.setDeleted(importState.getDeleted());
        node.setDraft(importState.getDraft());
        node.setArchived(importState.getArchived());
        node.setLocked(importState.getLocked());
        node.setPublished(importState.getPublished());
        node.setCommentable(importState.getCommentable());

        // Slug-Erstellung
        if (node.getSlug() == null) {
            node.setSlug(nodeService.findNextSlug(node.getTitle(), NodeType.NEWS));
        }

        // Attachments
        attr.deleteByNodeId(node.getNodeId());
        
        if (importNews.getAttachments() != null) {
            List<Attachment> attachments = new ArrayList<>();
            for (de.holarse.backend.export.Attachment importAttachment : importNews.getAttachments()) {
                final Attachment attachment = new Attachment();
                attachment.setNodeId(node.getNodeId());
                attachment.setAttachmentGroup(AttachmentGroup.valueOf(importAttachment.getGroup()));
                attachment.setAttachmentType(AttachmentType.lookup(importAttachment.getType(), importAttachment.getGroup()));
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
        nr.save(node);        
        
        // Such-Update
        searchEngine.update(node);        
        
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }
    
    
}
