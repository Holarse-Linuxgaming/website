package de.holarse.web.finder;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.TagGroup;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.TagGroupRepository;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.search.SearchEngine;
import de.holarse.services.TagService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
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
@RequestMapping("/finder/")
public class GameFinderController {

    Logger logger = LoggerFactory.getLogger(GameFinderController.class);
    
    @Autowired TagGroupRepository tagGroupRepository;
    @Autowired TagRepository tagRepository;
    
    @Autowired ArticleRepository articleRepository;
    
    @Autowired TagService tagService;
    
    @Autowired 
    @Qualifier("es")            
    SearchEngine searchEngine;
    
    @Transactional
    @GetMapping
    public ModelAndView index(@RequestParam(value = "tags", required = false) final List<String> tags, 
                        @RequestParam(value = "tag",  required = false) final String newTag, 
                        @RequestParam(value = "q", required = false) final String q,
                        @RequestParam(value = "i", required = false, defaultValue = "0") final int i, // Indikator für Erstsuche, wo Top-Titel
                        final Model map) {
        // Taggroups laden und lazy Tags holen
        final List<TagGroup> tagGroups = tagGroupRepository.findSortedTagGroups();
        tagGroups.forEach(tg -> Hibernate.initialize(tg.getTags()));
        map.addAttribute("tagGroups", tagGroups);

        // Restliche Unstrukturierte Tags laden 
        // TODO später noch nach Häufigkeit sortieren
        map.addAttribute("freeTags", tagRepository.findFreeTags());
        
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
        
        final String taglist = tagService.createTagList(chosenTags);
        final List<Tag> validatedTags = chosenTags.stream().map(ct -> tagRepository.findByNameIgnoreCase(ct)).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        
        // Neues Tag hinzufügen und Seite neuladen
        if (redirectAfterNewTag) {
            return new ModelAndView(new RedirectView("/finder/?tags=" + taglist + "&q=" + (q == null ? "" : q), false, false, false));
        }
        
        // Ergebnisse anzeigen
        final List<Article> articles = searchEngine.searchByTags(validatedTags, q).stream()
                .map(result -> articleRepository.findById(result.getId()))
                .filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toList());
        articles.forEach(a -> Hibernate.initialize(a.getTags()));
        map.addAttribute("nodes", articles);
        map.addAttribute("tags", validatedTags);
        map.addAttribute("taglist", taglist);
        map.addAttribute("q", q);
        
        map.addAttribute("title", "Spielefinder für " + taglist);
        
        return new ModelAndView("finder/index");
    }
    
}
