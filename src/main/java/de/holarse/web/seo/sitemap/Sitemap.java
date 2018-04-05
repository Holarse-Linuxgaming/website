package de.holarse.web.seo.sitemap;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.repositories.ArticleRepository;
import java.time.format.DateTimeFormatter;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Sitemap {
    
    @Autowired
    ArticleRepository ar;
    
    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML)
    public @ResponseBody String sitemap() {
        final StringBuilder buffer = new StringBuilder();
        
        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        buffer.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">");
        
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        
        for (final Article article : ar.findAll()) {
            buffer.append("<url>");
            
            buffer.append("<loc>").append(article.getUrl()).append("</loc>");
            buffer.append("<lastmod>").append(article.getUpdated() != null ? article.getUpdated().format(formatter) : article.getCreated().format(formatter)).append("</lastmod>");
            buffer.append("<changefreq>").append("daily").append("</changefreq>");
            buffer.append("<priority>").append("0.5").append("</priority>");
            
            buffer.append("</url>");
        }
        
        buffer.append("</urlset>");
        
        return buffer.toString();
    }
    
}
