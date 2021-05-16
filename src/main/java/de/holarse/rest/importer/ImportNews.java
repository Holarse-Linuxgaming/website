package de.holarse.rest.importer;

import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.Job;
import de.holarse.backend.db.types.ContentType;
import de.holarse.backend.db.News;
import de.holarse.backend.db.types.NewsCategory;
import de.holarse.backend.db.NodeType;
import de.holarse.backend.db.repositories.AttachmentRepository;
import de.holarse.backend.db.repositories.JobRepository;
import de.holarse.backend.db.repositories.NewsRepository;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.db.types.AttachmentDataType;
import de.holarse.backend.db.types.AttachmentGroup;
import de.holarse.backend.db.types.AttachmentType;
import de.holarse.backend.db.types.NewsType;
import de.holarse.backend.db.types.QueueWorkerType;
import de.holarse.search.SearchEngine;
import de.holarse.services.NodeService;
import de.holarse.services.TagService;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping("/api/import/news")
public class ImportNews {
    
    Logger log = LoggerFactory.getLogger(ImportNews.class);
    
    @Autowired
    JobRepository jobRepository;    
    
    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> upload(@RequestBody final de.holarse.backend.export.News importNews) throws Exception {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(importNews);
        
        final Job job = new Job();
        job.setCreated(OffsetDateTime.now());
        job.setPayload(out.toByteArray());
        job.setWorker(QueueWorkerType.IMPORT);
        job.setDetails("NEWS");
        
        jobRepository.saveAndFlush(job);
        
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }
    
    
}
