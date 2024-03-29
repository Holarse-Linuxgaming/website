package de.holarse.web.controller;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.*;
import de.holarse.backend.db.repositories.*;
import de.holarse.backend.types.AttachmentGroupType;
import de.holarse.backend.types.NodeType;
import de.holarse.backend.view.*;

import static de.holarse.config.JmsQueueTypes.QUEUE_SEARCH;
import de.holarse.queues.commands.SearchRefresh;
import de.holarse.web.controller.commands.ArticleForm;
import de.holarse.web.defines.WebDefines;

import static de.holarse.web.defines.WebDefines.WIKI_ARTICLES_DEFAULT_PAGE_SIZE;
import de.holarse.web.renderer.Renderer;
import de.holarse.web.services.AttachmentService;
import de.holarse.web.services.SlugService;
import de.holarse.web.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = {"/wiki", "/wiki/" })
public class WikiController {
    
    private final static transient Logger logger = LoggerFactory.getLogger(WikiController.class);
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private ArticleRevisionRepository articleRevisionRepository;
    
    @Autowired
    private NodeSlugRepository nodeSlugRepository;
    
    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;
    
    @Autowired
    private AttachmentTypeRepository attachmentTypeRepository;

    @Autowired
    private AttachmentGroupRepository attachmentGroupRepository;
    
    @Autowired
    private SlugService slugService;
    
    @Autowired
    private TagService tagService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private Renderer renderer;
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    @GetMapping
    public ModelAndView index(@PageableDefault(sort={"title"}, value=WIKI_ARTICLES_DEFAULT_PAGE_SIZE) final Pageable pageable, final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/wiki/index");
        
        // TODO: Wieder entfernen, nur zum Testen
        mv.addObject("articles", articleRepository.findFrontpageItems(pageable));
//        
        return mv;
    }
    
    @GetMapping(value = "{slug}")
    public ModelAndView show(@PathVariable("slug") final String slug, final ModelAndView mv, final Principal principal) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/wiki/show");       

        boolean adminOverride = false;
        
        final Article article = articleRepository.findBySlug(slug).orElseThrow(EntityNotFoundException::new);
        final ArticleRevision articleRevision = article.getNodeRevision();
        final Set<Tag> tags = article.getTags();
        final List<TagGroup> relevantTagGroups = tags.stream().map(t -> t.getTagGroup()).toList();
        final NodeSlug mainSlug = nodeSlugRepository.findMainSlug(articleRevision.getNodeId(), NodeType.article).orElseThrow(EntityNotFoundException::new);
        
        // TODO
//        if (article.getNodeStatus().isDeleted() || !article.getNodeStatus().isPublished()) {
//            logger.debug("Principal: {}", principal);
//            if (principal instanceof HolarsePrincipal holarsePrincipal) {
//                if (holarsePrincipal.getAuthorities().stream().filter(a -> a.getAuthority().equalsIgnoreCase(RoleUserTypes.ROLE_USER_ADMIN)).count() > 0) {
//                    adminOverride = true;
//                }
//            }
//            
//            if (!adminOverride) {
//                mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/wiki/blocked");        
//                return mv;                
//            }
//        }

        final List<Attachment> websiteLinks = attachmentService.getAttachments(article, attachmentGroupRepository.findByCode(AttachmentGroupType.website.name()));
        final List<Attachment> videos = attachmentService.getAttachments(article, attachmentGroupRepository.findByCode(AttachmentGroupType.video.name()));
        
        // View zusammenstellen
        final ArticleView view = ArticleView.of(articleRevision, mainSlug);
        view.setNodeId(article.getNodeId());
        view.setTagList(tags.stream().map(TagView::of).sorted(Comparator.comparingInt(TagView::getWeight).reversed().thenComparing(TagView::getUseCount).reversed().thenComparing(TagView::getName)).toList());
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
        final Article article = articleRepository.findBySlug(slug).orElseThrow(EntityNotFoundException::new);
        return new RedirectView(String.format("%d/edit", article.getNodeId()));
    }

    @GetMapping(value = "{nodeId}/edit")
    public ModelAndView edit(@PathVariable final Integer nodeId, final ModelAndView mv, final Principal principal) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/wiki/form");

        var article = articleRepository.findByNodeId(nodeId).orElseThrow(EntityNotFoundException::new);
        var articleRevision = article.getNodeRevision();
        var tags = article.getTags();
        
        // Form zusammenstellen
        final ArticleForm form = new ArticleForm();
        form.setNodeId(articleRevision.getNodeId());
        form.setTitle1(articleRevision.getTitle1());
        form.setTitle2(articleRevision.getTitle2());
        form.setTitle3(articleRevision.getTitle3());
        form.setTitle4(articleRevision.getTitle4());
        form.setTitle5(articleRevision.getTitle5());
        form.setTitle6(articleRevision.getTitle6());
        form.setTitle7(articleRevision.getTitle7());
        form.setContent(articleRevision.getContent());

        // Attachments
        final List<Attachment> websiteLinks = attachmentService.getAttachments(article, attachmentGroupRepository.findByCode("website"));
        logger.debug("Links: {}", websiteLinks);
        form.setWebsiteLinks(websiteLinks.stream().map(AttachmentView::of).toList());
        
        form.setTags(tags.stream().map(t -> t.getName()).collect(Collectors.joining(", ")));
        form.setSettings(SettingsView.of(article.getNodeStatus()));

        logger.debug("WebsiteLinks: {}", form.getWebsiteLinks());
        
        return mv.addObject("form", form);
    }
    
    @Transactional
    @PostMapping("{nodeId}")
    public ModelAndView update(@PathVariable final int nodeId, @ModelAttribute("form") final ArticleForm form, final ModelAndView mv, final Authentication authentication) {
        logger.debug("Updating by form {}", form);
        
        final Article article = articleRepository.findByNodeId(nodeId).orElseThrow(EntityNotFoundException::new);

        final User author = userRepository.findById( ((HolarsePrincipal)authentication.getPrincipal()).getUser().getId() ).orElseThrow(EntityNotFoundException::new);

        // Aktualisieren und als neue Revision speichern
        final int revision = articleRevisionRepository.nextRevision();
        
        ArticleRevision articleRevision = new ArticleRevision();
        articleRevision.setNodeId(nodeId);
        articleRevision.setRevision(revision);
        articleRevision.setTitle1(form.getTitle1());
        articleRevision.setTitle2(form.getTitle2());
        articleRevision.setTitle3(form.getTitle3());
        articleRevision.setTitle4(form.getTitle4());
        articleRevision.setTitle5(form.getTitle5());
        articleRevision.setTitle6(form.getTitle6());
        articleRevision.setTitle7(form.getTitle7());
        articleRevision.setContent(form.getContent());
        articleRevision.setChangelog("TODO Changelog im Form"); // TODO Changelog im Article-Form
        articleRevision.setAuthor(author);
        articleRevisionRepository.save(articleRevision);
        
        final Set<Tag> articleTags = tagService.extract(form);

        // TODO Bilder, Anhänge 
        
        //
        // Website Links
        //
        logger.debug("Link-Attachments: {}", form.getWebsiteLinks());
        final Map<Boolean, List<AttachmentView>> websiteLinksMap = form.getWebsiteLinks().stream().collect(Collectors.partitioningBy(av -> av.isMarkAsDeleted()));
        // Die zu Löschenden verarbeiten
        attachmentRepository.deleteAllById(websiteLinksMap.get(Boolean.TRUE).stream().map(av -> av.getId()).toList());
        
        final List<Attachment> createdAndUpdatedAttachments = new ArrayList<>();
        // Die neuen Entities umwandeln und speichern
        createdAndUpdatedAttachments.addAll(websiteLinksMap.get(Boolean.FALSE).stream()
                                                                       .filter(av -> av.getId() == null)
                                                                       .filter(av -> StringUtils.isNotBlank(av.getData()))
                                                                       .map(av -> Attachment.build(av, nodeId, attachmentTypeRepository.findByCode("link")))
                                                                       .toList());
        // Die bestehenden finden und updaten
        for (final AttachmentView av : websiteLinksMap.get(Boolean.FALSE).stream().filter(av -> av.getId() != null).toList()) {
            final Attachment att = attachmentRepository.findById(av.getId()).orElseThrow(EntityNotFoundException::new);
            att.setWeight(av.getWeight());
            att.setData(av.getData());
            att.setDescription(av.getDescription());
            
            createdAndUpdatedAttachments.add(att);
        }
        attachmentRepository.saveAllAndFlush(createdAndUpdatedAttachments);
        logger.debug("Saved attachments");        
        
        // Status
        final NodeStatus nodeStatus = article.getNodeStatus();
        // TODO Nur vom Moderator zusetzen!
        nodeStatus.setPublished(form.getSettings().isPublished());
        nodeStatus.setArchived(form.getSettings().isArchived());
        nodeStatus.setCommentable(form.getSettings().isCommentable());
        nodeStatus.setDeleted(form.getSettings().isDeleted());
        
        // Artikel auf neue Revision setzen
        article.setNodeRevision(articleRevision);
        article.setTags(articleTags);        
        article.setNodeStatus(nodeStatus);
        articleRepository.saveAndFlush(article);
        logger.debug("Saved article");
        
        // Suche aktualisieren
        jmsTemplate.convertAndSend(QUEUE_SEARCH, new SearchRefresh());
        
        final NodeSlug nodeSlug = nodeSlugRepository.findMainSlug(nodeId, NodeType.article).orElseThrow(EntityNotFoundException::new);
        logger.debug("Should redirect to {}", nodeSlug.getName());
        return new ModelAndView(String.format("redirect:%s", nodeSlug.getName()));
    }

    protected Attachment createOrUpdate(final Integer nodeId, final Attachment formAttachment) {
        Attachment dbAttachment;
        if (formAttachment.getId() == null) {
            // Create
            dbAttachment = new Attachment();
            dbAttachment.setNodeId(nodeId);
            
        } else {
             dbAttachment = attachmentRepository.findById(formAttachment.getId()).orElseThrow(IllegalArgumentException::new);
        }
        
        dbAttachment.setDescription(formAttachment.getDescription());
        dbAttachment.setData(formAttachment.getData());
        dbAttachment.setWeight(formAttachment.getWeight());
        
        return dbAttachment;
    }
    
}
