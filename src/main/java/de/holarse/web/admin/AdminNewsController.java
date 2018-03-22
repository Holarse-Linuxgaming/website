package de.holarse.web.admin;

import de.holarse.backend.db.repositories.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/news")
public class AdminNewsController {

    @Autowired
    NewsRepository newsRepository;
    
    @GetMapping("/")
    public String index(final ModelMap map) {
        map.addAttribute("nodes", newsRepository.findAll());
        return "admin/news/index";
    }
    
}
