package de.holarse.web.articles;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.ContentType;
import de.holarse.backend.db.repositories.ArticleRepository;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(path= {"wiki", "articles"})
public class ArticleController {
    
    @Autowired
    ArticleRepository articleRepository;

    // INDEX
    @GetMapping("/")
    public String index(final Model map) {
        map.addAttribute("articles", articleRepository.findAll());
        
        return "articles/index";
    }
       
    // NEW
    @GetMapping("/new")
    public String newArticle(final Model map, final ArticleCommand command) {
        map.addAttribute("articleCommand", command);
        return "articles/new";
    }
    
    // CREATE
    @PostMapping("/")
    public RedirectView create(@ModelAttribute final ArticleCommand command) {
        final Article article = initNewArticle();
        article.setTitle(command.getTitle());
        article.setContent(command.getContent());
        article.setContentType(ContentType.PLAIN);       
        
        article.setCreated(OffsetDateTime.now());
        articleRepository.saveAndFlush(article);        
        return new RedirectView("/articles/" + article.getId());
    }    
    
    // SHOW by ID
    @GetMapping("/{id}")
    public String showById(@PathVariable final Long id, final Model map) {
        map.addAttribute("article", articleRepository.findById(id).get());
        
        return "articles/show";
    }    
    
    // EDIT
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable final Long id, final Model map, final ArticleCommand command) {
        final Article article = articleRepository.findById(id).get();
        map.addAttribute("article", article);
        
        command.setTitle(article.getTitle());
        command.setContent(article.getContent());  
        command.setContentType(article.getContentType());
        
        map.addAttribute("articleCommand", command);
        map.addAttribute("contentTypes", ContentType.values());
        
        return "articles/edit";
    }       
    
    // UPDATE
    @PutMapping("/{id}")
    public RedirectView update(@PathVariable final Long id, final ArticleCommand command) {
        final Article article = articleRepository.findById(id).get();
        article.setTitle(command.getTitle());
        article.setContent(command.getContent());
        article.setContentType(command.getContentType());       
        
        article.setCreated(OffsetDateTime.now());
        articleRepository.saveAndFlush(article);        
        return new RedirectView("/articles/" + article.getId());
    }        
    
    // DELETE
 
    protected Article initNewArticle() {
        final Article article = new Article();
        article.setCommentable(Boolean.TRUE);
        article.setArchived(Boolean.FALSE);
        article.setDeleted(Boolean.FALSE);
        article.setDraft(Boolean.FALSE);
        article.setLocked(Boolean.FALSE);
        // Wahrscheinlich später auf false, wenn Vorschau Standard ist.
        article.setPublished(Boolean.TRUE);
        
        return article;
    }
    
}
