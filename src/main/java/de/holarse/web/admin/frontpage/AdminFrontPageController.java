package de.holarse.web.admin.frontpage;

import de.holarse.backend.db.FrontPageItem;
import de.holarse.backend.db.Frontpagable;
import de.holarse.backend.db.NodeType;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.FrontPageRepository;
import de.holarse.backend.db.repositories.NewsRepository;
import de.holarse.exceptions.HolarseException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RequestMapping("/admin/frontpage")
@Controller
public class AdminFrontPageController {
    
    @Autowired NewsRepository newsRepository;
    @Autowired ArticleRepository articleRepository;
    @Autowired FrontPageRepository frontPageRepository;
    
    @Autowired FrontPageService frontPageService;
    
    @GetMapping("status")
    public FrontPageStatus getFrontPageStatus(@ModelAttribute final FrontPageStatus frontPageStatus) {
        final Optional<FrontPageItem> item = frontPageRepository.findFirstByNodeIdOrderByCreatedDesc(frontPageStatus.getNodeId());

        if (item.isPresent()) {
            frontPageStatus.setPostable(false);
            return frontPageStatus;
        }
        
        return null;        
    }
    
    @GetMapping("")
    public String index(final Model map) {
        map.addAttribute("items", frontPageRepository.findAll());
        return "admin/frontpage/index";
    }
    
    @GetMapping("post/{nodeType}/{nodeId}")
    public String createOrUpdateFrontpagePost(@PathVariable("nodeType") final NodeType nodeType, @PathVariable("nodeId") final Long nodeId, final Model map) {
        final Frontpagable frontPagable;
        switch (nodeType) {
            case NEWS:
                frontPagable = newsRepository.findById(nodeId).get();
                break;
            case ARTICLE:
                frontPagable = articleRepository.findById(nodeId).get();
                break;
            default:
                throw new IllegalStateException("Unbehandelter NodeType " + nodeType);
        }

        // Command mit bestehenden oder neuen Daten füllen
        final FrontPageCommand command;

        final FrontPageItem fpi = frontPageService.getOrUpdate(nodeId);
        if (fpi.getId() != null) {
            command = new FrontPageCommand();
            command.setId(fpi.getId());
            command.setNodeId(fpi.getNodeId());
            command.setNodeType(fpi.getNodeType());            
            command.setTitle(fpi.getTitle());
            command.setTeaser(fpi.getTeaser());
            command.setPinned(fpi.isPinned());
            command.setPublishFrom(fpi.getPublishFrom());
            command.setPublishUntil(fpi.getPublishUntil());
            command.setCooldownUntil(fpi.getCooldownUntil());
        } else {
            command = new FrontPageCommand(frontPagable);
            command.setNodeType(nodeType);                        
        }
        
        map.addAttribute("node", frontPagable);
        map.addAttribute("fpi", fpi);
        if (fpi.getUrl() == null) {
            command.setUrl(frontPagable.getUrl());
        }        
        
        command.setRepostable(fpi.isRepostable());
        
        map.addAttribute("frontPageCommand", command);
        
        return "admin/frontpage/edit";
    }

    @PostMapping("post")
    public ModelAndView postFrontPage(@ModelAttribute final FrontPageCommand command) {
        final FrontPageItem fpi = command.getId() != null ? frontPageRepository.findById(command.getId()).get() : new FrontPageItem();

        frontPageService.buildFrontPageItem(fpi, command);
        
        fpi.setPinned(command.getPinned());
        fpi.setPublishFrom(command.getPublishFrom());
        fpi.setPublishUntil(command.getPublishUntil());
                
        frontPageRepository.save(fpi);
        
        return new ModelAndView(new RedirectView("/admin/frontpage/", false, false, false));
    }
    
    @GetMapping("delete/{frontPageId}")
    public @ResponseBody String deleteFrontPage(@PathVariable final Long frontPageId) throws Exception {
        final FrontPageItem fpi = frontPageRepository.findById(frontPageId).orElseThrow(() -> new HolarseException("Keine gültige FrontpageId"));
        frontPageRepository.delete(fpi);
        
        return "OK";
    }
    
}
