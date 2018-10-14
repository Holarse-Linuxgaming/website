package de.holarse.web.contact;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("contact")
public class ContactController {

    Logger logger = LoggerFactory.getLogger(ContactController.class);
    
    @GetMapping
    public String index(final ContactCommand command, ModelMap map, final Authentication authentication) {
        // Automatisch Nutzername nehmen, wenn eingeloggt.
        if (authentication != null) {
            final User user = ((HolarsePrincipal) authentication.getPrincipal()).getUser();
            command.setEmail(user.getEmail());
            command.setName(user.getLogin());
        }

        map.addAttribute("contactCommand", command);

        return "contact/index";
    }
    
    @PostMapping("send")
    public String send(@Validated @ModelAttribute final ContactCommand command, ModelMap map) {
        logger.debug("Recieved message: {}", command);
        map.addAttribute("name", command.getName());
        return "contact/send";
    }

}
