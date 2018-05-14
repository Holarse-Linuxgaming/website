package de.holarse.web.search;

import de.holarse.search.SearchEngine;
import de.holarse.services.TrafficService;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SearchController {
    
    Logger logger = LoggerFactory.getLogger(SearchController.class);
    
    @Autowired
    SearchEngine searchEngine;
    
    @Autowired
    TrafficService trafficService;

    @PostMapping("/search")
    public RedirectView searchResult(@ModelAttribute final SearchCommand command, final Model map, final HttpServletRequest req) throws UnsupportedEncodingException {
        return new RedirectView("/search?q=" + URLEncoder.encode(command.getQuery(), "UTF-8"), false, false, false);
    }
    
    @GetMapping("/search")
    public String searchUrl(@RequestParam("q") final String query, final Model map, final HttpServletRequest req) throws UnsupportedEncodingException {
        final String decodedQuery = URLDecoder.decode(query, "UTF-8");
        map.addAttribute("results", searchEngine.search(decodedQuery));
        map.addAttribute("query", decodedQuery);
        return "search/result";
    }    
    
}
