package de.holarse.web.controller;

import de.holarse.backend.db.repositories.NewsRepository;
import de.holarse.web.defines.WebDefines;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static de.holarse.web.defines.WebDefines.NEWS_ARTICLES_DEFAULT_PAGE_SIZE;

@Controller
@RequestMapping(value = {"/news", "/news/" })
public class NewsController {

    @Autowired
    private NewsRepository newsRepository;

    @GetMapping
    public ModelAndView index(@PageableDefault(sort = {"title"}, value = NEWS_ARTICLES_DEFAULT_PAGE_SIZE) final Pageable pageable, final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/news/index");

        // TODO: Wieder entfernen, nur zum Testen
        mv.addObject("items", newsRepository.listCurrent(pageable));
        return mv;
    }
}
