package de.holarse.website.controller;

import de.holarse.website.models.User;
import de.holarse.website.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class Welcome {

    private final UserRepository userRepository;

    @Autowired
    public Welcome(UserRepository userRepository) {
        System.out.println("I AM THERE!");
        this.userRepository = userRepository;
    }

    @RequestMapping("hello.html")
    public String hello() {
        System.out.println("I AM THERE too!");
        return "/welcome/hello";
    }

    @RequestMapping("jo.html")
    public String jo() {
        return "/articles/jo";
    }

    @RequestMapping("user.html")
    public String user(Model model) {
        List<User> users = this.userRepository.findAll();
        for (User s: users) System.out.println(s.getName() + " is here!");
        model.addAttribute("users", users);
        return "/welcome/user";
    }

    @RequestMapping("adduser.html")
    public String adduser(@RequestParam(value="name", required=false, defaultValue="TestUser") String name, Model model) {
        User s = new User(name + "@holarse-linuxgaming.de", name, "123456");
        this.userRepository.save(s);
        model.addAttribute("user", s);
        return "/welcome/adduser";
    }

}
