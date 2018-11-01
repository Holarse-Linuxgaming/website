package de.holarse.web.videonews;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.ContentType;
import de.holarse.backend.db.FrontPageItem;
import de.holarse.backend.db.News;
import de.holarse.backend.db.ShortNews;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.FrontPageRepository;
import de.holarse.backend.db.repositories.ShortNewsRepository;
import de.holarse.services.NodeService;
import de.holarse.services.WebUtilService;
import de.holarse.web.admin.frontpage.FrontPageService;
import java.time.OffsetDateTime;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(path = {"videonews"})
public class VideoNewsController {

    Logger logger = LoggerFactory.getLogger(News.class);

    @Autowired FrontPageRepository frontPageRepository;
    @Autowired ShortNewsRepository shortNewsRepository;
    @Autowired FrontPageService frontPageService;
    @Autowired NodeService nodeService;
    @Autowired WebUtilService webUtilService;

    // INDEX
    @GetMapping("/")
    public String index(final Model map) {
        map.addAttribute("nodes", shortNewsRepository.findAll());

        return "shortnews/index";
    }

    // NEW
    @Secured({"ROLE_REPORTER", "ROLE_ADMIN"})             
    @GetMapping("/new")
    public String newEntry(final Model map, final VideoNewsCommand command) {
        map.addAttribute("command", command);
        return "shortnews/new";
    }
    
    // SHOW, noch slug-fähig machen!
    @GetMapping("/{id}")
    public RedirectView show(@PathVariable("id") final Long id) {
        final ShortNews sn = shortNewsRepository.findById(id).get();
        return new RedirectView(sn.getLink(), false, false, false);
    }

    // CREATE
    @Secured({"ROLE_REPORTER", "ROLE_ADMIN"})        
    @Transactional    
    @PostMapping("/")   
    public RedirectView create(@ModelAttribute final VideoNewsCommand command, final Authentication authentication) throws Exception {
        final ShortNews node = nodeService.initNode(ShortNews.class);

        final YoutubeVideo video = webUtilService.parseYoutubeLink(command.getLink());
        
        // Node-Inhalt
        node.setTitle(command.getTitle());
        node.setTeaser(command.getTeaser());
        node.setTeaserImage(video.getThumbnail());
        node.setLink(video.getYoutubeUrl()); // Proxy für Content
        node.setContentType(ContentType.PLAIN);
        node.setAuthor(((HolarsePrincipal) authentication.getPrincipal()).getUser());

        node.setCreated(OffsetDateTime.now());
        shortNewsRepository.save(node);
        
        // Sofort ein Frontpage-Eintrag erstellen
        final FrontPageItem fpi = frontPageService.buildFrontPageItem(new FrontPageItem(), node);
        
        fpi.setPinned(false);
        fpi.setPublishFrom(OffsetDateTime.now());
        frontPageService.save(fpi);        

        return new RedirectView(node.getUrl(), false, false, false);
    }

    // EDIT
    @Secured({"ROLE_REPORTER", "ROLE_ADMIN"})      
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable final Long id, final Model map, final VideoNewsCommand command) throws Exception {
        final ShortNews node = shortNewsRepository.findById(id).get();
        
        final YoutubeVideo video = webUtilService.parseYoutubeLink(command.getLink());
        
        // Node-Inhalt
        node.setTitle(command.getTitle());
        node.setTeaser(command.getTeaser());
        node.setTeaserImage(video.getThumbnail());
        node.setLink(video.getYoutubeUrl()); // Proxy für Content
        node.setContentType(ContentType.PLAIN);

        map.addAttribute("node", node);        
        map.addAttribute("command", command);

        return "shortnews/edit";
    }

    // UPDATE
    @Secured({"ROLE_REPORTER", "ROLE_ADMIN"})   
    @Transactional
    @PostMapping("/{id}")
    public RedirectView update(@PathVariable final Long id, final VideoNewsCommand command, final Authentication authentication) throws Exception {
        final User currentUser = ((HolarsePrincipal) authentication.getPrincipal()).getUser();

        final ShortNews node = shortNewsRepository.findById(id).get();
        
        // Node-Inhalt
        final YoutubeVideo video = webUtilService.parseYoutubeLink(command.getLink());
        
        // Node-Inhalt
        node.setTitle(command.getTitle());
        node.setTeaser(command.getTeaser());
        node.setTeaserImage(video.getThumbnail());
        node.setLink(video.getYoutubeUrl()); // Proxy für Content
        node.setContentType(ContentType.PLAIN);
      
        // News-Metadaten aktualisieren
        node.setAuthor(currentUser);
        node.setUpdated(OffsetDateTime.now());
        
        shortNewsRepository.save(node);
        
        // Frontpage aktualisieren
        final FrontPageItem fpi = frontPageService.autoDiscoverFrontPageItem(node);
        
        fpi.setPinned(false);
        fpi.setPublishFrom(OffsetDateTime.now());
        frontPageService.save(fpi);            

        return new RedirectView(node.getUrl(), false, false, false);
    }

    // DELETE
}
