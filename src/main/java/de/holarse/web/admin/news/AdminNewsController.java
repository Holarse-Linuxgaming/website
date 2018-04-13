package de.holarse.web.admin.news;

import de.holarse.backend.db.repositories.FrontPageRepository;
import de.holarse.backend.db.repositories.NewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/news")
public class AdminNewsController {

    Logger logger = LoggerFactory.getLogger(AdminNewsController.class);
    
    @Autowired NewsRepository newsRepository;
    
    @Autowired FrontPageRepository frontPageRepository;
    
    @GetMapping("/")
    public String index(final ModelMap map) {
        map.addAttribute("nodes", newsRepository.findAll());
        return "admin/news/index";
    }
           
}
