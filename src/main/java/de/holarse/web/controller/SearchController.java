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
import static de.holarse.web.defines.WebDefines.TAG_DELIMITER;
import static de.holarse.web.defines.WebDefines.WIKI_ARTICLES_DEFAULT_PAGE_SIZE;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;

@Controller
@RequestMapping
public class SearchController {
    
    private final static transient Logger logger = LoggerFactory.getLogger(SearchController.class);
    
    // Standardsortierung nach Ergebnis-Ranking
    final static Sort defaultRankSorted = JpaSort.unsafe(Sort.Direction.DESC, "ts_rank(document, websearch_to_tsquery('german', :query))");
    
    @Autowired
    private SearchRepository searchRepository;
       
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
            @PageableDefault(value = WIKI_ARTICLES_DEFAULT_PAGE_SIZE) Pageable pageable, 
            final ModelAndView mv) throws UnsupportedEncodingException {
        mv.setViewName("layouts/bare");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/search/results");

        mv.addObject("q", URLDecoder.decode(q, StandardCharsets.UTF_8));
        
        final PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(defaultRankSorted));

        var results = searchRepository.search(String.join(" | ", q.trim().split(" ")), pageRequest);
        mv.addObject("results", results);
        mv.addObject("count", results.getTotalElements());
        
        return mv;
    }
    
}
