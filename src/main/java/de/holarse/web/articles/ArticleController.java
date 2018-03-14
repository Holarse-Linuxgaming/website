package de.holarse.web.articles;

import de.holarse.auth.HolarsePrincipal;
import de.holarse.backend.db.Article;
import de.holarse.backend.db.ContentType;
import de.holarse.backend.db.Revision;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.RevisionRepository;
import de.holarse.backend.db.repositories.SearchRepository;
import de.holarse.services.NodeService;
import java.time.OffsetDateTime;
import java.util.concurrent.Callable;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
@RequestMapping(path = {"wiki", "articles"})
public class ArticleController {

    Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    RevisionRepository revisionRepository;

    @Autowired
    SearchRepository searchRepository;

    @Autowired
    NodeService nodeService;

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
    @Transactional
    @PostMapping("/")
    public RedirectView create(@ModelAttribute final ArticleCommand command, final Authentication authentication) throws Exception {
        final Article article = nodeService.initNode(Article.class);
        // Artikelinhalt
        article.setTitle(command.getTitle());
        article.setAlternativeTitles(command.getAlternativeTitles());
        article.setContent(command.getContent());
        article.setContentType(ContentType.PLAIN);

        // Artikel-Metadaten
        article.setAuthor(((HolarsePrincipal) authentication.getPrincipal()).getUser());
        article.setCreated(OffsetDateTime.now());
        article.setRevision(revisionRepository.nextRevision());

        articleRepository.save(article);

        searchRepository.update();

        return new RedirectView("/articles/" + article.getId());
    }

    // SHOW by ID
    @GetMapping("/{id}")
    public String showById(@PathVariable final Long id, final Model map) {
        map.addAttribute("article", articleRepository.findById(id).get());

        logger.debug("SHOW ID" + id);
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
    @Transactional
    @PutMapping("/{id}")
    public RedirectView update(@PathVariable final Long id, final ArticleCommand command, final Authentication authentication) {
        final User currentUser = ((HolarsePrincipal) authentication.getPrincipal()).getUser();

        final Article node = articleRepository.findById(id).get();

        // Artikel archivieren
        final Revision revision = new Revision();
        revision.setNodeId(node.getId());
        // TODO durch die richtige XML-Ausgabe ersetzen
        revision.setContent(node.getContent());
        revision.setAuthor(node.getAuthor());
        revision.setChangelog(node.getChangelog());
        revision.setRevision(node.getRevision());
        revisionRepository.saveAndFlush(revision);

        // Artikel aktualisieren
        node.setTitle(command.getTitle());
        node.setAlternativeTitles(command.getAlternativeTitles());
        node.setContent(command.getContent());
        node.setContentType(command.getContentType());

        // Artikel-Metadaten aktualisieren
        node.setAuthor(currentUser);
        node.setChangelog(command.getChangelog());
        node.setUpdated(OffsetDateTime.now());
        node.setRevision(revisionRepository.nextRevision());

        articleRepository.save(node);

        logger.debug("Starting update on search index");
        searchRepository.update();
        logger.debug("search index updated");

        return new RedirectView("/articles/" + node.getId());
    }

    // DELETE
}
