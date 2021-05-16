package de.holarse.rest.importer;

import de.holarse.backend.db.Job;
import de.holarse.backend.db.repositories.JobRepository;
import de.holarse.backend.db.types.QueueWorkerType;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.time.OffsetDateTime;
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

@Secured({"ROLE_API_IMPORT", "ROLE_API_ADMIN"})
@RestController
@RequestMapping("/api/import/users")
public class ImportUsers {
    
    Logger log = LoggerFactory.getLogger(ImportUsers.class);    
    
    @Autowired
    JobRepository jobRepository;
    
    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> upload(@RequestBody final de.holarse.backend.export.User importUser) throws Exception {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(importUser);
        
        final Job job = new Job();
        job.setCreated(OffsetDateTime.now());
        job.setPayload(out.toByteArray());
        job.setWorker(QueueWorkerType.IMPORT);
        job.setDetails("USER");
        
        jobRepository.saveAndFlush(job);
        
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }    
    
}
