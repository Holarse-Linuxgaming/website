package de.holarse.web.categories;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.search.SearchEngine;
import de.holarse.services.TagService;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/categories/")
public class CategoriesController {

    Logger logger = LoggerFactory.getLogger(CategoriesController.class);
    
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    TagService tagService;
    @Autowired
    @Qualifier("es")            
    SearchEngine searchEngine;

    @GetMapping
    @Transactional
    public String index(@RequestParam(name = "tags", required = false) final List<String> tags, final Model map) {
        final Set<String> chosenTags = new HashSet<>();
        if (tags != null) {
            chosenTags.addAll(tags);
        }
        
        logger.debug("Tags are: {}", tags);
        
        // Wenn Tags komplett leer sind, immer Top-Titel anzeigen
        if (chosenTags.isEmpty()) {
            chosenTags.add("Top-Titel");
        }

        final List<Tag> validatedTags = chosenTags.stream().map(ct -> tagRepository.findByNameIgnoreCase(ct)).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        
        logger.debug("used tags: {}", validatedTags);
        
        // Ergebnisse anzeigen
        final List<Article> articles = searchEngine.searchByTags(validatedTags, null).stream()
                .map(result -> articleRepository.findById(result.getId()))
                .filter(Optional::isPresent).map(Optional::get)
                .collect(Collectors.toList());

        map.addAttribute("nodes", articles);
        map.addAttribute("tags", validatedTags);

        return "categories/index";
    }

}
