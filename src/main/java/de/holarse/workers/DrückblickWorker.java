package de.holarse.workers;

import de.holarse.backend.db.DrückblickEntry;
import de.holarse.backend.db.Job;
import de.holarse.backend.db.repositories.DrückblickRepository;
import de.holarse.backend.db.repositories.JobRepository;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.OffsetDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author comrad
 */
@Component
public class DrückblickWorker {
    
    private final static transient Logger log = LoggerFactory.getLogger(UserWorker.class);
    
    @Autowired
    JobRepository jobRepository;
    
    @Autowired
    DrückblickRepository drückblickRepository;
    
    @Scheduled(fixedRate = 10000)
    public void importDrückblickEntries() {
        final List<Job> jobs = jobRepository.findOpenJobs(JobConfiguration.IMPORT_QUEUE, JobQueueContext.DRÜCKBLICK.toString().toLowerCase());
        for (final Job job : jobs) {
            log.debug("Working on Job#{}", job);
            try {
                final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(job.getData()));
                de.holarse.backend.api.drückblick.DrückblickEntry entry_u = (de.holarse.backend.api.drückblick.DrückblickEntry) ois.readObject();
                
                DrückblickEntry entry = new DrückblickEntry();
                entry.setName(entry_u.getName());
                entry.setReporter(entry_u.getReporter());
                entry.setMessage(entry_u.getMessage());
                entry.setSource(entry_u.getSource());
                entry.setUrl(entry_u.getUrl());
                
                drückblickRepository.saveAndFlush(entry);
                
                job.setCompleted(true);
                jobRepository.save(job);                
            } catch (Exception ex) {
                log.error("Error on import job #{}", job.getId(), ex);
                job.setTries(job.getTries() + 1);
                job.setUpdated(OffsetDateTime.now());
                
                jobRepository.save(job);                
            }
        }
    }
    
}
