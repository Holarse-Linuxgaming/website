package de.holarse.web.welcome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WelcomeController {

    final Logger log = LoggerFactory.getLogger(WelcomeController.class);
    
    @RequestMapping({"", "index.html" })
    public String index(final ModelMap map) {
        map.addAttribute("welcomeMessage", "world");
        return "welcome/index";
    }
    
}
