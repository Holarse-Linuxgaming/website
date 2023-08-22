package de.holarse.api.drückblick;

import de.holarse.backend.db.DrückblickEntry;
import de.holarse.backend.db.Job;
import de.holarse.backend.db.repositories.DrückblickRepository;
import de.holarse.backend.db.repositories.JobRepository;
import de.holarse.web.services.JobService;
import de.holarse.workers.JobQueueContext;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
public class Drückblick {
    
    Logger log = LoggerFactory.getLogger(Drückblick.class);    
    
    @Autowired
    JobRepository jobRepository;
    
    @Autowired
    JobService jobService;
    
    @Autowired
    DrückblickRepository dblRepo;
    
    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> upload(@Valid @RequestBody final de.holarse.backend.api.drückblick.DrückblickEntry importItem) throws Exception {
        final Job job = jobService.prepareForJob(importItem, JobQueueContext.DRÜCKBLICK.toString().toLowerCase());
        jobRepository.save(job);
        
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
           
    
}
