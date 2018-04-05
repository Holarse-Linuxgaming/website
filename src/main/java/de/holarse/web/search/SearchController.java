package de.holarse.web.search;

import de.holarse.backend.db.repositories.SearchRepository;
import de.holarse.services.TrafficService;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriUtils;

@Controller
public class SearchController {
    
    Logger logger = LoggerFactory.getLogger(SearchController.class);
    
    @Autowired
    SearchRepository searchRepository;
    
    @Autowired
    TrafficService trafficService;

    @PostMapping("/search")
    public RedirectView searchResult(@ModelAttribute final SearchCommand command, final Model map, HttpServletRequest req) throws UnsupportedEncodingException {
        return new RedirectView("/search?q=" + URLEncoder.encode(command.getQuery(), "ISO-8859-1"));
    }
    
    @GetMapping("/search")
    public String searchUrl(@RequestParam("q") final String query, final Model map, final HttpServletRequest req) throws UnsupportedEncodingException {
        final String decodedQuery = URLDecoder.decode(query, "ISO-8859-1");
        map.addAttribute("results", searchRepository.search(decodedQuery));
        map.addAttribute("query", decodedQuery);
        return "search/result";
    }    
    
    
}
