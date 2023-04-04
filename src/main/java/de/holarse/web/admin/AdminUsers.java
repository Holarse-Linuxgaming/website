package de.holarse.web.admin;

import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.view.UserView;
import de.holarse.web.defines.WebDefines;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminUsers {

    private final static transient Logger log = LoggerFactory.getLogger(AdminUsers.class);
    
    @Autowired
    private UserRepository userRepository;    
    
    @GetMapping("/admin/users")
    public ModelAndView index(final Pageable pageable, final ModelAndView mv) {
        mv.setViewName("layouts/admin");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/admin/users");
        mv.addObject("users", userRepository.findAll(pageable).map(UserView::of));
        return mv;
    }

}
