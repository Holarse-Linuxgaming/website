package web.controllers;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.services.ArticleService;

@Controller
public class WelcomeController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private ArticleService as;
    
    @RequestMapping("/")
    String home(final ModelMap map) throws Exception {
        map.addAttribute("title", "Holarse - Spielen unter Linux");

        map.addAttribute("articles", as.getAll());
        
        return "index";
    }

}
