package de.holarse.web.api;

import de.holarse.backend.db.repositories.SearchRepository;
import de.holarse.backend.view.TagView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping(value = {"/webapi/tags"})
public class TagApiController {

    private final static transient Logger logger = LoggerFactory.getLogger(TagApiController.class);
    
    @Autowired
    SearchRepository searchRepository;

    @GetMapping(value = "autocomplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TagView> autoComplete(@RequestParam final String query) {
        logger.info("Autocomplete request for input {}", query);
        return new ArrayList<>();
    }

}
