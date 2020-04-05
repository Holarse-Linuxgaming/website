package de.holarse.rest.drueckblick;

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

import de.holarse.backend.db.DrueckblickEntry;
import de.holarse.backend.db.repositories.DrueckblickEntryRepository;
import de.holarse.backend.db.types.DrueckblickCategory;

@Secured({ "ROLE_API_DRUECKBLICK", "ROLE_API_ADMIN" })
@RestController
@RequestMapping("/api/drueckblick/")
public class DrueckblickEndpoint {

    Logger log = LoggerFactory.getLogger(DrueckblickEndpoint.class);    
  
    @Autowired
    private DrueckblickEntryRepository dblRepository;
    
    @Transactional
    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> upload(@RequestBody final String line) throws Exception {
        // Erster Schritt, erstmal einfach speichern
        DrueckblickEntry entry = new DrueckblickEntry();
        entry.setCreated(OffsetDateTime.now());
        entry.setCategory(DrueckblickCategory.UNASSIGNED);
        entry.setMessage(line);

        dblRepository.save(entry);

        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }

}