package de.holarse.website.controller;

import de.holarse.website.models.User;
import de.holarse.website.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import org.apache.log4j.Logger;

@Controller
@RequestMapping("/")
public class Welcome {

    private final static transient Logger log = Logger.getLogger(Welcome.class);
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
        users.stream().forEach((s) -> {
            log.debug(s.getName() + " is here!");
        });
        model.addAttribute("users", users);
        return "/welcome/user";
    }

    @RequestMapping("adduser.html")
    public String adduser(@RequestParam(value="name", required=false, defaultValue="TestUser") String name, Model model) {
        User s = new User();
        s.setName(name);
        s.setEmail(s.getName() + "@holarse-linuxgaming.de");
        s.setPassword("12345");
        
        this.userRepository.save(s);
        model.addAttribute("user", s);
        return "/welcome/adduser";
    }

}
