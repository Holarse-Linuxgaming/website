package de.holarse.web.controller;

import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.News;
import de.holarse.backend.db.NewsRevision;
import de.holarse.backend.db.NodeSlug;
import de.holarse.backend.db.NodeStatus;
import de.holarse.backend.db.repositories.AttachmentGroupRepository;
import de.holarse.backend.db.repositories.AttachmentRepository;
import de.holarse.backend.db.repositories.AttachmentTypeRepository;
import de.holarse.backend.db.repositories.NewsCategoryRepository;
import de.holarse.backend.db.repositories.NewsRepository;
import de.holarse.backend.db.repositories.NodeSlugRepository;
import de.holarse.backend.types.AttachmentGroupType;
import de.holarse.backend.types.NodeType;
import de.holarse.backend.view.AttachmentView;
import de.holarse.backend.view.NewsView;
import de.holarse.backend.view.SettingsView;
import de.holarse.backend.view.YoutubeView;
import de.holarse.web.controller.commands.NewsForm;
import de.holarse.web.defines.WebDefines;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static de.holarse.web.defines.WebDefines.NEWS_ARTICLES_DEFAULT_PAGE_SIZE;
import de.holarse.web.renderer.Renderer;
import de.holarse.web.services.AttachmentService;
import jakarta.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = {"/news", "/news/" })
public class NewsController {

    final static transient Logger logger = LoggerFactory.getLogger(NewsController.class);
    
    @Autowired
    private NewsRepository newsRepository;
    
    @Autowired
    private NodeSlugRepository nodeSlugRepository; 
    
    @Autowired
    private Renderer renderer;    

    @Autowired
    private AttachmentService attachmentService;    
    
    @Autowired
    private AttachmentRepository attachmentRepository;
    
    @Autowired
    private AttachmentTypeRepository attachmentTypeRepository;

    @Autowired
    private AttachmentGroupRepository attachmentGroupRepository;    
    
    @Autowired
    private NewsCategoryRepository newsCategoryRepository;
    
    @GetMapping
    public ModelAndView index(@PageableDefault(sort = {"title"}, value = NEWS_ARTICLES_DEFAULT_PAGE_SIZE) final Pageable pageable, final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/news/index");

        mv.addObject("items", newsRepository.findFrontpageItems(pageable));
        return mv;
    }
    
    @GetMapping("{slug}")
    public ModelAndView show(@PathVariable("slug") final String slug, final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/news/show");
        
        final News news = newsRepository.findBySlug(slug).orElseThrow(EntityNotFoundException::new);
        
        // NodeStatus auswerten und entscheiden, ob für öffentlich sichtbar        
        final NodeStatus nodeStatus = news.getNodeStatus();
        
        final List<Attachment> websiteLinks = attachmentService.getAttachments(news, attachmentGroupRepository.findByCode(AttachmentGroupType.website.name()));
        final List<Attachment> videos = attachmentService.getAttachments(news, attachmentGroupRepository.findByCode(AttachmentGroupType.video.name()));        
        
        final NewsRevision newsRevision = news.getNodeRevision();
        final NodeSlug mainSlug = nodeSlugRepository.findMainSlug(newsRevision.getNodeId(), NodeType.news).orElseThrow(EntityNotFoundException::new);
        final NewsView view = NewsView.of(newsRevision, mainSlug);
        view.setNodeId(news.getNodeId());
        view.setContent(renderer.render(view.getContent(), null));
        //view.setSlug(mainSlug.getName());
        view.setWebsiteLinks(websiteLinks.stream().map(AttachmentView::of).toList());
        view.setYoutubeVideos(videos.stream().map(YoutubeView::of).toList());

        mv.addObject("view", view);        
        
        return mv;
    }
    
    /**
     * Weiterleitung der lesbaren URL auf die Edit-URL
     * @param slug
     * @param mv
     * @return
     */
    @GetMapping(value = "{slug}", params="edit")
    public RedirectView edit(@PathVariable final String slug, final ModelAndView mv) {
        final News news = newsRepository.findBySlug(slug).orElseThrow(EntityNotFoundException::new);
        return new RedirectView(String.format("%d/edit", news.getNodeId()));
    }
    
    @GetMapping(value = "{nodeId}/edit")
    public ModelAndView edit(@PathVariable final Integer nodeId, final ModelAndView mv, final Principal principal) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/news/form");

        var news = newsRepository.findByNodeId(nodeId).orElseThrow(EntityNotFoundException::new);
        var newsRevision = news.getNodeRevision();
        
        // Form zusammenstellen
        final NewsForm form = new NewsForm();
        form.setNodeId(newsRevision.getNodeId());
        form.setNewsCategoryId(newsRevision.getNewsCategory() != null ? newsRevision.getNewsCategory().getId() : 0);
        form.setTitle(newsRevision.getTitle());
        form.setContent(newsRevision.getContent());

        // Attachments
        final List<Attachment> websiteLinks = attachmentService.getAttachments(news, attachmentGroupRepository.findByCode("website"));
        logger.debug("Links: {}", websiteLinks);
        form.setWebsiteLinks(websiteLinks.stream().map(AttachmentView::of).toList());
        
        form.setSettings(SettingsView.of(news.getNodeStatus()));

        logger.debug("WebsiteLinks: {}", form.getWebsiteLinks());
        
        
        mv.addObject("news_categories", newsCategoryRepository.findActiveCategories());
        
        return mv.addObject("form", form);
    }    
    
}
