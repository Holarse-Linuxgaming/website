package de.holarse.web.admin.search;

import de.holarse.backend.db.News;
import de.holarse.backend.db.NodeType;
import de.holarse.backend.db.Searchable;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.NewsRepository;
import de.holarse.search.SearchEngine;
import java.io.IOException;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/search")
public class AdminSearchController {

    Logger logger = LoggerFactory.getLogger(AdminSearchController.class);

    @Autowired NewsRepository newsRepository;
    @Autowired ArticleRepository articleRepository;
    
    @Autowired
    @Qualifier("es")            
    SearchEngine searchEngine;

    @GetMapping("reindex")
    public ResponseEntity<String> reindex() throws IOException {
        final Iterable<News> news = newsRepository.findAll();
        searchEngine.update(news);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @Transactional
    @GetMapping("reindex/{nodeType}/{nodeId}")
    public ResponseEntity<String> reindex(@PathVariable("nodeType") final NodeType nodeType, @PathVariable("nodeId") final Long nodeId, final Model map) {
        final Searchable searchable;
        switch (nodeType) {
            case NEWS:
                searchable = newsRepository.findById(nodeId).get();
                break;
            case ARTICLE:
                searchable = articleRepository.findById(nodeId).get();
                break;
            default:
                throw new IllegalStateException("Unbehandelter NodeType " + nodeType);
        }

        try {
            Hibernate.initialize(searchable.getComments());
            Hibernate.initialize(searchable.getTags());
            searchEngine.update(searchable);
        } catch (IOException e) {
            logger.warn("Aktualisieren des Suchindexes fehlgeschlagen", e);
        }
        
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }    
    
}
