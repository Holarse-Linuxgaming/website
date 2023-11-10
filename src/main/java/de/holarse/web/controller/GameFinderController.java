package de.holarse.web.controller;

import de.holarse.backend.db.repositories.SearchRepository;
import de.holarse.backend.db.repositories.TagGroupRepository;
import de.holarse.web.defines.WebDefines;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = {"/spielefinder", "/spielefinder/"})
public class GameFinderController {
    
    @Autowired
    private TagGroupRepository tagGroupRepository;
    
    @Autowired
    private SearchRepository searchRepository;
    
    @GetMapping
    public ModelAndView index(
            @PageableDefault(value = 25, page = 0) @SortDefault(sort="title", direction = Sort.Direction.ASC) Pageable pageable, 
            @RequestParam(name = "t", defaultValue = "") final List<String> selectedTags,
            @RequestParam(name = "f", defaultValue = "") final String filter,
            final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/spielefinder/index");        
        
        // Ermitteln der Taggruppen und der dazugehörigen Tags
        var tagGroups = tagGroupRepository.findAllTagGroups(Sort.by(Sort.Direction.DESC, "weight"));
        
        // Suchergebnis ermitteln
        var searchResults = StringUtils.isAllBlank(filter) ? 
                            searchRepository.searchTags(String.join("~", selectedTags), pageable) :
                            searchRepository.searchTags(String.join("~", selectedTags), filter, pageable);
        
        mv.addObject("count", searchResults.getTotalElements());
        mv.addObject("tagGroups", tagGroups);
        mv.addObject("searchResults", searchResults);
        
        mv.addObject("t", selectedTags);
        mv.addObject("f", filter);
        
        return mv;
    }
    
}
