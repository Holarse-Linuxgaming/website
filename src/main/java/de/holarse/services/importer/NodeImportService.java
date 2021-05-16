/*
 * Copyright (C) 2021 comrad
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.holarse.services.importer;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.Job;
import de.holarse.backend.db.News;
import de.holarse.backend.db.NodeType;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.AttachmentRepository;
import de.holarse.backend.db.repositories.JobRepository;
import de.holarse.backend.db.repositories.NewsRepository;
import de.holarse.backend.db.repositories.RoleRepository;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.db.types.AttachmentDataType;
import de.holarse.backend.db.types.AttachmentGroup;
import de.holarse.backend.db.types.ContentType;
import de.holarse.backend.db.types.NewsCategory;
import de.holarse.backend.db.types.NewsType;
import de.holarse.backend.db.types.PasswordType;
import de.holarse.backend.db.types.QueueWorkerType;
import de.holarse.backend.export.Title;
import de.holarse.backend.export.TitleType;
import de.holarse.search.SearchEngine;
import de.holarse.services.NodeService;
import de.holarse.services.TagService;
import de.holarse.services.WebUtils;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author comrad
 */
@Service
public class NodeImportService {
    
    @Autowired 
    NewsRepository nr;
    
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
    private RoleRepository rr;    
    
    @Autowired
    @Qualifier("pgsql")            
    SearchEngine searchEngine;   
    
    @Autowired
    JobRepository jobRepository;    
    
    @Transactional    
    public void doImport(final de.holarse.backend.export.Article importArticle) throws Exception {
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
    }
    
    @Transactional    
    public void doImport(final de.holarse.backend.export.News importNews) throws Exception {
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
        node.setNewsType(NewsType.valueOf(importNews.getNewsType()));
        
        node.setAuthor(ur.findByLogin(importNews.getRevision().getAuthor()));
                
        de.holarse.backend.export.State importState = importNews.getState();
        
        node.setDeleted(importState.getDeleted());
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
    }
    
    @Transactional
    public void doImport(final de.holarse.backend.export.User importUser) throws Exception {
        // Bstehenden Drupal-Import finden oder neuen anlegen
        final User user  = ur.findByOldId(importUser.getUid()).orElseGet(() -> new User());
        user.setCreated(OffsetDateTime.ofInstant(importUser.getCreated().toInstant(), ZoneOffset.UTC));
        
        user.setLogin(importUser.getLogin());        
        user.setEmail(importUser.getEmail());
        user.setPasswordType(PasswordType.valueOf(importUser.getPassword().getType()));
        user.setDigest(importUser.getPassword().getDigest());
        
        user.setSignature(importUser.getSignature());
        user.setAvatar(importUser.getAvatar());
        user.setVerified(true); // Automatisch verifiziert durch den Import
        user.setLocked(importUser.isLocked());
        user.setOldId(importUser.getUid());
        user.setSlug(WebUtils.slugify(importUser.getLogin()));
        
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        } else {
            user.getRoles().clear();    
        }
        
        if (importUser.getRoles() != null) {
            user.setRoles( importUser.getRoles().stream().map(r -> rr.findByCodeIgnoreCase(r.getValue()))
                                                         .filter(Optional::isPresent)
                                                         .map(Optional::get)
                                                         .collect(Collectors.toSet()) );
        }
        ur.save(user);
    }     
}
