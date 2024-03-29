package de.holarse.web.controller.admin;

import de.holarse.backend.db.repositories.UserRepository;
import static de.holarse.utils.ModelAndViewFactory.makeAdminLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = {"/admin", "/admin/" })
public class AdminDashboard {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    public ModelAndView index(final ModelAndView mv) {
        makeAdminLayout(mv, "sites/admin/dashboard");
        mv.addObject("userCount", userRepository.count());
        
        return mv;
    }
    
}
