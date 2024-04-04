package de.holarse.web.controller;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.*;
import de.holarse.backend.db.repositories.*;

import static de.holarse.config.JmsQueueTypes.QUEUE_SEARCH;
import de.holarse.queues.commands.SearchRefresh;
import de.holarse.web.controller.commands.ArticleForm;
import de.holarse.web.controller.commands.NewsForm;
import de.holarse.web.defines.WebDefines;
import de.holarse.web.services.SlugService;
import de.holarse.web.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = {"/workspace", "/workspace/"})
public class WorkspaceController {
    
    private final static transient Logger log = LoggerFactory.getLogger(WorkspaceController.class);
    
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private NewsRepository newsRepository;
    
    @Autowired
    private ArticleRevisionRepository articleRevisionRepository;

    @Autowired
    private NewsRevisionRepository newsRevisionRepository;
    
    @Autowired
    private NodeStatusRepository nodeStatusRepository;

    @Autowired
    private NewsCategoryRepository newsCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SlugService slugService;
    
    @Autowired
    private TagService tagService;
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    /**
     * Übersicht über den eigenen Workspace
     * @param mv
     * @return 
     */
    @GetMapping
    public ModelAndView index(final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/workspace/index");        
        return mv;
    }

    @GetMapping("add")
    public ModelAndView add(final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/workspace/add");        
        return mv;
    }    

    @GetMapping("add/article")
    public ModelAndView addArticle(final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/workspace/article");
        
        final ArticleForm articleForm = new ArticleForm();
        articleForm.getSettings().setPublished(true);
        mv.addObject("form", articleForm);
        
        return mv;
    }

    @GetMapping("add/news")
    public ModelAndView addNews(final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/workspace/news");

        final NewsForm form = new NewsForm();
        form.getSettings().setPublished(true);

        final List<NewsCategory> newsCategories = newsCategoryRepository.findActiveCategories();

        mv.addObject("news_categories", newsCategories);
        mv.addObject("form", form);

        return mv;
    }

    @Transactional
    @PostMapping("add/news")
    public ModelAndView saveNews(@Valid @ModelAttribute("form") final NewsForm form, final BindingResult result, final ModelAndView mv, final Authentication authentication) {
        if (result.hasErrors()) {
            mv.setViewName("layouts/bare");
            mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/workspace/news");
            return mv;
        }

        final User author = userRepository.findById( ((HolarsePrincipal)authentication.getPrincipal()).getUser().getId() ).orElseThrow(EntityNotFoundException::new);
        final NewsCategory newsCategory = newsCategoryRepository.findById(form.getNewsCategoryId()).orElseThrow(EntityNotFoundException::new);

        final int nodeId = newsRepository.nextNodeId();
        final int revision = newsRevisionRepository.nextRevision();

        // NewsRevision anlegen aus Form
        final NewsRevision newsRevision = new NewsRevision();
        newsRevision.setNodeId(nodeId);
        newsRevision.setRevision(revision);
        newsRevision.setTitle(form.getTitle());
        newsRevision.setContent(form.getContent());
        newsRevision.setAuthor(author);
        newsRevision.setNewsCategory(newsCategory);
        newsRevision.setChangelog("Angelegt");
        newsRevisionRepository.saveAndFlush(newsRevision);

        log.debug("NewsRevision: {}", newsRevision);

        // NodeStatus anlegen
        final NodeStatus nodeStatus = new NodeStatus();
        nodeStatus.setNodeId(nodeId);
        nodeStatus.setPublished(form.getSettings().isPublished());

        // Slug anlegen
        final NodeSlug nodeSlug = slugService.slugify(newsRevision);

        // News anlegen
        final News news = new News();
        news.setNodeId(nodeId);
        news.setNodeRevision(newsRevision); // TODO Referenz auf NewsRevision wird noch nicht gespeichert
        news.setNodeStatus(nodeStatus);
        news.getNodeSlugz().add(nodeSlug);

        newsRepository.saveAndFlush(news);

        log.debug("News: {}", news);

        // Suche aktualisieren
      if (nodeStatus.isPublished()) {
            jmsTemplate.convertAndSend(QUEUE_SEARCH, new SearchRefresh());
      }

        return new ModelAndView("redirect:/", HttpStatus.CREATED);
    }
    
    @Transactional
    @PostMapping("add/article")
    public ModelAndView saveArticle(@Valid @ModelAttribute("form") final ArticleForm form, final BindingResult result, final ModelAndView mv, final Authentication authentication) {
        if (result.hasErrors()) {
            mv.setViewName("layouts/bare");
            mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/workspace/article");            
            return mv;
        }

        final User author = userRepository.findById( ((HolarsePrincipal)authentication.getPrincipal()).getUser().getId() ).orElseThrow(EntityNotFoundException::new);

        final int nodeId = articleRepository.nextNodeId();
        final int revision = articleRevisionRepository.nextRevision();
        
        // Version des Artikels hinterlegen
        final ArticleRevision articleRevision = new ArticleRevision();
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
        articleRevision.setAuthor(author);
        articleRevision.setChangelog("Angelegt");
        articleRevisionRepository.saveAndFlush(articleRevision);

        log.debug("New Article Revision: {}, Node: {} for ID: {}", articleRevision.getRevision(), articleRevision.getNodeId(), articleRevision.getId());
      
        // Status anlegen
        final NodeStatus nodeStatus = new NodeStatus();
        nodeStatus.setNodeId(nodeId);
        nodeStatus.setPublished(form.getSettings().isPublished());

        // Slug anlegen
        final NodeSlug nodeSlug = slugService.slugify(articleRevision);

        // Tags verarbeiten
        final Set<Tag> tags = tagService.extract(form);
        
        // Artikel anlegen
        final Article article = new Article();
        article.setNodeId(nodeId);
        article.setNodeRevision(articleRevision);
        article.setNodeStatus(nodeStatus);
        article.getNodeSlugs().add(nodeSlug);
        article.setTags(tags);

        articleRepository.saveAndFlush(article);        
        
        // Suche aktualisieren
        if (nodeStatus.isPublished()) { 
            jmsTemplate.convertAndSend(QUEUE_SEARCH, new SearchRefresh());
        }        
        
        return new ModelAndView("redirect:/", HttpStatus.CREATED);
    }
}
