package de.holarse.web.controller;

import de.holarse.backend.db.TagGroup;
import de.holarse.backend.db.datasets.SearchResultView;
import de.holarse.backend.db.repositories.SearchRepository;
import de.holarse.backend.db.repositories.TagGroupRepository;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.backend.types.SearchScopeType;
import de.holarse.web.controller.commands.SearchForm;
import de.holarse.web.defines.WebDefines;
import jakarta.validation.Valid;

import static de.holarse.web.defines.WebDefines.TAG_DELIMITER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping(value = {"/spielefinder", "/spielefinder/"})
public class GameFinderController {
    
    private final static transient Logger logger = LoggerFactory.getLogger(GameFinderController.class);

    @Autowired
    private TagGroupRepository tagGroupRepository;
    
    @Autowired
    private TagRepository tagRepository;
    
    @Autowired
    private SearchRepository searchRepository;

    @Value("${holarse.search.rankLimit}")
    private Float rankLimit;
   
    // Standardsortierung nach Ergebnis-Ranking
    // See https://www.postgresql.org/docs/current/textsearch-controls.html#TEXTSEARCH-RANKING
    final static Sort defaultRankSorted = JpaSort.unsafe(Sort.Direction.DESC, "ts_rank_cd(document, websearch_to_tsquery('german', :query), 16)");
        
    @GetMapping
    public String index(
            // @RequestParam(name = "t", defaultValue = "") final List<String> selectedTags,
            // @RequestParam(name = "q", defaultValue = "") final String query,
            // @RequestParam(name = "a", defaultValue = "") final String toggleTag,
            // @RequestParam(name = "sort", defaultValue = "") final String sort,
            // @RequestParam(name = "s", defaultValue = "article,news,thread") final List<SearchScopeType> scope,
            @PageableDefault(size = 10)
            final Pageable pageable,
            @Valid @ModelAttribute("searchFormDetail") final SearchForm searchForm,
            final Model model) {       
        logger.debug("searchForm at start: {}", searchForm);
        final List<String> selectedTags = searchForm.getT();

        // Ein Tag soll entweder hinzugeschaltet oder weggenommen werden.
        if (StringUtils.isNotBlank(searchForm.getA())) {
            if (selectedTags.contains(searchForm.getA())) {
                selectedTags.remove(searchForm.getA());
            } else {
                selectedTags.add(searchForm.getA());
            }

            // TODO: Tags normalisieren und Aliasse auflösen
            final UriComponents uriComponents = UriComponentsBuilder.newInstance().path("spielefinder")
                                                                                  .queryParam("t", String.join(",", selectedTags)).encode()
                                                                                  .queryParam("q", searchForm.getQ()).encode()
                                                                                  .queryParam("s", String.join(",", searchForm.getS().stream().map(SearchScopeType::toString).toList()))
                                                                                  .build();
            
            return String.format("redirect:/%s", uriComponents.toUriString());            
        }
        
        // if (!StringUtils.isBlank(toggleTag)) {
        //     if (selectedTags.contains(toggleTag)) {
        //         selectedTags.remove(toggleTag);
        //     } else {
        //         selectedTags.add(toggleTag);
        //     }
            
            // TODO: Normalisieren und Aliasse auflösen
            
        //     final UriComponents uriComponents = UriComponentsBuilder.newInstance().path("spielefinder")
        //                                                                           .queryParam("t", String.join(",", selectedTags)).encode()
        //                                                                           .queryParam("q", query).encode()
        //                                                                           .queryParam("s", scope)                    
        //                                                                           .queryParam("c", "0") // Do not pagecount the redirect
        //                                                                           .build();
            
        //     return String.format("redirect:/%s", uriComponents.toUriString());
        // }
        
        // TODO: Normalisieren und Aliasse auflösen
        
        //
        // Suchergebnis ermitteln
        //
        
        // // Suchwörter splitten und mit Oder verbinden
        // var orJoinQuery = String.join(" | ", searchForm.getQ().trim().split(" "));
        
        // // Die Standardsortierung davon abhängig machen, was wir suchen. 
        // final PageRequest pageRequest = StringUtils.isNotBlank(query) // Bei Text "ranked" suchen, sofern nicht anders definiert
        //                                                               ?    PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(defaultRankSorted)) 
        //                                                               // bei Tags nur nach Titel
        //                                                               :    PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSortOr(Sort.by(Sort.Order.by("title"))));
        
        // // Suche unterscheiden, ob Tags gesetzt sind oder nur ein Query
        // Page<SearchResultView> searchResults;
        // if (selectedTags.isEmpty()) {
        //     // Garkeine Tags gesetzt, wir suchen im Text
        //     searchResults = searchRepository.search(orJoinQuery, scope, rankLimit, pageRequest);
        // } else {
        //     // Tags gesetzt, also Tag-basierte Suche
        //     searchResults = StringUtils.isAllBlank(query) ? 
        //                     searchRepository.searchTags(String.join(TAG_DELIMITER, selectedTags), scope, pageRequest) :
        //                     searchRepository.searchTags(String.join(TAG_DELIMITER, selectedTags), orJoinQuery, scope, pageRequest);            
        // }

        Page<SearchResultView> searchResults;
        searchResults = searchRepository.search(searchForm.getQ(), searchForm.getS().stream().map(SearchScopeType::toString).toList(), rankLimit, pageable);
        
        // Ermitteln der Taggruppen und der dazugehörigen Tags
        // final List<TagGroup> tagGroups = tagGroupRepository.findAllTagGroups(Sort.by(Sort.Order.desc("tg.weight"), Sort.Order.desc("t.weight"), Sort.Order.desc("t.useCount")));                        

        //final SearchForm searchFormDetail = new SearchForm();
        
        // model.addAttribute("count", searchResults.getTotalElements());
        // model.addAttribute("tagGroups", tagGroups);
        // model.addAttribute("searchResults", searchResults);
        
        // model.addAttribute("t", String.join(",", selectedTags));
        // model.addAttribute("selectedTags", selectedTags.stream().map(s -> tagRepository.findBySlug(s))
        //                                                   .filter(Optional::isPresent)
        //                                                   .map(Optional::get)
        //                                                   .toList());

        final SearchForm newSearchForm = new SearchForm();
        newSearchForm.setQ(searchForm.getQ());
        newSearchForm.setS(CollectionUtils.isEmpty(searchForm.getS()) ? Arrays.asList(SearchScopeType.values()) : searchForm.getS());
        newSearchForm.setSort(searchForm.getSort());
        newSearchForm.setT(selectedTags);

        logger.debug("newSearchForm: {}", newSearchForm);

        model.addAttribute("searchFormDetail", newSearchForm);
        model.addAttribute("searchResults", searchResults);
        
        return "sites/search/results";
    }
    
}
