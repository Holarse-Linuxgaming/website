package de.holarse.web.sitemap;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import de.holarse.backend.sitemap.Url;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.NewsRepository;
import de.holarse.backend.sitemap.ChangeFrequencyType;
import de.holarse.backend.sitemap.UrlSet;
import java.io.StringWriter;
import java.time.OffsetDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Implementation der Sitemap gemäß sitemap-Protokoll: 
 * https://www.sitemaps.org/de/protocol.html
 */
@Controller
public class SiteMap {

    @Autowired 
    NewsRepository newsRepo;

    @Autowired 
    ArticleRepository articleRepo;

    @Autowired 
    HttpServletRequest request;
    
    /**
     * TODO
     * Muss später noch gegen das header-value von x-forwarded-proto ausgetauscht werden
     */
    final static String baseAddress = "https://www.holarse-linuxgaming.de";
    
    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML)
    public @ResponseBody String sitemap() throws Exception {
        final UrlSet urlSet = new UrlSet();
        
        final Url homepage = new Url(baseAddress, OffsetDateTime.now(), ChangeFrequencyType.HOURLY);
        urlSet.getUrls().add(homepage);        
        
        articleRepo.findByDeletedFalseAndPublishedTrue().map(n -> new Url(String.format("%s/wiki/%s", baseAddress, n.getSlug()), n.getUpdated() != null ? n.getUpdated() : n.getCreated(), ChangeFrequencyType.ALWAYS))
                                                        .forEach(u -> urlSet.getUrls().add(u));        
        
        newsRepo.findByDeletedFalseAndPublishedTrue().map(n -> new Url(String.format("%s/news/%s", baseAddress, n.getSlug()), n.getUpdated() != null ? n.getUpdated() : n.getCreated(), ChangeFrequencyType.ALWAYS))
                                                     .forEach(u -> urlSet.getUrls().add(u));
        
        // Leider von Hand, da ich Jackson nicht via Spring konfiguriert bekomme, die XML declaration anzuzeigen
        final XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.enable(ToXmlGenerator.Feature.WRITE_XML_DECLARATION);
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return xmlMapper.writeValueAsString(urlSet);
    }
}
