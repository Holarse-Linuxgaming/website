package de.holarse.web.seo;

import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.NewsRepository;
import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SiteMap {

    @Autowired private NewsRepository newsRepo;

    @Autowired private ArticleRepository articleRepo;

    @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML)
    public @ResponseBody String sitemap(HttpServletRequest request)
            throws JAXBException, PropertyException {

        StringBuffer UrlBuffer = request.getRequestURL();
        // Cut the sitemap.xml from the request
        UrlBuffer.setLength(UrlBuffer.length() - 12);
        String url = UrlBuffer.toString();

        final StringWriter buffer = new StringWriter();

        buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        buffer.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");
        buffer.append(
                "<url><loc>"
                        + url
                        + "/</loc>"
                        + "<changefreq>daily</changefreq><priority>1.0</priority></url>\n");

        JAXBContext jaxbContext = JAXBContext.newInstance(SiteMapNode.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty("jaxb.fragment", true);

        for (SiteMapNode node : this.newsRepo.findAllForSiteMap()) {
            node.setBaseURL(url);
            marshaller.marshal(node, buffer);
        }

        for (SiteMapNode node : this.articleRepo.findAllForSiteMap()) {
            node.setBaseURL(url);
            marshaller.marshal(node, buffer);
        }

        buffer.append("</urlset>\n");
        return buffer.toString();
    }
}
