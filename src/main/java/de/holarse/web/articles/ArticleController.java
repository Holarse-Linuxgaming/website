package de.holarse.web.articles;

import de.holarse.auth.HolarsePrincipal;
import de.holarse.backend.db.Article;
import de.holarse.backend.db.ContentType;
import de.holarse.backend.db.NodeType;
import de.holarse.backend.db.Revision;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.RevisionRepository;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.exceptions.NodeNotFoundException;
import de.holarse.exceptions.RedirectException;
import de.holarse.renderer.Renderer;
import de.holarse.search.SearchEngine;
import de.holarse.services.NodeService;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping(path = {"wiki", "articles"})
public class ArticleController {

    Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    RevisionRepository revisionRepository;

    @Autowired
    @Qualifier("elasticsearch")             
    SearchEngine searchEngine;

    @Autowired
    TagRepository tagRepository;    
    
    @Autowired
    NodeService nodeService;

    @Qualifier("wikiRenderer")
    @Autowired Renderer renderer;
    
    
    // INDEX
    @GetMapping("/")
    public String index(final Model map) {
        map.addAttribute("nodes", articleRepository.findAll());

        return "articles/index";
    }

    // NEW
    @GetMapping("/new")
    public String newArticle(final Model map, final ArticleCommand command) {
        map.addAttribute("articleCommand", command);
        map.addAttribute("contentTypes", ContentType.values());
        return "articles/form";
    }

    // CREATE
    @Transactional
    @PostMapping("/")
    public RedirectView create(@ModelAttribute final ArticleCommand command, final Authentication authentication) throws Exception {
        final Article article = nodeService.initCommentableNode(Article.class);
        // Artikelinhalt
        article.setTitle(command.getTitle());
        article.setAlternativeTitle1(command.getAlternativeTitle1());
        article.setAlternativeTitle2(command.getAlternativeTitle2());
        article.setAlternativeTitle3(command.getAlternativeTitle3());
        article.setContent(command.getContent());
        article.setContentType(command.getContentType());
        article.setBranch(StringUtils.isBlank(command.getBranch()) ? "master" : command.getBranch());

        // Tags anlegen
        Set<Tag> tags = commandToTags(command.getTags());
        tagRepository.saveAll(tags);
        article.getTags().addAll(tags);
        
        // Artikel-Metadaten
        article.setAuthor(((HolarsePrincipal) authentication.getPrincipal()).getUser());
        article.setCreated(OffsetDateTime.now());
        article.setRevision(revisionRepository.nextRevision());

        // Slug setzen
        article.setSlug(nodeService.findNextSlug(article.getTitle(), NodeType.ARTICLE));
        
        articleRepository.save(article);

        searchEngine.update(article);

        return new RedirectView("/wiki/" + URLEncoder.encode(article.getSlug(), "UTF-8"), true, false, false);
    }
   
    // SHOW by Slug
    @Transactional
    @GetMapping("/{slug}")
    public ModelAndView showBySlug(@PathVariable final String slug, final Model map) { 
        try {
            final Article article = nodeService.findArticle(slug).get();
            Hibernate.initialize(article.getTags());
            map.addAttribute("node", article);
            map.addAttribute("rendererContent", renderer.render(article.getContent()));
            return new ModelAndView("articles/show", map.asMap());
        } catch (RedirectException re) {
            return new ModelAndView(re.getRedirect());
        }
    }    

    // EDIT
    @Transactional
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable final Long id, final Model map, final ArticleCommand command) {
        final Article article = articleRepository.findById(id).get();
        Hibernate.initialize(article.getTags());        
        map.addAttribute("article", article);

        command.setTitle(article.getTitle());
        command.setAlternativeTitle1(article.getAlternativeTitle1());
        command.setAlternativeTitle2(article.getAlternativeTitle2());
        command.setAlternativeTitle3(article.getAlternativeTitle3());        
        command.setContent(article.getContent());
        command.setContentType(article.getContentType());
        command.setTags(article.getTags().stream().map(t -> t.getName()).collect(Collectors.joining(",")));
        command.setBranch(article.getBranch());

        map.addAttribute("articleCommand", command);
        map.addAttribute("contentTypes", ContentType.values());

        return "articles/form";
    }

    // UPDATE
    @Transactional
    @PostMapping("/{id}")
    public RedirectView update(@PathVariable final Long id, final ArticleCommand command, final Authentication authentication) throws UnsupportedEncodingException {
        final User currentUser = ((HolarsePrincipal) authentication.getPrincipal()).getUser();

        final Article article = articleRepository.findById(id).orElseThrow(() -> new NodeNotFoundException(id));
        
        // Artikel archivieren
        final Revision revision = new Revision();
        revision.setNodeId(article.getId());
        // TODO durch die richtige XML-Ausgabe ersetzen
        revision.setContent(article.getContent());
        revision.setAuthor(article.getAuthor());
        revision.setChangelog(article.getChangelog());
        revision.setRevision(article.getRevision());
        revision.setBranch(article.getBranch());
        revisionRepository.saveAndFlush(revision);

        // Slug ggf. archivieren
        if (!article.getTitle().equalsIgnoreCase(command.getTitle())) {
            nodeService.archivateSlug(article.getSlug(), article, NodeType.ARTICLE);
            article.setSlug(nodeService.findNextSlug(command.getTitle(), NodeType.ARTICLE));
        }        
        
        // Artikel aktualisieren
        article.setTitle(command.getTitle());
        article.setAlternativeTitle1(command.getAlternativeTitle1());
        article.setAlternativeTitle2(command.getAlternativeTitle2());
        article.setAlternativeTitle3(command.getAlternativeTitle3()); 
        article.setContent(command.getContent());
        article.setContentType(command.getContentType());
        // Branch darf nicht gewechselt werden
        article.getTags().clear();
        
        // Tags anlegen
        Set<Tag> tags = commandToTags(command.getTags());
        tagRepository.saveAll(tags);
        article.getTags().addAll(tags);

        // Artikel-Metadaten aktualisieren
        article.setAuthor(currentUser);
        article.setChangelog(command.getChangelog());
        article.setUpdated(OffsetDateTime.now());
        article.setRevision(revisionRepository.nextRevision());

        articleRepository.save(article);

        logger.debug("Starting update on search index");
        searchEngine.update(article);
        logger.debug("search index updated");

        return new RedirectView("/wiki/" + URLEncoder.encode(article.getSlug(), "UTF-8"), true, false, false);
    }
    
    protected String tagsToCommand(final Set<Tag> tags) {
        return tags.stream().map(t -> t.getName()).collect(Collectors.joining(","));
    }
    
    protected Set<Tag> commandToTags(final String tags) {
        if (StringUtils.isBlank(tags)) {
            return new HashSet<>();
        }
        return Arrays.asList(tags.split(",")).stream().map(createOrUpdateTag).collect(Collectors.toSet());
    }
    
    protected Function<String, Tag> createOrUpdateTag =  s -> tagRepository.findByNameIgnoreCase(s.trim()).orElse(new Tag(s));

    // DELETE
}
