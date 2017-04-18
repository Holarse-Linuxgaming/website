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
    
    @RequestMapping("{uid}")
    String article(final @PathVariable("uid") long uid, final ModelMap map) throws Exception {
        logger.debug("Asking for article uid {}", uid);
        
        final Article a = as.get(uid);
        logger.debug("{}", a);
        
        map.addAttribute("article", a);
        
        return "article";
    }          
    
}
