package de.holarse.web.controller;

import de.holarse.backend.db.repositories.SearchRepository;
import de.holarse.backend.db.repositories.TagGroupRepository;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.web.controller.commands.SearchForm;
import de.holarse.web.defines.WebDefines;
import static de.holarse.web.defines.WebDefines.TAG_DELIMITER;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping(value = {"/spielefinder", "/spielefinder/"})
public class GameFinderController {
    
    @Autowired
    private TagGroupRepository tagGroupRepository;
    
    @Autowired
    private TagRepository tagRepository;
    
    @Autowired
    private SearchRepository searchRepository;
    
    @ModelAttribute
    public SearchForm setupSearchForm() {
        return new SearchForm();
    }    
        
    @GetMapping
    public ModelAndView index(
            @PageableDefault(value = 25, page = 0) @SortDefault(sort="title", direction = Sort.Direction.ASC) Pageable pageable, 
            @RequestParam(name = "t", defaultValue = "") final List<String> selectedTags,
            @RequestParam(name = "f", defaultValue = "") final String filter,
            @RequestParam(name = "a", defaultValue = "") final String toggleTag,
            final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/spielefinder/index");        
        
        // Ermitteln der Taggruppen und der dazugehörigen Tags
        var tagGroups = tagGroupRepository.findAllTagGroups(Sort.by(Sort.Direction.DESC, "weight"));
        
        // Ein Tag soll entweder hinzugeschaltet oder weggenommen werden. Danach Seite neuladen
        if (!StringUtils.isBlank(toggleTag)) {
            if (selectedTags.contains(toggleTag)) {
                selectedTags.remove(toggleTag);
            } else {
                selectedTags.add(toggleTag);
            }
            
            // TODO: Normalisieren und Aliasse auflösen
            
            final UriComponents uriComponents = UriComponentsBuilder.newInstance().path("spielefinder")
                                                                                  .queryParam("t", String.join(",", selectedTags)).encode()
                                                                                  .queryParam("f", filter).encode()
                                                                                  .queryParam("c", "0") // Dont count the redirect
                                                                                  .build();
            
            return new ModelAndView(String.format("redirect:/%s", uriComponents.toUriString()));
        }
        
        // TODO: Normalisieren und Aliasse auflösen
        
        // Suchergebnis ermitteln
        var searchResults = StringUtils.isAllBlank(filter) ? 
                            searchRepository.searchTags(String.join(TAG_DELIMITER, selectedTags), pageable) :
                            searchRepository.searchTags(String.join(TAG_DELIMITER, selectedTags), filter, pageable);
        
        mv.addObject("count", searchResults.getTotalElements());
        mv.addObject("tagGroups", tagGroups);
        mv.addObject("searchResults", searchResults);
        
        mv.addObject("t", String.join(",", selectedTags));
        mv.addObject("selectedTags", selectedTags.stream().map(s -> tagRepository.findBySlug(s))
                                                          .filter(Optional::isPresent)
                                                          .map(Optional::get)
                                                          .toList());
        mv.addObject("f", filter);
        
        return mv;
    }
    
}
