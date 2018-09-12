package de.holarse.web.admin;

import de.holarse.backend.db.repositories.ApiUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/api_users")
public class AdminApiUserController {

    @Autowired
    ApiUserRepository apiUserRepository;
    
    @GetMapping
    public String index(final ModelMap map) {
        map.addAttribute("users", apiUserRepository.findAll());
        return "admin/api_users/index";
    }
    
    @GetMapping("new")
    public String newApiUser(@ModelAttribute("command") final ApiUserCommand command) {
        return "admin/api_users/edit";
    }
    
}
