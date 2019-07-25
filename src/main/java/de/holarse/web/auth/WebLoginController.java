package de.holarse.web.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class WebLoginController {
    
    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false, defaultValue = "false") boolean error,
                        @RequestParam(name = "logout", required = false, defaultValue = "false") boolean logout,
                        final ModelMap map) {

        map.put("error", error);
        map.put("logout", logout);
        return "auth/login";
    }

    @GetMapping("/logout")
    public String logout(final SessionStatus session) {
        SecurityContextHolder.getContext().setAuthentication(null);
        session.setComplete();
        return "redirect:/login?logout=true";
    }
    
}
