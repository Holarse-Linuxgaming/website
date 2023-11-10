package de.holarse.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import de.holarse.backend.db.repositories.SearchRepository;
import de.holarse.web.controller.commands.SearchForm;
import de.holarse.web.defines.WebDefines;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;

@Controller
@RequestMapping
public class SearchController {
    
    private final static transient Logger logger = LoggerFactory.getLogger(SearchController.class);
    
    @Autowired
    SearchRepository searchRepository;
       
    @ModelAttribute
    public SearchForm setupSearchForm() {
        return new SearchForm();
    }
    
    @PostMapping("/search")
    public RedirectView searchForm(@Valid final SearchForm searchForm) throws UnsupportedEncodingException {
        logger.info("Recieved search query {}", searchForm);
        return new RedirectView("search?q=" + URLEncoder.encode(searchForm.getQuery(), StandardCharsets.UTF_8));
    }
    
    @GetMapping("/search")
    public ModelAndView search(
            @RequestParam("q") final String q, 
            @PageableDefault(value = 25, page = 0) @SortDefault(sort="name", direction = Sort.Direction.ASC) Pageable pageable, 
            final ModelAndView mv) throws UnsupportedEncodingException {
        mv.setViewName("layouts/bare");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/search/results");

        mv.addObject("q", URLDecoder.decode(q, StandardCharsets.UTF_8));
        
        var results = searchRepository.search(String.join(" | ", q.trim().split(" ")), pageable);
        mv.addObject("results", results);
        mv.addObject("count", results.getTotalElements());
        
        return mv;
    }

    @GetMapping("/tags/{tags}")
    public ModelAndView searchTags(
            @PathVariable("tags") final List<String> tags, 
            @PageableDefault(value = 25, page = 0) @SortDefault(sort="name", direction = Sort.Direction.ASC) Pageable pageable, 
            final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/search/results");

        //mv.addObject("q", URLDecoder.decode(tags, StandardCharsets.UTF_8));
        
        var results = searchRepository.searchTags(String.join("~", tags), pageable);
        mv.addObject("results", results);
        mv.addObject("count", results.getTotalElements());
        
        return mv;
    }
    
}
