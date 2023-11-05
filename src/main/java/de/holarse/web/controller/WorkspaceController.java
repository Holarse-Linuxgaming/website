package de.holarse.web.controller;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.ArticleRevision;
import de.holarse.backend.db.NodeSlug;
import de.holarse.backend.db.NodeStatus;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.ArticleRevisionRepository;
import de.holarse.backend.db.repositories.NodeStatusRepository;
import static de.holarse.config.JmsQueueTypes.QUEUE_SEARCH;
import de.holarse.queues.commands.SearchRefresh;
import de.holarse.web.controller.commands.ArticleForm;
import de.holarse.web.defines.WebDefines;
import de.holarse.web.services.SlugService;
import de.holarse.web.services.TagService;
import jakarta.validation.Valid;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ArticleRevisionRepository articleRevisionRepository;
    
    @Autowired
    private NodeStatusRepository nodeStatusRepository;
    
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
        articleForm.setPublished(true);
        mv.addObject("form", articleForm);
        
        return mv;
    }
    
    @Transactional
    @PostMapping("add/article")
    public ModelAndView saveArticle(@Valid @ModelAttribute("form") final ArticleForm form, final BindingResult result, final ModelAndView mv, final Authentication authentication) {
        if (result.hasErrors()) {
            mv.setViewName("layouts/bare");
            mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/workspace/article");            
            return mv;
        }

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
        articleRevisionRepository.saveAndFlush(articleRevision);

        log.debug("New Article Revision: {}, Node: {} for ID: {}", articleRevision.getRevision(), articleRevision.getNodeId(), articleRevision.getId());
      
        // Status anlegen
        final NodeStatus nodeStatus = new NodeStatus();
        nodeStatus.setNodeId(nodeId);
        nodeStatus.setPublished(form.isPublished());

        // Slug anlegen
        final NodeSlug nodeSlug = slugService.slugify(articleRevision);

        // Tags verarbeiten
        final Set<Tag> tags = tagService.extract(form);
        
        // Artikel anlegen
        final Article article = new Article();
        article.setNodeId(nodeId);
        article.setArticleRevision(articleRevision);        
        article.setNodeStatus(nodeStatus);
        article.getNodeSlugs().add(nodeSlug);
        article.setTags(tags);

        articleRepository.saveAndFlush(article);        
        
        // Suche aktualisieren
        if (nodeStatus.isPublished()) { 
            jmsTemplate.convertAndSend(QUEUE_SEARCH, new SearchRefresh());
        }        
        
        return new ModelAndView("redirect:/");
    }
}
