package de.holarse.web.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebLoginController {
    
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    
}
