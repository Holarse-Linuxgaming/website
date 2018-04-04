package de.holarse.web.search;

import de.holarse.backend.db.repositories.SearchRepository;
import de.holarse.services.TrafficService;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SearchController {
    
    @Autowired
    SearchRepository searchRepository;
    
    @Autowired
    TrafficService trafficService;
            
    @GetMapping("/search")
    public String searchForm(final Model map, final SearchCommand command) {
        map.addAttribute("searchCommand", command);
        
        return "search/form";
    }

    @PostMapping("/search")
    public RedirectView searchResult(@ModelAttribute final SearchCommand command, final Model map, HttpServletRequest req) throws UnsupportedEncodingException {
        return new RedirectView("/search/" + URLEncoder.encode(command.getQuery(), StandardCharsets.UTF_8.toString()));
    }
    
    @GetMapping("/search/{query}")
    public String searchUrl(@PathVariable("query") final String query, final Model map, HttpServletRequest req) {
        trafficService.saveSearchRequest(req, query);        
        map.addAttribute("results", searchRepository.search(query));
        map.addAttribute("query", query);
        return "search/result";
    }    
    
    
}
