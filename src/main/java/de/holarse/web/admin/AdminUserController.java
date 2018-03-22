package de.holarse.web.admin;

import de.holarse.backend.db.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    @Autowired
    UserRepository userRepository;
    
    @GetMapping("/")
    public String index(final ModelMap map) {
        map.addAttribute("users", userRepository.findAll());
        return "admin/users/index";
    }
    
}
