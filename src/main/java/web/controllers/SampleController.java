package web.controllers;

import org.springframework.stereotype.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.configuration.AtomicIdGenerator;
import web.entities.Article;
import web.entities.sub.Content;
import web.entities.sub.ContentType;
import web.entities.sub.TitleType;

@Controller
public class SampleController {

    @RequestMapping("/")
    String home(final ModelMap map) {
        map.addAttribute("hallo", "welt");
        
        map.addAttribute("article", generateSampleArticle());
        map.addAttribute("title", "Holarse - Spielen unter Linux");
        
        return "index";
    }
    
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
