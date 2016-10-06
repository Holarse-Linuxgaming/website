package web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.configuration.AtomicIdGenerator;
import web.entities.Article;
import web.entities.sub.Content;
import web.entities.sub.ContentType;
import web.entities.sub.TitleType;
import web.services.ArticleService;

@Controller
public class SampleController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private ArticleService as;
    
    @RequestMapping("/")
    String home(final ModelMap map) {
        map.addAttribute("hallo", "möööp");
       
        map.addAttribute("article", as.findById(123123l));
        map.addAttribute("title", "Holarse - Spielen unter Linux");

        logger.debug("Askign for stuff");
        
        return "index";
    }
    
    @RequestMapping("/wiki/{uid}")
    String article(final @PathVariable("uid") long uid, final ModelMap map) {
        logger.debug("Asking for article uid {}", uid);
        map.addAttribute("hallo", "möööp");        
        map.addAttribute("title", "Holarse - Spielen unter Linux");
        
        map.addAttribute("article", as.findById(uid));
        return "index";
    }

    @Deprecated
    protected Article getAnArticle() {
        return generateSampleArticle();
    }
    
    @Deprecated
    protected Article generateSampleArticle() {
            final Article a = new Article(AtomicIdGenerator.nextId());
            a.addNewTitle(TitleType.MAIN, "Der erste Artikel");

            final Content c = new Content();
            c.setType(ContentType.PLAIN);
            c.setContent("Hallo Welt");
            a.setContent(c);
            
            return a;
    }

}
