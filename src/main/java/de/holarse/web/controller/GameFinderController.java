package de.holarse.web.controller;

import de.holarse.backend.db.TagGroup;
import de.holarse.backend.db.datasets.SearchResultView;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
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
    
    // Standardsortierung nach Ergebnis-Ranking
    final static Sort defaultRankSorted = JpaSort.unsafe(Sort.Direction.DESC, "ts_rank(document, websearch_to_tsquery('german', :query))");    
        
    @GetMapping
    public ModelAndView index(
            @PageableDefault(value = WebDefines.DEFAULT_LIST_SIZE) Pageable pageable, 
            @RequestParam(name = "t", defaultValue = "") final List<String> selectedTags,
            @RequestParam(name = "q", defaultValue = "") final String query,
            @RequestParam(name = "a", defaultValue = "") final String toggleTag,
            final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/search/results");        
                
        // Ermitteln der Taggruppen und der dazugehörigen Tags
        final List<TagGroup> tagGroups = tagGroupRepository.findAllTagGroups(Sort.by(Sort.Order.desc("weight"), Sort.Order.desc("t.useCount")));
        
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
                                                                                  .queryParam("q", query).encode()
                                                                                  .queryParam("c", "0") // Dont count the redirect
                                                                                  .build();
            
            return new ModelAndView(String.format("redirect:/%s", uriComponents.toUriString()));
        }
        
        // TODO: Normalisieren und Aliasse auflösen
        
        //
        // Suchergebnis ermitteln
        //
        
        // Suchwörter splitten und mit Oder verbinden
        var orJoinQuery = String.join(" | ", query.trim().split(" "));
        
        // Die Standardsortierung davon abhängig machen, was wir suchen. 
        final PageRequest pageRequest = StringUtils.isNotBlank(query) // Bei Text "ranked" suchen, sofern nicht anders definiert
                                                                      ?    PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(defaultRankSorted)) 
                                                                      // bei Tags nur nach Titel
                                                                      :    PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Order.by("title"))));
        
        // Suche unterscheiden, ob Tags gesetzt sind oder nur ein Query
        Page<SearchResultView> searchResults;
        if (selectedTags.isEmpty()) {
            // Garkeine Tags gesetzt, wir suchen im Text
            searchResults = searchRepository.search(orJoinQuery, pageRequest);            
        } else {
            // Tags gesetzt, also Tag-basierte Suche
            searchResults = StringUtils.isAllBlank(query) ? 
                            searchRepository.searchTags(String.join(TAG_DELIMITER, selectedTags), pageRequest) :
                            searchRepository.searchTags(String.join(TAG_DELIMITER, selectedTags), orJoinQuery, pageRequest);
        }
        
        mv.addObject("count", searchResults.getTotalElements());
        mv.addObject("tagGroups", tagGroups);
        mv.addObject("searchResults", searchResults);
        
        mv.addObject("t", String.join(",", selectedTags));
        mv.addObject("selectedTags", selectedTags.stream().map(s -> tagRepository.findBySlug(s))
                                                          .filter(Optional::isPresent)
                                                          .map(Optional::get)
                                                          .toList());
        mv.addObject("q", query);
        
        return mv;
    }
    
}
