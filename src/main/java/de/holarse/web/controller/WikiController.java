package de.holarse.web.controller;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.repositories.ArticleRevisionRepository;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.backend.view.ArticleView;
import de.holarse.config.RoleUserTypes;
import de.holarse.web.defines.WebDefines;
import static de.holarse.web.defines.WebDefines.WIKI_ARTICLES_DEFAULT_PAGE_SIZE;
import de.holarse.web.renderer.Renderer;
import jakarta.persistence.EntityNotFoundException;
import java.security.Principal;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.UserRoleAuthorizationInterceptor;

@Controller
@RequestMapping("/wiki")
public class WikiController {
    
    private final static transient Logger logger = LoggerFactory.getLogger(WikiController.class);
    
    @Autowired
    private ArticleRevisionRepository articleRevisionRepository;
    
    @Autowired
    private TagRepository tagRepository;
    
    @Autowired
    private Renderer renderer;
    
    @GetMapping
    public ModelAndView index(@PageableDefault(sort={"title1"}, value=WIKI_ARTICLES_DEFAULT_PAGE_SIZE) final Pageable pageable, final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/wiki/overview");
        
        // TODO: Wieder entfernen, nur zum Testen
        mv.addObject("articles", articleRevisionRepository.findAllCurrent(pageable));
        
        return mv;
    }
    
    @GetMapping("{slug}")
    public ModelAndView show(@PathVariable("slug") final String slug, final ModelAndView mv, final Principal principal) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/wiki/show");       

        boolean adminOverride = false;
        
        var article = articleRevisionRepository.findBySlug(slug).orElseThrow(EntityNotFoundException::new);
        // TODO
        if (article.getNodeStatus().isDeleted() || !article.getNodeStatus().isPublished()) {
            logger.debug("Principal: {}", principal);
            if (principal instanceof HolarsePrincipal holarsePrincipal) {
                if (holarsePrincipal.getAuthorities().stream().filter(a -> a.getAuthority().equalsIgnoreCase(RoleUserTypes.ROLE_USER_ADMIN)).count() > 0) {
                    adminOverride = true;
                }
            }
            
            if (!adminOverride) {
                mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/wiki/blocked");        
                return mv;                
            }
        }

        // View zusammenstellen
        final ArticleView view = ArticleView.of(article);
        view.setTags(tagRepository.findByNodeId(article.getNodeId(), Sort.by("weight")));
        view.setContent(renderer.render(view.getContent(), null));
        
        mv.addObject("view", view);
        
        return mv;
    }
    
}
