package de.holarse.web.users;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.PasswordType;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.views.PaginationView;
import de.holarse.exceptions.NodeNotFoundException;
import de.holarse.factories.ViewFactory;
import de.holarse.services.SecurityService;
import javax.validation.Valid;

import de.holarse.services.WebUtils;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    
    @Autowired
    ViewFactory viewFactory;

    @Transactional
    @GetMapping
    public String index(@PageableDefault(page = 1, size = 30) final Pageable pageable, final Model map) {
        var pagination = new PaginationView("/users/", pageable, userRepository.count());
        var data = userRepository.findAll(pagination.getPageable(Sort.by(Direction.ASC, "login")))
                                    .stream()
                                    .map(viewFactory::fromUser)
                                    .collect(Collectors.toList());

        map.addAttribute("views", data);
        map.addAttribute("pagination", pagination);

        return "users/index";
    }
    
    @Transactional
    @GetMapping("{username}")
    public String show(@PathVariable("username") final String username, final ModelMap map) {       
        return show(userRepository.findBySlug(username).orElseThrow(() -> new NodeNotFoundException("Username not found")), map);
    }

    protected String show(final User user, final ModelMap map) {
        map.addAttribute("user", viewFactory.fromUser(user));
        return "users/show";        
    }

    @Secured("ROLE_USER")
    @GetMapping("{login}/edit")
    public String edit(@PathVariable("login")final String login, final UserCommand command, final Authentication authentication, final ModelMap map) {
        final User user = userRepository.findBySlug(login).orElseThrow(() -> new NodeNotFoundException("Login not found"));

        if (!securityService.hasEditPermissions(user, ((HolarsePrincipal) authentication.getPrincipal()).getUser())) {
            throw new AccessDeniedException("Unzureichende Rechte für diese Aktion. Der Vorfall wird protokolliert.");
        }

        command.setEmail(user.getEmail());
        command.setSignature(user.getSignature());
        
        map.addAttribute("user", user);
        map.addAttribute("command", command);
        
        return "users/form";
    }
    
    @Secured("ROLE_USER")    
    @PostMapping("{login}")
    public String update(@PathVariable("login")final String login, @Valid @ModelAttribute final UserCommand command, final Authentication authentication, final ModelMap map) {
        final User user = userRepository.findBySlug(login).orElseThrow(() -> new NodeNotFoundException("Login not found"));
        
        if (!securityService.hasEditPermissions(user, ((HolarsePrincipal) authentication.getPrincipal()).getUser())) {
            throw new AccessDeniedException("Unzureichende Rechte für diese Aktion. Der Vorfall wird protokolliert.");
        }

        user.setEmail(command.getEmail());
        user.setSignature(command.getSignature());
        user.setSlug(WebUtils.slugify(user.getLogin()));
        
        // Passwort gesetzt und identisch?
        if (StringUtils.isNotBlank(command.getPassword()) && StringUtils.isNotBlank(command.getPasswordConfirmation()) && command.getPassword().equals(command.getPasswordConfirmation())) {
            user.setPasswordType(PasswordType.BCRYPT);
            user.setDigest(passwordEncoder.encode(command.getPassword()));
        }

        userRepository.save(user);
        
        return "redirect:/users/" + user.getSlug();
    }
    
}