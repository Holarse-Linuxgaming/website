package de.holarse.rest.importer;

import de.holarse.backend.db.Job;
import de.holarse.backend.db.repositories.JobRepository;
import de.holarse.rest.JobService;
import de.holarse.workers.JobQueueContext;
import de.holarse.workers.JobConfiguration;
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

@Secured({"ROLE_API_DRÜCKBLICK", "ROLE_API_ADMIN"})
@RestController
@RequestMapping("/api/drückblick/")
public class DrückblickEndpoint {
    
    Logger log = LoggerFactory.getLogger(DrückblickEndpoint.class);    
    
    @Autowired
    JobRepository jobRepository;
    
    @Autowired
    JobService jobService;
    
    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> upload(@RequestBody final de.holarse.backend.api.drückblick.DrückblickEntry importItem) throws Exception {
        final Job job = jobService.prepareForJob(importItem, JobQueueContext.DRÜCKBLICK.toString().toLowerCase());
        jobRepository.save(job);
        
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
       
    
}
