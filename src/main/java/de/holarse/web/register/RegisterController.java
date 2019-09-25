package de.holarse.web.register;

import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserRepository;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/register")
@Controller
public class RegisterController {

    Logger log = LoggerFactory.getLogger(RegisterService.class);

    @Autowired
    private UserRepository ur;
    
    @Autowired
    private RegisterService registerService;

    @GetMapping
    public String form(final ModelMap map, final RegisterCommand cmd) {
        map.addAttribute("form", cmd);

        return "register/form";
    }

    @PostMapping
    public String register(@ModelAttribute @Valid final RegisterCommand cmd, final ModelMap map, BindingResult bindingResult) {
        // Prüfen, ob die Email-Adresse oder der Name bereits belegt ist. Oder ob die beiden
        // Passwörter nicht übereinstimmend passen
        if (bindingResult.hasErrors()) {
            return "register/form";
        }
        
        if (!cmd.getPassword().equals(cmd.getPasswordConfirmation())) {
            log.warn("Passwort und Bestätigung nicht identisch.");
            return "register/form";
        }
        
        if (ur.existsByLoginOrEmail(cmd.getLogin(),cmd.getEmail())) {
            log.warn("Benutzer oder Email existieren bereits.");
            return "register/form";
        }
        
        final User user = registerService.createAccount(cmd);
        map.addAttribute("verificationUser", user);
        
        return "register/done";
    }
    
    @GetMapping("/verify/{verificationKey}")
    public String verify(@PathVariable("verificationKey") final String verificationKey, final ModelMap map) {
        return "register/" + registerService.verifyAccount(verificationKey);
    }

}
