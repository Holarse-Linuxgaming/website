package de.holarse.web.controller;

import de.holarse.backend.db.repositories.ApiUserRepository;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.NewsRepository;
import de.holarse.backend.view.FrontpageItemView;
import de.holarse.web.defines.WebDefines;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.SessionFlashMapManager;

/**
 * Stell die Willkommensseite mit dem Mural dar
 */
@Controller
@RequestMapping(value = "/")
public class WelcomeController {
    
    @Autowired
    ApiUserRepository apiUserRepository;

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    NewsRepository newsRepository;

    @GetMapping
    public String index(final Model model) {
        model.addAttribute("title", "Die Linuxspiele-Seite für Linuxspieler");

        var pageRequest = PageRequest.of(1, 10, Sort.by("nr.updated").descending().and(Sort.by("nr.created").descending()));

        final List<FrontpageItemView> articles = articleRepository.findFrontpageItems(pageRequest);
        final List<FrontpageItemView> news = newsRepository.findFrontpageItems(pageRequest) != null ? newsRepository.findFrontpageItems(pageRequest).getContent() : new ArrayList<>();
        
        final List<FrontpageItemView> items = new ArrayList<>();
        items.addAll(articles);
        items.addAll(news);
        // TODO Sortieren nach belieben
        
        model.addAttribute("items", items);

        

        return "sites/welcome";
    }
    
}
