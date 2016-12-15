package web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import web.entities.User;
import web.services.UserService;

@Controller
@RequestMapping(value = "users")
public class UserController {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private UserService us;

    @RequestMapping("reload")
    @ResponseStatus(HttpStatus.ACCEPTED)
    void reload() throws Exception {
        us.initFromDisk();
    }
    
    @RequestMapping("{uid}")
    String article(final @PathVariable("uid") long uid, final ModelMap map) throws Exception {
        logger.debug("Asking for user uid {}", uid);
        
        final User u = us.findById(uid);
        logger.debug("{}", u);
        
        map.addAttribute("user", u);
        
        return "user";
    }    
    
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping("{uid}/reload")
    void reloadArticle(final @PathVariable("uid") long uid, final ModelMap map) throws Exception {
        logger.debug("Asking to reload user uid {}", uid);
        
        us.reloadFromDisk(uid);
    }        
    
}
