package de.holarse.web.tags;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.repositories.ArticleRepository;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class TagController {

    @Autowired
    ArticleRepository articleRepository;
    
    @GetMapping("/category/stichworte/{name}")
    public ModelAndView forwardSingleLegacyTag(final @PathVariable String name) {
        return new ModelAndView(new RedirectView("/tags/" + name, false, false, true));
    }
    
    @GetMapping("/category/stichworte/{tags}")    
    public ModelAndView forwardMultipleLegacyTags(final @PathVariable List<Long> tags) {
        return new ModelAndView(new RedirectView("/tags/" + tags.stream().map(t -> String.valueOf(t)).collect(Collectors.joining(",")), false, false, true));
    }
    
    @Transactional
    @GetMapping("/tags/{tags}")
    public String index(final @PathVariable List<String> tags, final Model map) {
        final List<Article> articles = articleRepository.findByTags(tags);
        articles.forEach(a -> Hibernate.initialize(a.getTags()));
        map.addAttribute("results", articles);
        return "tags/result";
    }
    
}
