package de.holarse.web;

import de.holarse.backend.UserLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Welcome {

    @Autowired
    UserLoader userLoader;

    @Autowired
    Example ex;
    
    final Logger log = LoggerFactory.getLogger(Welcome.class);
    
    @RequestMapping({"", "index.html" })
    public String index(final ModelMap map) {
        log.debug("I am here!");
        log.debug(ex.sayHello());
        map.addAttribute("welcomeMessage", "world");
        map.addAttribute("users", userLoader.getUsers());
        return "welcome/index";
    }
    
}
