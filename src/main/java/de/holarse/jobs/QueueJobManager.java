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
    
    @Transactional
    @Scheduled(fixedDelay = 1000)
    public void workImportEntries() {
        for (final Job job : jobRepository.getJobs(QueueWorkerType.IMPORT)) {
            switch(job.getDetails()) {
                case "ARTCILE": break;
                case "NEWS": break;
                case "USERS": break;
                default:
                    logger.error("unhandled job detail for import: " + job.getDetails());
                    job.incrementFail();
                    jobRepository.save(job);
            }
        }
    }
    
}
