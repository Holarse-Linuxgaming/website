package de.holarse.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Welcome {

    final Logger log = LoggerFactory.getLogger(Welcome.class);
    
    @RequestMapping("index.html")
    public String index(final ModelMap map) {
        log.debug("I am here!");
        map.addAttribute("welcomeMessage", "world");
        return "welcome/index";
    }
    
}
