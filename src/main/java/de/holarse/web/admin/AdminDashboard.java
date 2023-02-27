package de.holarse.web.admin;

import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.web.defines.WebDefines;
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
        mv.setViewName("layouts/admin");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/admin/dashboard");  
        
        mv.addObject("userCount", userRepository.count());
        
        return mv;
    }
    
}
