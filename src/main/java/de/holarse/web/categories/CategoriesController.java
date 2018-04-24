package de.holarse.web.categories;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.services.TagService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories/")
public class CategoriesController {
    
    @Autowired ArticleRepository articleRepository;
    @Autowired TagService tagService;
    
    @GetMapping
    @Transactional
    public String index(@PathVariable(name = "tags", required= false) final List<String> tags, final Model map) {
        final Set<String> chosenTags = new HashSet<>();
        
        // Wenn Tags komplett leer sind, immer Top-Titel anzeigen
        if (chosenTags.isEmpty()) {
            chosenTags.add("Top-Titel");
        }        
        
        final List<Article> articles = articleRepository.findByTags(chosenTags, chosenTags.size());
        articles.forEach(a -> Hibernate.initialize(a.getTags()));
        map.addAttribute("nodes", articles);        
       
        final String taglist = tagService.createTagList(chosenTags);
        map.addAttribute("taglist", taglist);
        
       return "categories/index"; 
    }
    
}
