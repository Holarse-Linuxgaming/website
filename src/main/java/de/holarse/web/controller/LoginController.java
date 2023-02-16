package de.holarse.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @GetMapping
    public String index(final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");

        return "partials/login";
    }
    
    @GetMapping("/logout")
    public String logout(final SessionStatus session) {
        SecurityContextHolder.getContext().setAuthentication(null);
        session.setComplete();
        return "redirect:/login?logout=true";
    }    

}
