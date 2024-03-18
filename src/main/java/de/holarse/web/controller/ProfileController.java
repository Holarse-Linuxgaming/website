package de.holarse.web.controller;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.web.defines.WebDefines;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/profile")
public class ProfileController {
    
    @GetMapping
    public ModelAndView show(@AuthenticationPrincipal HolarsePrincipal principal, final ModelAndView mv) {
        
        mv.addObject("user", principal.getUser());
        
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/profiles/show");
        return mv;        
    }
    
}
