package de.holarse.web.finder;

import de.holarse.backend.db.Tag;
import de.holarse.backend.db.TagGroup;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.TagGroupRepository;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.backend.views.PaginationView;
import de.holarse.backend.views.SearchResultView;
import de.holarse.backend.views.TagGroupView;
import de.holarse.backend.views.TagView;
import de.holarse.factories.ViewFactory;
import de.holarse.search.SearchEngine;
import de.holarse.services.TagService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/finder")
public class GameFinderController {

    Logger logger = LoggerFactory.getLogger(GameFinderController.class);
    
    @Autowired TagGroupRepository tagGroupRepository;
    @Autowired TagRepository tagRepository;
    
    @Autowired ArticleRepository articleRepository;
    
    @Autowired TagService tagService;

    @Autowired ViewFactory viewFactory;
    
    @Autowired 
    @Qualifier("pgsql")            
    SearchEngine searchEngine;
       
    @Transactional
    @GetMapping
    public ModelAndView index(@RequestParam(value = "tags", required = false) final List<String> tags, 
                              @RequestParam(value = "tag",  required = false) final String newTag, 
                              @RequestParam(value = "q", required = false) final String q,
                              @RequestParam(value = "i", required = false, defaultValue = "0") final int i, // Indikator für Erstsuche, bei 0 werden immer Top-Titel angezeigt
                              @RequestParam(value = "page", defaultValue = "1") final int page, 
                              @RequestParam(value = "pageSize", defaultValue = "30") final int pageSize,                        
                              final Model map) {
        long timer_start = System.currentTimeMillis();
        
        final List<TagGroupView> views = new ArrayList<>(100);
        
        // Taggroups laden und lazy Tags holen
        final List<TagGroup> tagGroups = tagGroupRepository.findAllByOrderByWeightDesc();
        for(final TagGroup tg : tagGroups) {
            // Sortierte Liste der Tags der Taggroup holen
            final List<Tag> assocTags = tagRepository.findByTagGroup(tg.getName());
            // Summe der zugehörigen Artikel zählen
            final long groupedSum = assocTags.stream().mapToLong(t -> t.getUseCount()).sum();
            // TagViews erstellen
            final List<TagView> tagViews = assocTags.stream().map(viewFactory::fromTag).collect(Collectors.toList());
            // TagGroupView erstellen
            final TagGroupView tgv = new TagGroupView(tg.getId(), tg.getName(), tagViews, groupedSum);
            views.add(tgv);
        }
        
        // Freie Tags noch einfügen
        final List<Tag> freeTags = tagRepository.findFreeTags();
        final List<TagView> freeTagViews = freeTags.stream().map(viewFactory::fromTag).collect(Collectors.toList());
        final long freeTagGroupSum = freeTags.stream().mapToLong(t -> t.getUseCount()).sum();
        final TagGroupView tgvFree = new TagGroupView(0, "Freie Stichworte", freeTagViews, freeTagGroupSum);
        views.add(tgvFree);
        
        map.addAttribute("tagGroups", views);
        
        // Bestehende Tags einfügen
        final Set<String> chosenTags = new HashSet<>();
        if (tags != null) {
            chosenTags.addAll(tags);
        }
        
        // Neuen Tag einfügen oder entfernen falls bereits vorhanden
        boolean redirectAfterNewTag = false;      
        if (StringUtils.isNotBlank(newTag)) {
            if (chosenTags.contains(newTag)) {
                chosenTags.remove(newTag);
            } else {
                chosenTags.add(newTag);
            }
            redirectAfterNewTag = true;
        }
        
        // Wenn Tags komplett leer sind, immer Top-Titel anzeigen
        if (chosenTags.isEmpty()) {
            chosenTags.add("Top-Titel");
        }
        
        final String qry = (q == null ? "" : q);

        
        // Aus den ausgewählten Tags die korrekten Tags ermitteln und 
        // diese über ihre Aliasse normalisieren
        final Collection<Tag> validatedTags = chosenTags.stream().map(tagRepository::findByNameIgnoreCase)
                                                           .filter(Optional::isPresent)
                                                           .map(Optional::get)
                                                           .collect(Collectors.toList());
        
        // Tagliste für die Url erzeugen
        final String taglistForUrl = validatedTags.stream().map(t -> t.getName()).collect(Collectors.joining(","));        
        final String newUrl = String.format("/finder/?tags=%s&q=%s", taglistForUrl, qry);
        
        // Neues Tag hinzufügen und Seite neuladen bevor wir die unnötige Suche ausführen
        if (redirectAfterNewTag) {
            return new ModelAndView(new RedirectView(newUrl, false, false, false));
        }

        var pagination = new PaginationView(newUrl, page, searchEngine.searchCount(validatedTags, q), pageSize);                
        
        // Ergebnisse ermitteln
        final List<SearchResultView> searchResults = searchEngine.searchByTags(validatedTags, q, pagination.getPageable())
                                                                 .stream()
                                                                 .map(SearchResultView::new)
                                                                 .collect(Collectors.toList());
        

        
        // Ergebnisse anzeigen
        map.addAttribute("searchResults", searchResults);
        map.addAttribute("tags", validatedTags);
        map.addAttribute("taglist", taglistForUrl);
        map.addAttribute("q", q);
        map.addAttribute("pagination", pagination);        
        
        String title = "Alle Linux-Spiele auf Holarse mit " + taglistForUrl;
        if (StringUtils.isNotBlank(q))
            title += " und " + q;
        
        map.addAttribute("title", title);
        map.addAttribute("duration", (System.currentTimeMillis() - timer_start));
        
        return new ModelAndView("finder/index");
    }
    
}
