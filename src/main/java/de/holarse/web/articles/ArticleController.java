package de.holarse.web.articles;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.Article;
import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.ContentType;
import de.holarse.backend.db.NodeType;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.AttachmentRepository;
import de.holarse.backend.db.repositories.RevisionRepository;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.backend.db.types.AttachmentGroup;
import de.holarse.backend.views.ArticleView;
import de.holarse.exceptions.ErrorMode;
import de.holarse.exceptions.FlashMessage;
import de.holarse.exceptions.HolarseException;
import de.holarse.exceptions.NodeLockException;
import de.holarse.exceptions.NodeNotFoundException;
import de.holarse.exceptions.RedirectException;
import de.holarse.renderer.Renderer;
import de.holarse.search.SearchEngine;
import de.holarse.services.NodeService;
import de.holarse.services.TagService;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value = {"/html", "/articles"})
public class ArticleController {

    Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    RevisionRepository revisionRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    @Qualifier("es")            
    SearchEngine searchEngine;

    @Autowired
    TagRepository tagRepository;
    
    @Autowired
    TagService tagService;

    @Autowired
    NodeService nodeService;

    @Qualifier("htmlRenderer")
    @Autowired
    Renderer renderer;

    // INDEX
    @GetMapping
    public String index(final Model map) {
        map.addAttribute("nodes", articleRepository.findAll());

        return "articles/index";
    }

    // NEW
    @Secured("ROLE_USER")
    @GetMapping("new")
    public String newArticle(final Model map, final ArticleCommand command) {
        map.addAttribute("articleCommand", command);
        map.addAttribute("contentTypes", ContentType.values());
        return "articles/form";
    }

    // CREATE
    @Secured("ROLE_USER")
    @Transactional
    @PostMapping
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
        Set<Tag> tags = tagService.commandToTags(command.getTags());
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

        return new RedirectView("/html/" + URLEncoder.encode(article.getSlug(), "UTF-8"), true, false, false);
    }

    // SHOW by Slug
    @Transactional
    @GetMapping("{slug}")
    public ModelAndView showBySlug(@PathVariable final String slug, final ModelMap map) {             
        try {
            final Article article = nodeService.findArticle(slug).get();
            Hibernate.initialize(article.getTags());
            Hibernate.initialize(article.getAttachments());
            Hibernate.initialize(article.getComments());

            logger.debug("Attachments for Article (id: {}) - {}", new Object[] { article.getId(), article.getTitle() });
            for(Attachment att : article.getAttachments()) {
                logger.debug("att: id={}, group={}, data={}", new Object[] { att.getId(), att.getAttachmentGroup(), att.getAttachmentData()});
            }
            
            final Map<AttachmentGroup, List<Attachment>> attachmentGroups = article.getAttachments()
                    .stream()
                    .filter(a -> StringUtils.isNotBlank(a.getAttachmentData()))
                    .collect(Collectors.groupingBy(a -> a.getAttachmentGroup()));
            
            logger.debug("Content: {}", article.getContent());
                       
            ArticleView view = new ArticleView();
            view.setNodeId(article.getNodeId());
            view.setMainTitle(article.getTitle());
            view.setAlternativeTitle1(article.getAlternativeTitle1());
            view.setAlternativeTitle2(article.getAlternativeTitle2());
            view.setAlternativeTitle3(article.getAlternativeTitle3());
            view.setContent( renderer.render(article.getContent() ));
            
            view.getTags().addAll(article.getTags().stream().map(t -> t.getName()).collect(Collectors.toList()));
            view.getAttachments().putAll(attachmentGroups);
            view.getComments().addAll(article.getComments());
            
            // Eigenen Titel setzen
            map.addAttribute("title", view.getMainTitle());
            // Das View-Objekt
            map.addAttribute("view", view);
            // NodeId für die Statistik setzen
            map.addAttribute("nodeId", article.getId());
            
            return new ModelAndView("articles/show", map);
        } catch (RedirectException re) {
            return new ModelAndView(re.getRedirect());
        }
    }

    @Secured("ROLE_USER")
    @Transactional
    @GetMapping(value = "{id}/edit.json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)    
    public ResponseEntity<ArticleView> editAjax(@PathVariable final Long id, final ArticleView view, final Authentication authentication) {
        final User currentUser = ((HolarsePrincipal) authentication.getPrincipal()).getUser();
        
        final Article article = articleRepository.findById(id).get();
        
//        if (!nodeService.isEditable(article)) {
//            throw new HolarseException("Artikel kann derzeit nicht bearbeitet werden.");
        //}        
        
        // Versuchen den Artikel zum Schreiben zu sperren
        //nodeService.tryTolock(article, currentUser);
      
  //      Hibernate.initialize(article.getTags());
//        Hibernate.initialize(article.getAttachments());        

        view.setMainTitle(article.getTitle());
        view.setNodeId(article.getNodeId());
        view.setAlternativeTitle1(article.getAlternativeTitle1());
        view.setAlternativeTitle2(article.getAlternativeTitle2());
        view.setAlternativeTitle3(article.getAlternativeTitle3());
        view.setContent(article.getContent());
       
        return new ResponseEntity<>(view, HttpStatus.OK);
    }
    
    // EDIT
    @Secured("ROLE_USER")
    @Transactional
    @GetMapping("{id}/edit")
    public String edit(@PathVariable final Long id, final Model map, final Authentication authentication, final RedirectAttributes redirectAttributes) {
        final User currentUser = ((HolarsePrincipal) authentication.getPrincipal()).getUser();

        final Article article = articleRepository.findById(id).get();

        if (!nodeService.isEditable(article)) {
            throw new HolarseException("Artikel kann derzeit nicht bearbeitet werden.");
        }
        
        // Versuchen den Artikel zum Schreiben zu sperren
        try {
            nodeService.tryTolock(article, currentUser);
        } catch (NodeLockException nle) {
            final FlashMessage msg = new FlashMessage();
            msg.setThrowable(nle);
            msg.setMode(ErrorMode.DANGER);
            msg.setTitle("Artikel wird gerade berarbeitet");
            msg.setMessage("Der Artikel <em>" + article.getTitle() + "</em> wird seit " + nle.getNodeLock().getCreated() + " von " + nle.getNodeLock().getUser().getLogin() + " bearbeitet. Die Sperre gilt noch bis " + nle.getNodeLock().getLockUntil() + ".");
            msg.setSolution("Notfalls warten bis Sperrzeit vorbeit ist, oder einem Moderator Bescheid geben.");
            redirectAttributes.addFlashAttribute("flashMessage", msg);

            return "redirect:/html/" + article.getSlug();
        }

        map.addAttribute("nodeId", article.getNodeId());

        return "articles/form";
    }

    // ABORT EDIT
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @Transactional
    @GetMapping("{id}/edit/abort")
    public String editAbort(@PathVariable final Long id, final Authentication authentication) {
        final Article article = articleRepository.findById(id).get();

        // Lock lösen
        nodeService.unlock(article);

        return "redirect:" + article.getUrl();
    }

    // UPDATE
    @Secured("ROLE_USER")
    @Transactional
    @PostMapping("{id}")
    public RedirectView update(
            @PathVariable final Long id,
            final ArticleView command,
            final Authentication authentication) throws UnsupportedEncodingException, NodeLockException {
        final User currentUser = ((HolarsePrincipal) authentication.getPrincipal()).getUser();

        final Article article = articleRepository.findById(id).orElseThrow(() -> new NodeNotFoundException(id));

        // Artikel archivieren
        nodeService.createRevisionFromCurrent(article);

        // Slug ggf. archivieren
        if (!article.getTitle().equalsIgnoreCase(command.getMainTitle())) {
            nodeService.archivateSlug(article.getSlug(), article, NodeType.ARTICLE);
            article.setSlug(nodeService.findNextSlug(command.getMainTitle(), NodeType.ARTICLE));
        }

        // Artikel aktualisieren
        article.setTitle(command.getMainTitle());
        article.setAlternativeTitle1(command.getAlternativeTitle1());
        article.setAlternativeTitle2(command.getAlternativeTitle2());
        article.setAlternativeTitle3(command.getAlternativeTitle3());
        article.setContent(command.getContent());
        //article.setContentType(command.getContentType());
        // Branch darf nicht gewechselt werden
        article.getTags().clear();

        // Tags anlegen
//        Set<Tag> tags = tagService.commandToTags(command.getTags());
//        tagRepository.saveAll(tags);
//        article.getTags().addAll(tags);

        // Artikel-Metadaten aktualisieren
        article.setAuthor(currentUser);
//        article.setChangelog(command.getChangelog());
        article.setUpdated(OffsetDateTime.now());
        article.setRevision(revisionRepository.nextRevision());

        articleRepository.save(article);

        try {
            searchEngine.update(article);
        } catch (IOException e) {
            logger.warn("Aktualisieren des Suchindexes fehlgeschlagen", e);
        }

        // Lock lösen
        nodeService.unlock(article);

        return new RedirectView("/html/" + URLEncoder.encode(article.getSlug(), "UTF-8"), true, false, false);
    }

    protected String tagsToCommand(final Set<Tag> tags) {
        return tags.stream().map(t -> t.getName()).collect(Collectors.joining(","));
    }

    // DELETE
    
    // unpublish
    @Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
    @Transactional
    @GetMapping("{id}/unpublish")
    public ResponseEntity unpublish(@PathVariable("id") Long id) throws IOException {
        final Article article = articleRepository.findById(id).get();
        article.setPublished(false);

        articleRepository.save(article);

        // Suchindex aktualisieren, da nicht veröffentlichte Artikel da auch nicht auftauchen.
        searchEngine.update(article);
        
        return ResponseEntity.ok(HttpStatus.OK);
    }
    
    // publish
    @Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})
    @Transactional
    @GetMapping("{id}/publish")
    public ResponseEntity publish(@PathVariable("id") Long id) throws IOException {
        final Article article = articleRepository.findById(id).get();
        article.setPublished(true);

        articleRepository.save(article);

        // Suchindex aktualisieren, da veröffentlichte Artikel da auch auftauchen.
        searchEngine.update(article);
        
        return ResponseEntity.ok(HttpStatus.OK);
    }    
    
    // lock commenting
    @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
    @Transactional
    @GetMapping("{id}/lock_commenting")
    public ResponseEntity lockCommenting(@PathVariable("id") Long id) throws IOException {
        final Article article = articleRepository.findById(id).get();
        article.setCommentable(false);
        articleRepository.save(article);
        
        return ResponseEntity.ok(HttpStatus.OK);
    }        

    // unlock commenting
    @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
    @Transactional
    @GetMapping("{id}/unlock_commenting")
    public ResponseEntity openCommenting(@PathVariable("id") Long id) throws IOException {
        final Article article = articleRepository.findById(id).get();
        article.setCommentable(true);
        articleRepository.save(article);
        
        return ResponseEntity.ok(HttpStatus.OK);
    }     
    
    // lock
    @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
    @Transactional
    @GetMapping("{id}/lock")
    public ResponseEntity lock(@PathVariable("id") Long id) throws IOException {
        final Article article = articleRepository.findById(id).get();
        article.setLocked(true);
        articleRepository.save(article);
        
        return ResponseEntity.ok(HttpStatus.OK);
    }        

    // unlock
    @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
    @Transactional
    @GetMapping("{id}/unlock")
    public ResponseEntity unlock(@PathVariable("id") Long id) throws IOException {
        final Article article = articleRepository.findById(id).get();
        article.setLocked(false);
        articleRepository.save(article);
        
        return ResponseEntity.ok(HttpStatus.OK);
    } 
    
    // unlock
    @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
    @Transactional
    @GetMapping("{id}/archivate")
    public ResponseEntity archivate(@PathVariable("id") Long id) throws IOException {
        final Article article = articleRepository.findById(id).get();
        article.setArchived(Boolean.TRUE);
        articleRepository.save(article);
        
        return ResponseEntity.ok(HttpStatus.OK);
    }     
    
    // unarchivate
    @Secured({"ROLE_MODERATOR", "ROLE_ADMIN"})
    @Transactional
    @GetMapping("{id}/unarchivate")
    public ResponseEntity unarchivate(@PathVariable("id") Long id) throws IOException {
        final Article article = articleRepository.findById(id).get();
        article.setArchived(Boolean.FALSE);
        articleRepository.save(article);
        
        return ResponseEntity.ok(HttpStatus.OK);
    }    
    
    
}
