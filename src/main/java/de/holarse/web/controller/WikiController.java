package de.holarse.web.controller;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.ArticleRevision;
import de.holarse.backend.db.NodeSlug;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.ArticleRevisionRepository;
import de.holarse.backend.db.repositories.NodeSlugRepository;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.backend.view.ArticleView;
import de.holarse.backend.view.TagView;
import de.holarse.web.controller.commands.ArticleForm;
import de.holarse.web.defines.WebDefines;
import static de.holarse.web.defines.WebDefines.WIKI_ARTICLES_DEFAULT_PAGE_SIZE;
import de.holarse.web.renderer.Renderer;
import de.holarse.web.services.SlugService;
import jakarta.persistence.EntityNotFoundException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
    private SlugService slugService;
    
    @Autowired
    private Renderer renderer;
    
    @GetMapping
    public ModelAndView index(@PageableDefault(sort={"title1"}, value=WIKI_ARTICLES_DEFAULT_PAGE_SIZE) final Pageable pageable, final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/wiki/index");
        
        // TODO: Wieder entfernen, nur zum Testen
        mv.addObject("articles", articleRepository.listCurrentArticles(pageable));
//        
        return mv;
    }
    
    @GetMapping("{slug}")
    public ModelAndView show(@PathVariable("slug") final String slug, final ModelAndView mv, final Principal principal) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/wiki/show");       

        boolean adminOverride = false;
        
        final Article article = articleRepository.findBySlug(slug).orElseThrow(EntityNotFoundException::new);
        final ArticleRevision articleRevision = article.getArticleRevision();
        final Set<Tag> tags = article.getTags();
        //final NodeSlug mainSlug = nodeSlugRepository.findMainSlug(article.getNodeId()).orElseThrow(EntityNotFoundException::new);
        
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

        // View zusammenstellen
        final ArticleView view = ArticleView.of(articleRevision);
        view.setNodeId(article.getNodeId());
        view.setTags(tags.stream().map(t -> t.getName()).collect(Collectors.joining(", ")));
        view.setTagList(tags.stream().map(TagView::of).toList()); // TODO Sort by weight
        view.setContent(renderer.render(view.getContent(), null));
        //view.setSlug(mainSlug.getName());
//        
        mv.addObject("view", view);
        
        return mv;
    }
    
    @GetMapping("{nodeId}/edit")
    public ModelAndView edit(@PathVariable("nodeId") final Integer nodeId, final ModelAndView mv, final Principal principal) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/wiki/form");

        var article = articleRepository.findByNodeId(nodeId).orElseThrow(EntityNotFoundException::new);
        var articleRevision = article.getArticleRevision();
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
        
        form.setTags(tags.stream().map(t -> t.getName()).collect(Collectors.joining(", ")));

        return mv.addObject("form", form);
    }
    
    @Transactional
    @PostMapping("{nodeId}")
    public ModelAndView update(@PathVariable("nodeId") final int nodeId, @ModelAttribute("form") final ArticleForm form, final ModelAndView mv, final Principal principal) {
        final Article article = articleRepository.findByNodeId(nodeId).orElseThrow(EntityNotFoundException::new);
        
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
        articleRevisionRepository.save(articleRevision);
        
        final Set<Tag> articleTags = new HashSet<>();
        
        // Tags auslesen, in Entities umwandeln und ggf. erzeugen
        final String[] tagNames = form.getTags().split(",");
        for (final String tagName : tagNames) {
            final Tag tag = tagRepository.findByName(tagName.trim()).orElseGet(() -> new Tag(tagName.trim()));
            articleTags.add(tag);
        }
        // Alle Tags mit Slugs versehen, bestehende werden nicht angefasst
        articleTags.stream().forEach(t -> slugService.slugify(t));

        // TODO Bilder, Anhänge        
        
        // Artikel auf neue Revision setzen
        article.setArticleRevision(articleRevision);
        article.setTags(articleTags);        
        articleRepository.saveAndFlush(article);
        
        final NodeSlug nodeSlug = nodeSlugRepository.findMainSlug(nodeId).orElseThrow(EntityNotFoundException::new);
        return new ModelAndView(String.format("redirect:{}", nodeSlug.getName()));
    }    
    
}
