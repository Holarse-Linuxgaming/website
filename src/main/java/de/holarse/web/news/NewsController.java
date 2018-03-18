package de.holarse.web.news;

import de.holarse.auth.HolarsePrincipal;
import de.holarse.backend.db.ContentType;
import de.holarse.backend.db.News;
import de.holarse.backend.db.NewsCategory;
import de.holarse.backend.db.Revision;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.NewsRepository;
import de.holarse.backend.db.repositories.RevisionRepository;
import de.holarse.backend.db.repositories.SearchRepository;
import de.holarse.services.NodeService;
import de.holarse.web.comments.CommentCommand;
import java.time.OffsetDateTime;
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
@RequestMapping(path = {"news"})
public class NewsController {

    Logger logger = LoggerFactory.getLogger(News.class);

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    RevisionRepository revisionRepository;

    @Autowired
    SearchRepository searchRepository;

    @Autowired
    NodeService nodeService;

    // INDEX
    @GetMapping("/")
    public String index(final Model map) {
        map.addAttribute("nodes", newsRepository.findAll());

        return "news/index";
    }

    // NEW
    @GetMapping("/new")
    public String newArticle(final Model map, final NewsCommand command) {
        map.addAttribute("newsCommand", command);
        map.addAttribute("categories", NewsCategory.values());
        map.addAttribute("contentTypes", ContentType.values());
        
        return "news/new";
    }

    // CREATE
    @Transactional    
    @PostMapping("/")   
    public RedirectView create(@ModelAttribute final NewsCommand command, final Authentication authentication) throws Exception {
        final News node = nodeService.initNode(News.class);
        // Node-Inhalt
        node.setTitle(command.getTitle());
        node.setSubtitle(command.getSubtitle());
        node.setCategory(command.getCategory());
        node.setContent(command.getContent());
        node.setContentType(ContentType.PLAIN);

        // Node-Metadaten
        node.setAuthor(((HolarsePrincipal) authentication.getPrincipal()).getUser());
        node.setCreated(OffsetDateTime.now());
        node.setRevision(revisionRepository.nextRevision());

        newsRepository.save(node);

        searchRepository.update();

        return new RedirectView("/news/" + node.getId());
    }

    // SHOW by ID
    @GetMapping("/{id}")
    public String showById(@PathVariable final Long id, final Model map, final CommentCommand commentCommand) {
        map.addAttribute("node", newsRepository.findById(id).get());
        map.addAttribute("commentCommand", commentCommand);

        logger.debug("SHOW ID" + id);
        return "news/show";
    }

    // EDIT
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable final Long id, final Model map, final NewsCommand command) {
        final News node = newsRepository.findById(id).get();
        map.addAttribute("node", node);

        command.setTitle(node.getTitle());
        command.setSubtitle(node.getSubtitle());
        command.setContent(node.getContent());
        command.setCategory(node.getCategory());
        command.setContentType(node.getContentType());

        map.addAttribute("newsCommand", command);
        map.addAttribute("categories", NewsCategory.values());
        map.addAttribute("contentTypes", ContentType.values());

        return "news/edit";
    }

    // UPDATE
    @Transactional
    @PutMapping("/{id}")
    public RedirectView update(@PathVariable final Long id, final NewsCommand command, final Authentication authentication) {
        final User currentUser = ((HolarsePrincipal) authentication.getPrincipal()).getUser();

        final News node = newsRepository.findById(id).get();

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
        node.setSubtitle(command.getSubtitle());
        node.setContent(command.getContent());
        node.setCategory(command.getCategory());
        node.setContentType(command.getContentType());

        // Artikel-Metadaten aktualisieren
        node.setAuthor(currentUser);
        node.setChangelog(command.getChangelog());
        node.setUpdated(OffsetDateTime.now());
        node.setRevision(revisionRepository.nextRevision());

        newsRepository.save(node);

        searchRepository.update();

        return new RedirectView("/news/" + node.getId());
    }

    // DELETE
}
