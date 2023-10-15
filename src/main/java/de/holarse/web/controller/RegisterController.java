package de.holarse.web.controller;

import de.holarse.backend.db.User;
import de.holarse.backend.db.UserData;
import de.holarse.backend.db.UserSlug;
import de.holarse.backend.db.UserStatus;
import de.holarse.backend.db.repositories.RoleRepository;
import de.holarse.backend.db.repositories.UserDataRepository;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.db.repositories.UserSlugRepository;
import de.holarse.backend.db.repositories.UserStatusRepository;
import de.holarse.backend.types.PasswordType;
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
import org.apache.commons.math3.random.RandomDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private UserStatusRepository userStatusRepository;    
    
    @Autowired
    private UserDataRepository userDataRepository;      
    
    @Autowired
    private UserSlugRepository userSlugRepository;        

    @Autowired
    private RegisterService registerService;

    @Autowired
    private JobService jobService;

    @Autowired
    private SlugService slugService;

    @Autowired
    private RoleRepository roleRepository;    
    
    @Autowired
    RegisterFormValidationService rfValidationService;
    
    @Autowired
    @Qualifier("bcryptEncoder")
    private PasswordEncoder passwordEncoder;      

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
        User user = new User();
        user.setLogin(registerForm.getUsername());
        user.setEmail(registerForm.getEmail());
        user.setHashType(PasswordType.bcrypt);
        user.setDigest(passwordEncoder.encode(registerForm.getPassword()));
        user.getRoles().add(roleRepository.findByCode("TRUSTED_USER"));
        userRepository.saveAndFlush(user);        
        
        final UserStatus userStatus = new UserStatus();
        userStatus.setCreated(OffsetDateTime.now());
        userStatus.setFailedLogins(0);
        userStatus.setLocked(false);
        userStatus.setVerified(false);
        userStatus.setVerificationHash(generateVerificationHash());
        userStatus.setVerificationHashValidUntil(OffsetDateTime.now().plusMinutes(30));
        user.setStatus(userStatus);

        final UserData userData = new UserData();
        user.setUserData(userData);
        
        // Slugs
        UserSlug userSlug = slugService.slugify(user);
        userSlug.setUser(user);
        userSlugRepository.saveAndFlush(userSlug);
        
        mv.addObject("validationKey", userStatus.getVerificationHash());        
        mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/accounts/registered");
        return mv;
    }

    @GetMapping(value = "verify")
    public ModelAndView verify(@NotNull @RequestParam("v") final String verificationHash, final ModelAndView mv) {
        mv.setViewName("layouts/bare");

        final Optional<UserStatus> userStatusOpt = userStatusRepository.findByValidVerification(verificationHash);
        if (userStatusOpt.isPresent()) {
            final UserStatus userStatus = userStatusOpt.get();
            userStatus.setVerified(true);
            userStatus.setUpdated(OffsetDateTime.now());

            userStatusRepository.saveAndFlush(userStatus);
            mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/accounts/verify-complete");
        } else {
            mv.addObject(WebDefines.DEFAULT_VIEW_ATTRIBUTE_NAME, "sites/accounts/verify-failed");
        }

        return mv;
    }
    
    public String generateVerificationHash() {
        return new RandomDataGenerator().nextSecureHexString(12);
    }    

}
