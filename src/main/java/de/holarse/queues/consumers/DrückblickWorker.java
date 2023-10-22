package de.holarse.queues.consumers;

import de.holarse.backend.db.DrückblickEntry;
import de.holarse.backend.db.repositories.DrückblickRepository;
import static de.holarse.config.JmsQueueTypes.QUEUE_DRÜCKBLICK;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author comrad
 */
@Component
public class DrückblickWorker {
    
    private final static transient Logger log = LoggerFactory.getLogger(UserWorker.class);
    
    @Autowired
    private DrückblickRepository drückblickRepository;
    
    @JmsListener(destination = QUEUE_DRÜCKBLICK)
    public void importDrückblickEntries(final de.holarse.backend.api.drückblick.DrückblickEntry entry_u) {
        log.debug("Incoming Drückblick via Queue: {}", entry_u);
        try {
            DrückblickEntry entry = new DrückblickEntry();
            entry.setName(entry_u.getName());
            entry.setReporter(entry_u.getReporter());
            entry.setMessage(entry_u.getMessage());
            entry.setSource(entry_u.getSource());
            entry.setUrl(entry_u.getUrl());

            drückblickRepository.saveAndFlush(entry);
        } catch (Exception ex) {
            log.error("Error on import", ex);
        }
    }
    
}
