package de.holarse.web.admin;

import de.holarse.backend.db.repositories.UserRepository;
import static de.holarse.utils.ModelAndViewFactory.makeAdminLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminDashboard {
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping(value = "/admin")
    public ModelAndView index(final ModelAndView mv) {
        makeAdminLayout(mv, "sites/admin/dashboard");
        mv.addObject("userCount", userRepository.count());
        
        return mv;
    }
    
}
