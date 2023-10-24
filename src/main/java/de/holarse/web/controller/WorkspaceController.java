/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.holarse.web.controller;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.auth.web.HolarseWebUserDetailsService;
import de.holarse.backend.db.Article;
import de.holarse.backend.db.ArticleRevision;
import de.holarse.backend.db.NodeStatus;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.ArticleRevisionRepository;
import de.holarse.backend.db.repositories.NodeStatusRepository;
import de.holarse.web.controller.commands.ArticleForm;
import de.holarse.web.defines.WebDefines;
import jakarta.validation.Valid;
import java.security.Principal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
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
    ArticleRepository articleRepository;
    
    @Autowired
    ArticleRevisionRepository articleRevisionRepository;
    
    @Autowired
    NodeStatusRepository nodeStatusRepository;
    
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
        mv.addObject("article", articleForm);
        
        return mv;
    }
    
    @PostMapping("add/article")
    public ModelAndView saveArticle(@Valid @ModelAttribute("article") final ArticleForm articleForm, final BindingResult result, final ModelAndView mv, final Authentication authentication) {
        if (result.hasErrors()) {
            mv.setViewName("layouts/bare");
            mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/workspace/article");            
            return mv;
        }

        final int revisionId = articleRepository.nextRevision();
        final int nodeId = articleRepository.nextNodeId();
        
        final ArticleRevision articleRevision = new ArticleRevision();
        articleRevision.setRevision(revisionId);
        articleRevision.setNodeId(nodeId);
        articleRevision.setTitle1(articleForm.getTitle1());
        articleRevision.setTitle2(articleForm.getTitle2());
        articleRevision.setTitle3(articleForm.getTitle3());
        articleRevision.setTitle4(articleForm.getTitle4());
        articleRevision.setTitle5(articleForm.getTitle5());
        articleRevision.setTitle6(articleForm.getTitle6());
        articleRevision.setTitle7(articleForm.getTitle7());
        articleRevision.setContent(articleForm.getContent());
        final ArticleRevision saved = articleRevisionRepository.saveAndFlush(articleRevision);

        log.debug("New Article Revision: {}, Node: {} for ID: {}", saved.getRevision(), saved.getNodeId(), saved.getId());
        
        final Article article = new Article();
        article.setNodeId(nodeId);
        article.setVersionId(saved.getId());
        articleRepository.saveAndFlush(article);
        
        final NodeStatus nodeStatus = new NodeStatus();
        nodeStatus.setNodeId(nodeId);
        nodeStatus.setUser(((HolarsePrincipal) authentication.getPrincipal()).getUser());
        nodeStatus.setPublished(articleForm.isPublished());
        nodeStatusRepository.saveAndFlush(nodeStatus);
        
        return new ModelAndView("redirect:/");
    }
}
