package de.holarse.web.admin.comments;

import de.holarse.backend.db.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/comments")
@Controller
public class AdminCommentController {
    
    @Autowired
    CommentRepository commentRepository;
    
    @GetMapping("/")
    public String index(final Model map) {
        map.addAttribute("comments", commentRepository.findAllByOrderByCreated());
        
        return "admin/comments/index";
    }
    
}
