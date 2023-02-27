package de.holarse.web.admin;

import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.web.defines.WebDefines;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminUsers {

    @Autowired
    private UserRepository userRepository;    
    
    @GetMapping("/admin/users")
    public ModelAndView index(final ModelAndView mv) {
        mv.setViewName("layouts/admin");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/admin/users");
        mv.addObject("users", userRepository.findAll());

        return mv;
    }

}
