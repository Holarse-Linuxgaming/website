package de.holarse.web.controller;

import de.holarse.web.controller.commands.LoginForm;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String index(final Model model) {
        model.addAttribute("loginForm", new LoginForm());
        
        return "sites/sessions/login";
    }
    
    @GetMapping("/logout")
    public RedirectView logout(final SessionStatus session) {
        SecurityContextHolder.getContext().setAuthentication(null);
        session.setComplete();
        return new RedirectView("login?logout=true");
    }    

}
