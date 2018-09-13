package de.holarse.rest.importer;

import de.holarse.backend.db.repositories.ArticleRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/import/articles")
public class ImportArticles {
 
    Logger log = LoggerFactory.getLogger(ImportArticles.class);
    
    @Autowired ArticleRepository ar;
    
    @GetMapping(name = "", produces = MediaType.APPLICATION_XML_VALUE)
    public List<String> all() {
        final List<String> data = new ArrayList<>();
        ar.findAll().forEach(a -> data.add(a.getTitle()));
        
        return data;
    }
    
    @PostMapping(name = "", consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> upload(@RequestBody de.holarse.entity.importer.Article articleImport) {
        log.debug(articleImport.getTitles().get(0).getTitle());
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }
    
}
