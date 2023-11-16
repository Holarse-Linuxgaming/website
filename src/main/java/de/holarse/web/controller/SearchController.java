package de.holarse.web.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import de.holarse.web.controller.commands.SearchForm;
import jakarta.validation.Valid;

/**
 * Nimmt das Suchformular entgegen und leitet uns an den Spielefinder weiter
 * @author comrad
 */
@Controller
@RequestMapping
public class SearchController {
    
    private final static transient Logger logger = LoggerFactory.getLogger(SearchController.class);
       
    @ModelAttribute
    public SearchForm setupSearchForm() {
        return new SearchForm();
    }
    
    @PostMapping("/search")
    public RedirectView searchForm(@Valid final SearchForm searchForm) throws UnsupportedEncodingException {
        logger.info("Recieved search query {}", searchForm);
        return new RedirectView("spielefinder?q=" + URLEncoder.encode(searchForm.getQuery(), StandardCharsets.UTF_8));
    }
    
}
