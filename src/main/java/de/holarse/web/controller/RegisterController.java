package de.holarse.web.controller;

import de.holarse.backend.db.User;
import de.holarse.backend.db.UserSlug;
import de.holarse.backend.db.repositories.RoleRepository;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.web.controller.commands.RegisterForm;
import de.holarse.web.defines.WebDefines;
import de.holarse.web.services.JobService;
import de.holarse.web.services.RegisterFormValidationService;
import de.holarse.web.services.RegisterService;
import de.holarse.web.services.SlugService;
import java.time.OffsetDateTime;
import java.util.Optional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {

    private final static transient Logger log = LoggerFactory.getLogger(RegisterController.class);

    @PersistenceContext
    private EntityManager em;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private JobService jobService;

    @Autowired
    private SlugService slugService;

    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    RegisterFormValidationService rfValidationService;

    @GetMapping
    public ModelAndView index(final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/accounts/register");
        mv.addObject("registerForm", new RegisterForm());
        return mv;
    }

    @Transactional
    @PostMapping
    public ModelAndView register(@Valid @ModelAttribute("registerForm") final RegisterForm registerForm, final BindingResult result, final ModelAndView mv) {
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");

        log.debug("Recieved register form email:{}, isRules: {}", registerForm.getEmail(), registerForm.isRules());
        rfValidationService.validate(registerForm, result);
        
        if (result.hasErrors()) {
            mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/accounts/register");
            log.debug("Registration form failed");
            return mv;            
        }

        log.info("Registration form ok");

        // Unbestätigten Benutzer anlegen
        final User newUser = registerService.createUnverifiedUser(registerForm);
        newUser.getRoles().add(roleRepository.findByCode("TRUSTED_USER"));
        
        // Slugs
        UserSlug userSlug = slugService.slugify(newUser);
        userSlug.setUser(newUser);
        newUser.getUserSlugs().add(userSlug);
        em.persist(newUser);
        mv.addObject("validationKey", newUser.getUserStatus().getVerificationHash());

        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/accounts/registered");
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
            mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/accounts/verify-complete");
        } else {
            mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/accounts/verify-failed");
        }

        return mv;
    }

}
