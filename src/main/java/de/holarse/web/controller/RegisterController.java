package de.holarse.web.controller;

import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.web.controller.commands.RegisterForm;
import java.time.OffsetDateTime;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {
 
    private final static transient Logger log = LoggerFactory.getLogger(RegisterController.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping
    public ModelAndView index() {
        final ModelAndView mv = new ModelAndView();
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject("content", "partials/accounts/register");
        mv.addObject("registerForm", new RegisterForm()); 

        return mv;
    }

    @PostMapping
    public ModelAndView register(@Valid @ModelAttribute("registerForm") final RegisterForm registerForm, final BindingResult result, final ModelAndView mv) {
        mv.setViewName("layouts/bare");        
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");

        log.debug("Recieved register form email:{}, isRules: {}", registerForm.getEmail(), registerForm.isRules());

        
        if (!registerForm.getPassword().equals(registerForm.getPasswordConfirmation())) {
            result.rejectValue("passwordConfirmation", "Passwörter stimmen nicht überein.");
        }
        
        if (userRepository.findByLogin(registerForm.getUsername()) != null) {
            result.rejectValue("username", "Benutzername wird bereits verwendet.");
        }

        if (userRepository.findByEmail(registerForm.getEmail()) != null) {
            result.rejectValue("email", "Email wird bereits verwendet.");
        }   
        
        if (result.hasErrors()) {
            mv.addObject("content", "partials/accounts/register");
            mv.addObject("registerForm", registerForm); 
            log.debug("Registration failed");
            return mv;
        }

        log.info("Registration form ok");
        
        // Benutzer anlegen
        
        mv.addObject("content", "partials/accounts/registered");       
        return mv;
    }    
    
    @GetMapping(value = "verify")
    public ModelAndView verify(@NotNull @RequestParam("v") final String verificationHash, final ModelAndView mv) {
        mv.setViewName("layouts/bare");          
        final Optional<User> user = userRepository.findByVerificationHash(verificationHash);
        if (user.isPresent()) {
            final User user2 = user.get();
            user2.getUserStatus().setVerified(true);
            user2.getUserStatus().setUpdated(OffsetDateTime.now());
            
            userRepository.save(user2);
            mv.addObject("content", "partials/accounts/verify-complete");       
        } else {
            mv.addObject("content", "partials/accounts/verify-failed");       
        }
        
        return mv;
    }
    
}
