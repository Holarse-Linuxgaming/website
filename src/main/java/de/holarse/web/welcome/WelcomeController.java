package de.holarse.web.welcome;

import de.holarse.backend.db.repositories.FrontPageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WelcomeController {

    final Logger log = LoggerFactory.getLogger(WelcomeController.class);
    
    @Autowired FrontPageRepository frontPageRepository;
    
    @RequestMapping({"", "index.html" })
    public String index(final ModelMap map) {
        map.addAttribute("welcomeMessage", "world");
        map.addAttribute("items", frontPageRepository.getFrontScreen());
        return "welcome/index";
    }
    
}
