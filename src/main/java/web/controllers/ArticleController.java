package web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import web.entities.Article;
import web.services.ArticleService;

@Controller
@RequestMapping(value = { "wiki", "articles" })
public class ArticleController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private ArticleService as;

    @RequestMapping("reload")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void reload() throws Exception {
        as.initArticlesFromDisk();
    }
    
    @RequestMapping("{uid}")
    String article(final @PathVariable("uid") long uid, final ModelMap map) {
        logger.debug("Asking for article uid {}", uid);
        map.addAttribute("hallo", "möööp");        
        map.addAttribute("title", "Holarse - Spielen unter Linux");
        
        final Article a = as.findById(uid);
        logger.debug("{}", a);
        
        map.addAttribute("article", a);
        
        return "article";
    }    
    
}
