package de.holarse.queues.consumers;

import de.holarse.backend.db.News;
import de.holarse.backend.db.NewsRevision;
import de.holarse.backend.db.NodeSlug;
import de.holarse.backend.db.NodeStatus;
import de.holarse.backend.db.repositories.NewsRepository;
import de.holarse.backend.db.repositories.NewsRevisionRepository;
import static de.holarse.config.JmsQueueTypes.*;
import de.holarse.web.services.SlugService;
import de.holarse.web.services.TagService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;

@Component
public class NewsImportWorker {

    private final static transient Logger log = LoggerFactory.getLogger(NewsImportWorker.class);

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    NewsRevisionRepository newsRevisionRepository;

    @Autowired
    SlugService slugService;
    
    @Autowired
    private TagService tagService;    

    @Transactional
    @JmsListener(destination = QUEUE_IMPORTS_NEWS)
    public void importNews(final de.holarse.backend.api.News queueEntry) {
        log.info("Import News '{}'", queueEntry.getTitle());

        int nodeId = 0;
        int revision = 0;

        // Bei Drupal-Import übernehmen wir die Ids aus Drupal
        // Die neuen Ids fangen ab 10000 an und können so unterschieden werden.
        if (queueEntry.getUid() != null) {
            nodeId = queueEntry.getUid().intValue();
        } else {
            nodeId = newsRepository.nextNodeId();
        }
        if (queueEntry.getVid() != null) {
            revision = queueEntry.getVid().intValue();
        } else {
            revision = newsRevisionRepository.nextRevision();
        }
        
        final OffsetDateTime created = OffsetDateTime.ofInstant(queueEntry.getCreated().toInstant(), ZoneId.systemDefault());
       
        final NewsRevision newsRevision = new NewsRevision();
        newsRevision.setTitle(queueEntry.getTitle());
        newsRevision.setContent(queueEntry.getContent().getValue());
        newsRevision.setCreated(created);
        newsRevision.setNodeId(nodeId);
        newsRevision.setRevision(revision);
        newsRevisionRepository.saveAndFlush(newsRevision);

        NodeStatus nodeStatus = new NodeStatus();
        nodeStatus.setPublished(true);
        nodeStatus.setNodeId(nodeId);
        nodeStatus.setCreated(created);

        // Slug anlegen
        final NodeSlug nodeSlug = slugService.slugify(newsRevision);

        News news = new News();
        news.setNodeId(nodeId);
        news.setNodeStatus(nodeStatus);
        news.setNodeRevision(newsRevision);
        news.setDrupalId(queueEntry.getUid().intValue());
        news.getNodeSlugz().add(nodeSlug);
        newsRepository.saveAndFlush(news);
    }
}