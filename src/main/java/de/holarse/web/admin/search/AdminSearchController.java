package de.holarse.web.admin.search;

import de.holarse.backend.db.NodeType;
import de.holarse.backend.db.Searchable;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.NewsRepository;
import de.holarse.search.SearchEngine;
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
    @Qualifier("postgres")
    SearchEngine searchEngine;

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

        searchEngine.update(searchable);
        
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }    
    
}
