package de.holarse.web.search;

import de.holarse.backend.views.SearchResultView;
import de.holarse.backend.views.SearchResultsView;
import de.holarse.search.SearchEngine;
import de.holarse.services.TrafficService;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping("/search")
@Controller
public class SearchController {
    
    Logger logger = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    @Qualifier("pgsql")            
    SearchEngine searchEngine;
    
    @Autowired
    TrafficService trafficService;

    // Suchanfrage entgegennehmen
    @PostMapping
    public RedirectView searchResult(@ModelAttribute @Valid final SearchCommand command, final Model map, final HttpServletRequest req) throws UnsupportedEncodingException {
        return new RedirectView("/search?q=" + URLEncoder.encode(command.getQuery(), "UTF-8"), false, false, false);
    }
    
    // Als Get verlinkbar machen
    @GetMapping
    public String searchUrl(@RequestParam("q") final String query, final Model map, final HttpServletRequest req) throws UnsupportedEncodingException {
        final SearchResultsView view = new SearchResultsView(query);
        
        final String decodedQuery = URLDecoder.decode(query, "UTF-8");
        final List<SearchResultView> results = searchEngine.search(decodedQuery).stream().map(s -> new SearchResultView(s)).collect(Collectors.toList());
        view.getResults().addAll(results);

        map.addAttribute("q", decodedQuery);
        
        map.addAttribute("view", view);
        return "search/result";
    }
    
    @GetMapping("suggest.json")
    public @ResponseBody List<Suggestion> suggestion(@RequestParam("term") final String query)
    {
        return searchEngine.search(query)
                .stream().map(r -> new Suggestion(r.getUrl(), "", r.getTitle(), r.getContent(), "Artikel"))
                .collect(Collectors.toList());
    }
}
