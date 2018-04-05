package de.holarse.web.comments;

import de.holarse.auth.HolarsePrincipal;
import de.holarse.backend.db.Comment;
import de.holarse.backend.db.ContentType;
import de.holarse.backend.db.repositories.CommentRepository;
import java.time.OffsetDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    
    Logger logger = LoggerFactory.getLogger(CommentController.class);
    
    @Autowired
    CommentRepository commentRepository;
    
    @PostMapping("/{nodeType}/{nodeId}/comments/")
    public String newComment(
            @PathVariable("nodeType") final String nodeType, 
            @PathVariable("nodeId") final Long nodeId, 
            @ModelAttribute final CommentCommand command, 
            final Authentication authentication) {
        logger.debug("Recieved comment for " + nodeType + " on " + nodeId);
        
        final Comment comment = new Comment();
        comment.setNodeId(nodeId);
        comment.setContent(command.getContent());
        comment.setDeleted(Boolean.FALSE);
        comment.setCreated(OffsetDateTime.now());
        comment.setContentType(ContentType.PLAIN); // TODO
        comment.setAuthor(((HolarsePrincipal) authentication.getPrincipal()).getUser());
        
        commentRepository.save(comment);
        
        return "redirect:/" + nodeType + "/" + nodeId;
    }
    
}
