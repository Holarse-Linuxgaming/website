package de.holarse.web.admin.frontpage;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.FrontPageItem;
import de.holarse.backend.db.News;
import de.holarse.backend.db.NodeType;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.FrontPageRepository;
import de.holarse.backend.db.repositories.NewsRepository;
import java.time.OffsetDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping("/admin/frontpage")
@Controller
public class AdminFrontPageController {
    
    @Autowired NewsRepository newsRepository;
    @Autowired ArticleRepository articleRepository;
    @Autowired FrontPageRepository frontPageRepository;
    
    @GetMapping("status")
    public FrontPageStatus getFrontPageStatus(@ModelAttribute final FrontPageStatus frontPageStatus) {
        final Optional<FrontPageItem> item = frontPageRepository.findFirstByNodeIdOrderByCreatedDesc(frontPageStatus.getNodeId());

        if (item.isPresent()) {
            frontPageStatus.setPostable(false);
            return frontPageStatus;
        }
        
        return null;        
    }
    
    @GetMapping("post/{nodeType}/{nodeId}")
    public String createOrUpdateFrontpagePost(@PathVariable("nodeType") final NodeType nodeType, @PathVariable("nodeId") final Long nodeId, final FrontPageCommand command, final Model map) {
        final Optional<FrontPageItem> item = frontPageRepository.findFirstByNodeIdOrderByCreatedDesc(nodeId);
        
        // Ist der Cooldown schon vorbei, dann können wir ein neues posten
        final FrontPageItem fpi = item.filter(f -> f.getCooldownUntil() != null && OffsetDateTime.now().isBefore(f.getCooldownUntil())).orElseGet(FrontPageItem::new);
        
        command.setId(fpi.getId());
        command.setNodeId(fpi.getNodeId());
        command.setTitle(fpi.getTitle());
        command.setTeaser(fpi.getTeaser());
        command.setPinned(fpi.isPinned());
        command.setPublishFrom(fpi.getPublishFrom());
        command.setPublishUntil(fpi.getPublishUntil());
        command.setCooldownUntil(fpi.getCooldownUntil());
        
        switch (nodeType) {
            case NEWS:
                final News n = newsRepository.findById(nodeId).get();
                map.addAttribute("node", n);
                if (fpi.getUrl() == null) { 
                    command.setUrl(n.getUrl());
                }
                break;
            case ARTICLE:
                final Article a = articleRepository.findById(nodeId).get();
                map.addAttribute("node", a);
                if (fpi.getUrl() == null) { 
                    command.setUrl(a.getUrl());                
                }
                break;
            default:
                throw new IllegalStateException("Unbehandelter NodeType " + nodeType);
        }
        
        map.addAttribute("frontPageCommand", command);
        
        return "admin/frontpage/edit";
    }

    @PostMapping("post")
    public ModelAndView postFrontPage(@ModelAttribute final FrontPageCommand command) {
        final FrontPageItem fpi = command.getId() != null ? frontPageRepository.findById(command.getId()).get() : new FrontPageItem();
        fpi.setNodeId(command.getNodeId());
        fpi.setTitle(command.getTitle());
        fpi.setTeaser(command.getTeaser());
        fpi.setUrl(command.getUrl());
        fpi.setPinned(command.getPinned());
        fpi.setPublishFrom(command.getPublishFrom());
        fpi.setPublishUntil(command.getPublishUntil());
        
        // Standard-Cooldown von 2 Tagen
        fpi.setCooldownUntil(OffsetDateTime.now().plusDays(2));
        
        frontPageRepository.save(fpi);
        
        return new ModelAndView(new RedirectView("/admin/", false, false, true));
    }
    
}
