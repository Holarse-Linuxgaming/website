package de.holarse.web.users;

import de.holarse.backend.UserLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserLoader userLoader;

    @RequestMapping("/")
    public String index(ModelMap map) {
        map.addAttribute("users", userLoader.getAll());

        return "users/index";
    }

    @RequestMapping("/{userId}")
    public String show(@PathVariable("userId") Long userId, ModelMap map) {
        map.addAttribute("user", userLoader.getAll().stream().filter(u -> u.getUid().equals(userId)).findFirst().orElseThrow(IllegalArgumentException::new));
        return "users/show";
    }

}
