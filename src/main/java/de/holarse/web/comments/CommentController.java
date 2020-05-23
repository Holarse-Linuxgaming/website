package de.holarse.web.comments;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.Comment;
import de.holarse.backend.db.ContentType;
import de.holarse.backend.db.NodeType;
import de.holarse.backend.db.Role;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.CommentRepository;
import de.holarse.backend.db.repositories.RoleRepository;
import de.holarse.backend.views.CommentView;
import de.holarse.factories.ViewFactory;
import de.holarse.renderer.Renderer;
import de.holarse.search.SearchEngine;
import de.holarse.services.SecurityService;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentController {

    Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired CommentRepository commentRepository;
    
    @Autowired RoleRepository roleRepository;
    
    @Autowired SecurityService securityService;
    
    @Autowired 
    @Qualifier("pgsql")            
    SearchEngine searchEngine;

    @Qualifier("htmlRenderer")
    @Autowired 
    Renderer renderer;
    
    @Autowired
    ViewFactory viewFactory;
    
    @Transactional
    @GetMapping(value = "/nodes/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentView>> comments(@RequestParam("nodeId") final Long nodeId) {
        final Collection<CommentView> comments = commentRepository.findNodeComments(nodeId).stream()
                                                                                           .map(viewFactory::fromComment)
                                                                                           .collect(Collectors.toList());
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    
    @Secured("ROLE_USER")    
    @PostMapping("/nodes/comments")
    public ResponseEntity<String> newComment(
            @ModelAttribute final CommentCommand command,
            final Authentication authentication) {

        final Comment comment = new Comment();
        comment.setNodeId(command.getNodeId());
        comment.setContent(renderer.render(command.getContent()));
        comment.setDeleted(Boolean.FALSE);
        comment.setCreated(OffsetDateTime.now());
        comment.setLocked(Boolean.FALSE);
        comment.setPublished(Boolean.TRUE);
        comment.setArchived(Boolean.FALSE);
        comment.setContentType(ContentType.WIKI);
        comment.setAuthor(((HolarsePrincipal) authentication.getPrincipal()).getUser());

        commentRepository.save(comment);
        
        return new ResponseEntity<>(comment.getId().toString(), HttpStatus.CREATED);
    }

    @Secured({"ROLE_USER", "ROLE_MODERATOR", "ROLE_ADMIN"})    
    @PostMapping("/node/{nodeId}/comments/{commentId}/delete")    
    public ResponseEntity<String> deleteComment(@PathVariable("nodeType") final NodeType nodeType,
                                @PathVariable("nodeId") final Long nodeId,
                                @PathVariable("commentId") final Long commentId,
                                final Authentication authentication) {

        final Comment comment = commentRepository.findById(commentId).get();        
        final User currentUser = ((HolarsePrincipal) authentication.getPrincipal()).getUser();

        // Der User selbst darf löschen
        if (securityService.hasEditPermissions(comment.getAuthor(), currentUser)) {
            comment.setDeleted(true);
            comment.setContent("");
            commentRepository.save(comment);
        }

        return new ResponseEntity<>("OK", HttpStatus.ACCEPTED);
    }
}
