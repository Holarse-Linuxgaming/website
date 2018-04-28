package de.holarse.web.search;

import de.holarse.backend.db.repositories.SearchRepository;
import de.holarse.services.TrafficService;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.StringJoiner;
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
    SearchRepository searchRepository;
    
    @Autowired
    TrafficService trafficService;

    @PostMapping("/search")
    public RedirectView searchResult(@ModelAttribute final SearchCommand command, final Model map, final HttpServletRequest req) throws UnsupportedEncodingException {
        return new RedirectView("/search?q=" + URLEncoder.encode(command.getQuery(), "UTF-8"), false, false, false);
    }
    
    @GetMapping("/search")
    public String searchUrl(@RequestParam("q") final String query, final Model map, final HttpServletRequest req) throws UnsupportedEncodingException {
        final String decodedQuery = URLDecoder.decode(query, "UTF-8");
        map.addAttribute("results", searchRepository.search(breakAndJoin(decodedQuery)));
        map.addAttribute("query", decodedQuery);
        return "search/result";
    }    
    
    /**
     * Bricht ein Query auseinander und verbindet es mit &
     * @param query
     * @return 
     */
    protected String breakAndJoin(final String query) {
        final StringJoiner sj = new StringJoiner("&");
        
        for (final String q : query.split(" ")) {
            sj.add(q);
        }
        
        return sj.toString();
    }
    
}
