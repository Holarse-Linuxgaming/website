package de.holarse.queues.consumers;

import de.holarse.backend.api.Title;
import de.holarse.backend.db.Article;
import de.holarse.backend.db.ArticleRevision;
import de.holarse.backend.db.NodeSlug;
import de.holarse.backend.db.NodeStatus;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.ArticleRevisionRepository;
import de.holarse.backend.db.repositories.RoleRepository;
import de.holarse.backend.db.repositories.UserRepository;
import static de.holarse.config.JmsQueueTypes.*;
import de.holarse.web.services.SlugService;
import de.holarse.web.services.TagService;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ArticleImportWorker {

    private final static transient Logger log = LoggerFactory.getLogger(ArticleImportWorker.class);

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleRevisionRepository articleRevisionRepository;

    @Autowired
    SlugService slugService;
    
    @Autowired
    private TagService tagService;    

    @Transactional
    @JmsListener(destination = QUEUE_IMPORTS_ARTICLES)
    public void importArticles(final de.holarse.backend.api.Article queueEntry) {
        log.info("Import Article {}", queueEntry.getTitles().stream().map(t -> t.getValue()).collect(Collectors.joining(", ")));

        int nodeId = articleRepository.nextNodeId();
        int revision = articleRevisionRepository.nextRevision();
        
        final ArticleRevision articleRevision = new ArticleRevision();
        int i = 0;
        for (final Title title : queueEntry.getTitles()) {
            i++;
            switch (i) {
                case 1 -> articleRevision.setTitle1(title.getValue());
                case 2 -> articleRevision.setTitle2(title.getValue());
                case 3 -> articleRevision.setTitle3(title.getValue());
                case 4 -> articleRevision.setTitle4(title.getValue());
                case 5 -> articleRevision.setTitle5(title.getValue());
                case 6 -> articleRevision.setTitle6(title.getValue());
                case 7 -> articleRevision.setTitle7(title.getValue());
                default -> log.warn("Too many titles (>7) in this article, ignoring the rest");
            }
        }
        
        articleRevision.setTitle1Lang("english");
        articleRevision.setTitle2Lang("english");
        articleRevision.setTitle3Lang("english");
        articleRevision.setTitle4Lang("english");
        articleRevision.setTitle5Lang("english");
        articleRevision.setTitle6Lang("english");
        articleRevision.setTitle7Lang("english");
        
        articleRevision.setContent(queueEntry.getContent().getValue());
        articleRevision.setNodeId(nodeId);
        articleRevision.setRevision(revision);
        articleRevision.setChangelog(queueEntry.getRevision().getChangelog());
        articleRevisionRepository.saveAndFlush(articleRevision);
        
        final NodeStatus nodeStatus = new NodeStatus();
        nodeStatus.setNodeId(nodeId);
        nodeStatus.setPublished(true);

        // Slug anlegen
        final NodeSlug nodeSlug = slugService.slugify(articleRevision);        
            
        final Set<Tag> tags = tagService.extract(queueEntry);
        
        final Article article = new Article();
        article.setDrupalId(queueEntry.getUid().intValue());
        article.setNodeId(nodeId);
        article.setNodeRevision(articleRevision);
        article.setNodeStatus(nodeStatus);
        article.getNodeSlugs().add(nodeSlug);
        article.setTags(tags);        
        articleRepository.saveAndFlush(article);
    }    
        


}
