package de.holarse.web.controller;

import de.holarse.web.controller.commands.LoginForm;
import de.holarse.web.defines.WebDefines;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @GetMapping("/login")
    public ModelAndView index(final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/sessions/login");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject("loginForm", new LoginForm());
        
        return mv;
    }
    
    @GetMapping("/logout")
    public String logout(final SessionStatus session) {
        SecurityContextHolder.getContext().setAuthentication(null);
        session.setComplete();
        return "/login?logout=true";
    }    

}
