package de.holarse.web.controller;

import de.holarse.web.controller.commands.SearchForm;
import de.holarse.web.defines.WebDefines;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = "/search")
public class SearchController {
    
    private final static transient Logger logger = LoggerFactory.getLogger(SearchController.class);
    
    @ModelAttribute
    public SearchForm setupSearchForm() {
        return new SearchForm();
    }
    
    @PostMapping
    public RedirectView searchForm(@Valid final SearchForm searchForm) throws UnsupportedEncodingException {
        logger.info("Recieved search query {}", searchForm);
        return new RedirectView("search?q=" + URLEncoder.encode(searchForm.getQuery(), StandardCharsets.UTF_8));
    }
    
    @GetMapping
    public ModelAndView search(@RequestParam("q") final String q, final ModelAndView mv) throws UnsupportedEncodingException {
        mv.setViewName("layouts/bare");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/search/results");

        mv.addObject("q", URLDecoder.decode(q, StandardCharsets.UTF_8));
        
        return mv;
    }
    
}
