package de.holarse.web.forums;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.types.ContentType;
import de.holarse.backend.db.Forum;
import de.holarse.backend.db.ForumThread;
import de.holarse.backend.db.repositories.ForumRepository;
import de.holarse.backend.db.repositories.ThreadRepository;
import de.holarse.exceptions.HolarseException;
import de.holarse.services.NodeService;
import de.holarse.services.WebUtils;
import java.time.OffsetDateTime;
import java.util.List;
import javax.transaction.Transactional;
import javax.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/forum")
public class ForumController {
    
    @Autowired
    ForumRepository forumRepository;
    
    @Autowired
    ThreadRepository threadRepository;
    
    @Autowired
    NodeService nodeService;
    
    @Transactional
    @GetMapping
    public String index(final ModelMap map) {
        final List<Forum> forums = forumRepository.findAllByOrderByWeight();
        forums.forEach(f -> Hibernate.initialize(f.getForumThreads()));
        map.addAttribute("forums", forums);
        
        return "forums/index";
    }
    
    @Transactional
    @GetMapping("{slug}")
    public String showForum(@PathVariable("slug") final String slug, final ModelMap map) {
        final Forum forum = forumRepository.findBySlug(slug).orElseThrow(() -> new HolarseException("Forum '" + slug + "' nicht gefunden"));
        final List<ForumThread> threads = forum.getForumThreads();
        
        threads.forEach(t -> Hibernate.initialize(t.getComments()));
        
        map.addAttribute("forum", forum);
        
        return "forums/show";
    }
    
    @GetMapping("{slug}/{postSlug}")
    public String showThread(@PathVariable("slug") final String slug, @PathVariable("postSlug") final String postSlug, final ModelMap map) throws Exception {
        final Forum forum = forumRepository.findBySlug(slug).orElseThrow(() -> new HolarseException("Forum '" + slug + "' nicht gefunden"));

        final ForumThread thread = threadRepository.findBySlugAndForum(postSlug, forum).orElseThrow(() -> new HolarseException("Thread nicht gefunden"));
        map.addAttribute("forum", forum);
        map.addAttribute("node", thread);
        
        return "threads/show";
    }
    
    @Secured("ROLE_USER")    
    @Transactional
    @GetMapping("{slug}/new")
    public String newPost(@PathVariable("slug") final String slug, final ModelMap map, final ThreadCommand command) {
        final Forum forum = forumRepository.findBySlug(slug).orElseThrow(() -> new HolarseException("Forum '" + slug + "' nicht gefunden"));        
        command.setForum(forum);
        
        map.addAttribute("forum", forum);
        map.addAttribute("contentTypes", ContentType.values());        
        map.addAttribute("command", command);
        
        return "threads/form";
    }
    
    @Secured("ROLE_USER")
    @Transactional
    @PostMapping("{slug}")
    public String createPost(@PathVariable("slug") final String slug, final ModelMap map, @ModelAttribute @Valid final ThreadCommand command, final Authentication authentication) throws Exception {
        final Forum forum = forumRepository.findBySlug(slug).orElseThrow(() -> new HolarseException("Forum '" + slug + "' nicht gefunden"));        

        // Stimmt die ForumID mit der aus dem Command überein?
        if (!forum.getId().equals(command.getForum().getId())) {
            throw new HolarseException("ForumID Mismatch");
        }
        
        final ForumThread thread = nodeService.initCommentableNode(ForumThread.class);
        thread.setAuthor(((HolarsePrincipal) authentication.getPrincipal()).getUser());
        thread.setCreated(OffsetDateTime.now());     
        
        // TODO nochmal genauer prüfen, ob das so geht, wahrscheinlich reicht der Scope innerhalb eines Forums
        thread.setTitle(command.getTitle());
        thread.setSlug(WebUtils.slugify(thread.getTitle())); 
        thread.setForum(forum);
        thread.setContent(command.getContent());
        thread.setContentType(command.getContentType() != null ? command.getContentType() : ContentType.PLAIN);
        
        final ForumThread savedThread = threadRepository.save(thread);
        
        // Jetzt wo die ID bekannt ist, Slug nochmal anpassen
        thread.setSlug(savedThread.getId() + "-" + savedThread.getSlug());
        threadRepository.save(savedThread);
        
        return "redirect:/forum/" + forum.getSlug() + "/" + thread.getSlug();
    }
    
}
