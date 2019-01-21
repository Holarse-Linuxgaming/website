package de.holarse.web.api;

import de.holarse.renderer.wiki.WikiRenderer;
import de.holarse.web.articles.ArticleController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webapi")
public class WebApiController {

    Logger logger = LoggerFactory.getLogger(WebApiController.class);    
    
    @Autowired
    private WikiRenderer wikiRenderer;
    
    @Secured("ROLE_USER")    
    @RequestMapping(value = "/render/preview", method = RequestMethod.POST)
    public String renderPreview(String content) {
        return wikiRenderer.render(content);
    }
            
    
}
