package de.holarse.web.users;

import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("users", userRepository.findAll());
        return "users/index";
    }

    @RequestMapping("{userId:Long}")
    public String show(@PathVariable("userId") Long userId, ModelMap map) {
        return show(userRepository.findById(userId).get(), map);
    }
    
    @RequestMapping("{login}")
    public String show(@PathVariable("login") String login, ModelMap map) {       
        return show(userRepository.findByLogin(login), map);
    }
    
    protected String show(final User user, final ModelMap map) {
        map.addAttribute("user", user);
        return "users/show";        
    }
    
}