package de.holarse.web.news;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.types.ContentType;
import de.holarse.backend.db.News;
import de.holarse.backend.db.types.NewsCategory;
import de.holarse.backend.db.NodeType;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.NewsRepository;
import de.holarse.backend.db.repositories.RevisionRepository;
import de.holarse.backend.views.NewsView;
import de.holarse.backend.views.PaginationView;
import de.holarse.exceptions.RedirectException;
import de.holarse.factories.ViewFactory;
import de.holarse.exceptions.HolarseException;
import de.holarse.exceptions.NodeNotVisibleException;
import de.holarse.search.SearchEngine;
import de.holarse.services.NodeService;
import de.holarse.services.SecurityService;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/news")
public class NewsController {

    Logger logger = LoggerFactory.getLogger(News.class);

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    RevisionRepository revisionRepository;

    @Autowired
    SecurityService securityService;

    @Autowired
    @Qualifier("pgsql")            
    SearchEngine searchEngine;

    @Autowired
    NodeService nodeService;
    
    @Autowired
    ViewFactory viewFactory;    

    // INDEX
    @Transactional
    @GetMapping
    public String index(@PageableDefault(page = 1, size = 30) final Pageable pageable, final Model map) {
        var pagination = new PaginationView("/news", pageable, newsRepository.count());
        
        var data = newsRepository.findAll(pagination.getPageable(Sort.by(Sort.Direction.DESC, "updated", "created")))
                .stream()
                .map(n -> {
                        Hibernate.initialize(n.getComments());            
                        Hibernate.initialize(n.getAttachments());
                        
                        return n;
                })
                .map(viewFactory::fromNews)
                .collect(Collectors.toList());
        
        
        map.addAttribute("views", data);
        map.addAttribute("pagination", pagination);

        return "news/index";
    }

    // NEW
    @Deprecated
    @Secured({"ROLE_REPORTER", "ROLE_ADMIN"})           
    @GetMapping("new")
    public String newArticle(final Model map, final NewsCommand command) {
        map.addAttribute("newsCommand", command);
        map.addAttribute("categories", NewsCategory.values());
        map.addAttribute("contentTypes", ContentType.values());
        
        return "news/form";
    }

    // CREATE
    @Deprecated
    @Secured({"ROLE_REPORTER", "ROLE_ADMIN"})            
    @Transactional    
    @PostMapping  
    public RedirectView create(@ModelAttribute final NewsCommand command, final Authentication authentication) throws Exception {
        final News node = nodeService.initCommentableNode(News.class);
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

        // Slug setzen
        node.setSlug(nodeService.findNextSlug(node.getTitle(), NodeType.NEWS));        
        
        newsRepository.save(node);

        searchEngine.update(node);

        return new RedirectView("/news/" + node.getId());
    }

    // SHOW by Slug
    @Transactional
    @GetMapping("{slug}")
    public ModelAndView show(@PathVariable("slug") final String slug, final Model map, final Authentication authentication) {  
        final News node = nodeService.findNews(slug).get();
        Hibernate.initialize(node.getComments());            
        Hibernate.initialize(node.getAttachments());
        
        if (!nodeService.isPublicViewable(node)) {
            if (!securityService.hasClearance(authentication, "MODERATOR")) {
                throw new NodeNotVisibleException("News ist noch nicht freigegeben.", node.getNodeId());
            }
        }
        
        // View-Objekt erzeugen
        NewsView view = viewFactory.fromNews(node);
        
        // View übergeben
        map.addAttribute("view", view);
        
        // NodeId für die Statistik einfügen
        map.addAttribute("nodeId", node.getId());
        
        return new ModelAndView("news/show");
    }

    // EDIT
    @Secured({"ROLE_REPORTER", "ROLE_ADMIN"})           
    @GetMapping("{id}/edit")
    public String edit(@PathVariable final Long id, final Model map, final NewsCommand command, final Authentication authentication) {
        final User currentUser = ((HolarsePrincipal) authentication.getPrincipal()).getUser();
        
        final News news = newsRepository.findById(id).get();

        // Versuchen die News zum Schreiben zu sperren
        nodeService.tryTolock(news, currentUser);  
        
        map.addAttribute("node", news);

        command.setTitle(news.getTitle());
        command.setSubtitle(news.getSubtitle());
        command.setContent(news.getContent());
        command.setCategory(news.getCategory());
        command.setContentType(news.getContentType());

        map.addAttribute("newsCommand", command);
        map.addAttribute("categories", NewsCategory.values());
        map.addAttribute("contentTypes", ContentType.values());

        return "news/form";
    }

    // UPDATE
    @Secured({"ROLE_REPORTER", "ROLE_ADMIN"})          
    @Transactional
    @PostMapping("{id}")
    public RedirectView update(@PathVariable final Long id, final NewsCommand command, final Authentication authentication) {
        final User currentUser = ((HolarsePrincipal) authentication.getPrincipal()).getUser();

        final News news = newsRepository.findById(id).get();
      
        // News archivieren
        nodeService.createRevisionFromCurrent(news);        

        // Slug ggf. archivieren
        if (!news.getTitle().equalsIgnoreCase(command.getTitle())) {
            nodeService.archivateSlug(news.getSlug(), news, NodeType.NEWS);
            news.setSlug(nodeService.findNextSlug(command.getTitle(), NodeType.NEWS));
        }           
        
        // News aktualisieren
        news.setTitle(command.getTitle());
        news.setSubtitle(command.getSubtitle());
        news.setContent(command.getContent());
        news.setCategory(command.getCategory());
        news.setContentType(command.getContentType());

        // News-Metadaten aktualisieren
        news.setAuthor(currentUser);
        news.setChangelog(command.getChangelog());
        news.setUpdated(OffsetDateTime.now());
        news.setRevision(revisionRepository.nextRevision());
        
        newsRepository.save(news);

        try {
            searchEngine.update(news);
        } catch (IOException e) {
            logger.warn("Aktualisieren des Suchindexes fehlgeschlagen", e);
        }

        // Sperre lösen
        nodeService.unlock(news);
        
        return new RedirectView("/news/" + news.getId());
    }

    // DELETE
}
