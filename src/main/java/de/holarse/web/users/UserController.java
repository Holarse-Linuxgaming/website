package de.holarse.web.users;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.PasswordType;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.services.SecurityService;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    SecurityService securityService;

    @Autowired
    @Qualifier("bcryptEncoder")
    PasswordEncoder passwordEncoder;    

    @GetMapping
    public String index(ModelMap map) {
        map.addAttribute("users", userRepository.findAll());
        return "users/index";
    }
    
    @GetMapping("{login}")
    public String show(@PathVariable("login") String login, ModelMap map) {       
        return show(userRepository.findByLogin(login), map);
    }
    
    protected String show(final User user, final ModelMap map) {
        map.addAttribute("user", user);
        return "users/show";        
    }
    
    @GetMapping("{login}/edit")
    public String edit(@PathVariable("login")final String login, final UserCommand command, final Authentication authentication, final ModelMap map) {
        final User user = userRepository.findByLogin(login);        
        if (user == null) {
            throw new EntityNotFoundException("Benutzer " + login + " existiert nicht.");
        }
        
        if (!securityService.hasEditPermissions(user, ((HolarsePrincipal) authentication.getPrincipal()).getUser())) {
            throw new AccessDeniedException("Unzureichende Rechte für diese Aktion. Der Vorfall wird protokolliert.");
        }

        command.setEmail(user.getEmail());
        command.setSignature(user.getSignature());
        
        map.addAttribute("user", user);
        map.addAttribute("command", command);
        
        return "users/form";
    }
    
    @PostMapping("{login}")
    public String update(@PathVariable("login")final String login, @Valid @ModelAttribute final UserCommand command, final Authentication authentication, final ModelMap map) {
        final User user = userRepository.findByLogin(login);        
        
        if (!securityService.hasEditPermissions(user, ((HolarsePrincipal) authentication.getPrincipal()).getUser())) {
            throw new AccessDeniedException("Unzureichende Rechte für diese Aktion. Der Vorfall wird protokolliert.");
        }

        user.setEmail(command.getEmail());
        user.setSignature(command.getSignature());
        
        // Passwort gesetzt und identisch?
        if (StringUtils.isNotBlank(command.getPassword()) && StringUtils.isNotBlank(command.getPasswordConfirmation()) && command.getPassword().equals(command.getPasswordConfirmation())) {
            user.setPasswordType(PasswordType.BCRYPT);
            user.setDigest(passwordEncoder.encode(command.getPassword()));
        }

        userRepository.save(user);
        
        return "redirect:/users/" + user.getLogin();        
    }
    
}