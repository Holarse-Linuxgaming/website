package de.holarse.web.users;

import de.holarse.backend.repository.UserRepository;
import de.holarse.backend.xml.UserLoader;
import de.holarse.view.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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

    @RequestMapping("/{userId}")
    public String show(@PathVariable("userId") Long userId, ModelMap map) {
        map.addAttribute("user", userRepository.findById(userId).orElseThrow(IllegalArgumentException::new));
        return "users/show";
    }
    
    @RequestMapping("/{login}")
    public String show(@PathVariable("login") String login, ModelMap map) {       
        map.addAttribute("user", userRepository.findByLogin(login));
        return "users/show";
    }

}