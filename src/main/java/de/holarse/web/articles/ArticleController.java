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
    public String index(Model map) {
        map.addAttribute("articles", articleRepository.findAll());
        
        return "articles/index";
    }
    
    // SHOW
    @GetMapping("/{id}")
    public String showById(@PathVariable Long id, Model map) {
        map.addAttribute("article", articleRepository.findById(id).get());
        
        return "articles/show";
    }
    
    // NEW
    @GetMapping("/new")
    public String newArticle(Model map, ArticleCommand command) {
        map.addAttribute("articleCommand", command);
        return "articles/new";
    }
    
    // CREATE
    @PostMapping("/")
    public RedirectView create(@ModelAttribute ArticleCommand command) {
        final Article article = initArticle();
        article.setTitle(command.getTitle());
        article.setContent(command.getContent());
        article.setContentType(ContentType.PLAIN);       
        
        article.setCreated(OffsetDateTime.now());
        articleRepository.saveAndFlush(article);        
        return new RedirectView("/articles/" + article.getId());
    }    
    
    // EDIT
    
    // UPDATE
    @PutMapping("/{id}")
    public RedirectView update(@PathVariable Long id, ArticleCommand command) {
        final Article article = articleRepository.findById(id).get();
        article.setTitle(command.getTitle());
        article.setContent(command.getContent());
        article.setContentType(ContentType.PLAIN);       
        
        article.setCreated(OffsetDateTime.now());
        articleRepository.saveAndFlush(article);        
        return new RedirectView("/articles/" + article.getId());
    }        
    
    // DELETE
 
    protected Article initArticle() {
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
