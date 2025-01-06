package de.holarse.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.*;
import de.holarse.backend.db.repositories.*;
import de.holarse.backend.types.AttachmentGroupType;
import de.holarse.backend.types.NodeTagsType;
import de.holarse.backend.types.NodeType;
import de.holarse.backend.types.StatIntervalType;
import de.holarse.backend.view.*;

import static de.holarse.config.JmsQueueTypes.QUEUE_SEARCH;

import de.holarse.exceptions.EntityLockedException;
import de.holarse.queues.commands.SearchRefresh;
import de.holarse.web.controller.commands.ArticleForm;
import de.holarse.web.controller.commands.FileUploadForm;
import de.holarse.web.defines.WebDefines;

import static de.holarse.web.defines.WebDefines.WIKI_ARTICLES_DEFAULT_PAGE_SIZE;
import de.holarse.web.renderer.Renderer;
import de.holarse.web.services.*;
import jakarta.persistence.EntityNotFoundException;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
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
    private ObjectStorageService objectStorageService;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private EntityLockService entityLockService;

    @Autowired
    private ArticleService articleService;

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

    @GetMapping(value = "{slug}", params="stats")
    public RedirectView stats(@PathVariable final String slug, final ModelAndView mv) {
        final Article article = articleRepository.findBySlug(slug).orElseThrow(EntityNotFoundException::new);
        return new RedirectView(String.format("%d/stats", article.getNodeId()));
    }

    @GetMapping(value = "{nodeId}/stats")
    public ModelAndView stats(@PathVariable final Integer nodeId,
                              @RequestParam(value="interval", defaultValue = "daily") final StatIntervalType statIntervalType,
                              @RequestParam(value="amount", defaultValue = "7") final int amount,
                              final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/wiki/stats");

        final Article article = articleRepository.findByNodeId(nodeId).orElseThrow(EntityNotFoundException::new);
        final ArticleRevision articleRevision = article.getNodeRevision();

        final List<NodeStatisticsView> stats = switch (statIntervalType) {
            case daily -> articleRepository.getDailyStats(article.getNodeId(), amount);
            case monthly -> articleRepository.getMonthlyStats(article.getNodeId(), amount);
        };

        stats.forEach(s -> logger.debug("Stats: time: {} amount: {}", s.getTime(), s.getAmount()));


        mv.addObject("title1", articleRevision.getTitle1());
        mv.addObject("interval", statIntervalType);
        mv.addObject("amount", amount);
        mv.addObject("stats", stats);
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

        final ArticleView view = articleService.buildArticleView(article, articleRevision);

        mv.addObject("nodeid", articleRevision.getNodeId());
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
    public ModelAndView edit(@PathVariable final Integer nodeId, final ModelAndView mv, final Authentication authentication) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/wiki/form");

        var article = articleRepository.findByNodeId(nodeId).orElseThrow(EntityNotFoundException::new);
        var articleRevision = article.getNodeRevision();
        var tags = article.getTags();

        // Versuchen den Artikel für uns zu sperren
        entityLockService.tryToLock(article, ((HolarsePrincipal)authentication.getPrincipal()).getUser());

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
        final List<Attachment> websiteLinks = attachmentService.getAttachments(article, attachmentGroupRepository.findByCode(AttachmentGroupType.website.name()));
        form.setWebsiteLinks(websiteLinks.stream().map(AttachmentView::of).toList());

        final List<Attachment> videos = attachmentService.getAttachments(article, attachmentGroupRepository.findByCode(AttachmentGroupType.video.name()));
        form.setVideos(videos.stream().map(YoutubeView::of).toList());
        final List<Attachment> screenshots = attachmentService.getAttachments(article, attachmentGroupRepository.findByCode(AttachmentGroupType.image.name()));
        form.setScreenshots(screenshots.stream().map(ScreenshotView::of).map(ssv -> objectStorageService.patchUrl(ssv)).toList());

        form.setTags(tags.stream().map(Tag::getName).collect(Collectors.joining(", ")));
        form.setSettings(SettingsView.of(article.getNodeStatus()));

        logger.debug("WebsiteLinks: {}", form.getWebsiteLinks());

        return mv.addObject("form", form);
    }

    @Transactional
    @PostMapping("{nodeId}")
    public ModelAndView update(@PathVariable final int nodeId, @ModelAttribute("form") final ArticleForm form, final ModelAndView mv, final Authentication authentication) throws JsonProcessingException {
        logger.debug("Updating by form {}", form);
        
        final Article article = articleRepository.findByNodeId(nodeId).orElseThrow(EntityNotFoundException::new);

        final User author = userRepository.findById( ((HolarsePrincipal)authentication.getPrincipal()).getUser().getId() ).orElseThrow(EntityNotFoundException::new);

        final Set<Tag> articleTags = tagService.extract(form);

        final AttachmentType attScreenshot = attachmentTypeRepository.findByCode("screenshot");
        final AttachmentType attLink = attachmentTypeRepository.findByCode("link");
        final AttachmentType attVideo = attachmentTypeRepository.findByCode("youtube");

        List<Attachment> addedScreenShots = new ArrayList<>();
        final List<FileUploadForm> screenshots = fileUploadService.readFileUpload(form);
        for (FileUploadForm screenshot : screenshots) {
            final String hashedFilename = objectStorageService.writeToCloud(screenshot);
            Attachment attachment = new Attachment();
            attachment.setData(hashedFilename);
            attachment.setAttachmentType(attScreenshot);
            attachment.setWeight(0);
            attachment.setNodeId(nodeId);
            attachment.setCreated(OffsetDateTime.now());
            addedScreenShots.add(attachment);
        }

        // Aktualisieren und als neue Revision speichern
        final int revision = articleRevisionRepository.nextRevision();
        
        ArticleRevision articleRevision = new ArticleRevision();
        articleRevision.setNodeId(nodeId);
        articleRevision.setRevision(revision);
        articleRevision.setTitle1(form.getTitle1());
        articleRevision.setTitle1Lang(WebDefines.TITLE_LANG_GERMAN);
        articleRevision.setTitle2(form.getTitle2());
        articleRevision.setTitle2Lang(WebDefines.TITLE_LANG_GERMAN);
        articleRevision.setTitle3(form.getTitle3());
        articleRevision.setTitle3Lang(WebDefines.TITLE_LANG_GERMAN);
        articleRevision.setTitle4(form.getTitle4());
        articleRevision.setTitle4Lang(WebDefines.TITLE_LANG_GERMAN);
        articleRevision.setTitle5(form.getTitle5());
        articleRevision.setTitle5Lang(WebDefines.TITLE_LANG_GERMAN);
        articleRevision.setTitle6(form.getTitle6());
        articleRevision.setTitle6Lang(WebDefines.TITLE_LANG_GERMAN);
        articleRevision.setTitle7(form.getTitle7());
        articleRevision.setTitle7Lang(WebDefines.TITLE_LANG_GERMAN);
        articleRevision.setContent(form.getContent());
        articleRevision.setChangelog("TODO Changelog im Form"); // TODO Changelog im Article-Form
        articleRevision.setAuthor(author);

        // Tags einfrieren
        var frozenTagsList = new NodeTagsType();
        frozenTagsList.setTags(articleTags.stream().map(Tag::getName).toList());
        articleRevision.setTagslist(frozenTagsList);

        articleRevisionRepository.save(articleRevision);

        // TODO Bilder, Anhänge 
        
        //
        // Website Links
        //
        logger.debug("Link-Attachments: {}", form.getWebsiteLinks());
        final Map<Boolean, List<AttachmentView>> websiteLinksMap = form.getWebsiteLinks().stream().collect(Collectors.partitioningBy(AttachmentView::isMarkAsDeleted));
        // Die zu Löschenden verarbeiten
        attachmentRepository.deleteAllById(websiteLinksMap.get(Boolean.TRUE).stream().map(AttachmentView::getId).toList());
        
        final List<Attachment> createdAndUpdatedAttachments = new ArrayList<>();
        // Die neuen Entities umwandeln und speichern
        createdAndUpdatedAttachments.addAll(websiteLinksMap.get(Boolean.FALSE).stream()
                                                                       .filter(av -> av.getId() == null)
                                                                       .filter(av -> StringUtils.isNotBlank(av.getData()))
                                                                       .map(av -> Attachment.build(av, nodeId, attLink))
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
        logger.debug("Saved attachments (links)");

        //
        // Videos
        //
        logger.debug("Video-Attachments: {}", form.getVideos());
        final Map<Boolean, List<YoutubeView>> videoMaps = form.getVideos().stream().collect(Collectors.partitioningBy(AttachmentView::isMarkAsDeleted));
        // Die zu Löschenden verarbeiten
        attachmentRepository.deleteAllById(videoMaps.get(Boolean.TRUE).stream().map(AttachmentView::getId).toList());

        final List<Attachment> createdAndUpdatedVideos = new ArrayList<>();
        // Die neuen Entities umwandeln und speichern
        createdAndUpdatedVideos.addAll(videoMaps.get(Boolean.FALSE).stream()
                .filter(av -> av.getId() == null)
                .filter(av -> StringUtils.isNotBlank(av.getData()))
                .map(av -> Attachment.build(av, nodeId, attVideo))
                .toList());
        // Die bestehenden finden und updaten
        for (final AttachmentView av : videoMaps.get(Boolean.FALSE).stream().filter(av -> av.getId() != null).toList()) {
            final Attachment att = attachmentRepository.findById(av.getId()).orElseThrow(EntityNotFoundException::new);
            att.setWeight(av.getWeight());
            att.setData(av.getData());
            att.setDescription(av.getDescription());

            createdAndUpdatedVideos.add(att);
        }
        attachmentRepository.saveAllAndFlush(createdAndUpdatedVideos);
        logger.debug("Saved attachments (videos)");

        // Neue Screenshots
        attachmentRepository.saveAllAndFlush(addedScreenShots);
        logger.debug("Saved attachments (screenshots");
        
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

        // Lock wieder lösen
        entityLockService.unlock(article, author);

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
