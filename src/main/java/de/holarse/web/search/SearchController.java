package de.holarse.web.search;

import de.holarse.backend.db.repositories.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SearchController {
    
    @Autowired
    SearchRepository searchRepository;
    
    @GetMapping("/search")
    public String searchForm(final Model map, final SearchCommand command) {
        map.addAttribute("searchCommand", command);
        
        return "search/form";
    }
    
    @GetMapping("/search/{query}")
    public String searchUrl(@PathVariable("query") final String query, final Model map) {
        map.addAttribute("results", searchRepository.search(query));
        
        return "search/result";
    }    
    
    @PostMapping("/search")
    public String searchResult(@ModelAttribute final SearchCommand command, final Model map) {
        map.addAttribute("results", searchRepository.search(command.getQuery()));
        return "search/result";
    }
    
}
