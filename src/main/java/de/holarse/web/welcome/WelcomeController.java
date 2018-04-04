package de.holarse.web.welcome;

import de.holarse.services.TrafficService;
import javax.servlet.http.HttpServletRequest;
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
    
    @Autowired
    TrafficService trafficService;
    
    @RequestMapping({"", "index.html" })
    public String index(final ModelMap map, final HttpServletRequest req) {
        trafficService.saveRequest(req);
        
        map.addAttribute("welcomeMessage", "world");
        return "welcome/index";
    }
    
}
