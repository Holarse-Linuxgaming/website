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
package de.holarse.jobs;

import de.holarse.backend.db.Job;
import de.holarse.backend.db.types.QueueWorkerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import de.holarse.backend.db.repositories.JobRepository;
import de.holarse.services.importer.NodeImportService;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author comrad
 */
@Service
public class QueueJobManager {

    final static Logger logger = LoggerFactory.getLogger(QueueJobManager.class);    
    
    @Autowired
    JobRepository jobRepository;
    
    @Autowired
    NodeImportService nodeImportService;
    
    @Transactional
    @Scheduled(fixedDelay = 1000)
    public void workImportEntries() {
        for (final Job job : jobRepository.getJobs(QueueWorkerType.IMPORT)) {
            logger.debug("Job #{}: Importing {} (fails: {})", new Object[]{job.getId(), job.getDetails(), job.getFails()});
            try {            
                final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(job.getPayload()));    

                switch (job.getDetails()) {
                    case "ARTICLE":
                        final de.holarse.backend.export.Article article = (de.holarse.backend.export.Article) ois.readObject();
                        nodeImportService.doImport(article);                    
                        break;
                    case "NEWS":
                        final de.holarse.backend.export.News news = (de.holarse.backend.export.News) ois.readObject();
                        nodeImportService.doImport(news);                                            
                        break;
                    case "USER": 
                        final de.holarse.backend.export.User user = (de.holarse.backend.export.User) ois.readObject();
                        nodeImportService.doImport(user);                                            
                        break;
                    default:
                }
            } catch (final Exception ex) {
                logger.error("Job #{} has failed: ", job.getId(), ex);
                job.incrementFail();
                jobRepository.save(job);
            }
        }
    }
    
}
