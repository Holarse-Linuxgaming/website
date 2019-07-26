package de.holarse.web.admin;

import de.holarse.backend.db.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/html")
public class AdminWikiController {

    @Autowired
    ArticleRepository articleRepository;
    
    @GetMapping("/")
    public String index(final ModelMap map) {
        map.addAttribute("nodes", articleRepository.findAll());
        return "admin/html/index";
    }
    
}
