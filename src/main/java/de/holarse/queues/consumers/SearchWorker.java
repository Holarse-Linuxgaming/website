package de.holarse.queues.consumers;

import de.holarse.backend.db.repositories.SearchRepository;
import de.holarse.queues.commands.SearchRefresh;
import static de.holarse.config.JmsQueueTypes.QUEUE_SEARCH;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class SearchWorker {
    
    private final static transient Logger log = LoggerFactory.getLogger(SearchWorker.class);
    
    @Autowired
    private SearchRepository searchRepository;
    
    @JmsListener(destination = QUEUE_SEARCH)
    public void importDrückblickEntries(final SearchRefresh cmd) {
        log.debug("Incoming Command to Refresh Materialized View");
        searchRepository.refreshIndex();
    }    
}
