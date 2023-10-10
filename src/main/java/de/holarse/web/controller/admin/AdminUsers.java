package de.holarse.web.controller.admin;

import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.view.UserView;
import static de.holarse.utils.ModelAndViewFactory.makeAdminLayout;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.OffsetDateTime;

@Controller
@RequestMapping(value = "/admin/users")
public class AdminUsers {

    private final static transient Logger log = LoggerFactory.getLogger(AdminUsers.class);
    
    @Autowired
    private UserRepository userRepository;    
    
    @GetMapping
    public ModelAndView index(final Pageable pageable, final ModelAndView mv) {
        makeAdminLayout(mv, "sites/admin/users");
        mv.addObject("users", userRepository.findAll(pageable).map(UserView::of));
        return mv;
    }
    
    @GetMapping("{userId}")
    public ModelAndView show(@PathVariable("userId") final Integer userId, final ModelAndView mv) {
        makeAdminLayout(mv, "sites/admin/user");  
        mv.addObject("user", userRepository.findById(userId).map(UserView::of).get());
        return mv;
    }

    @PostMapping("{userId}")
    public String save(@Valid @ModelAttribute("user") UserView user, @PathVariable Integer userId) {
        if (!userId.equals(user.getId())) {
            log.error("Userid ({}) does not match form userid. Form: {}", userId, user);
            throw new IllegalArgumentException("userid does not match form userid");
        }

        final User backendUser = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        backendUser.setEmail(user.getEmail());
        backendUser.setLogin(user.getUsername());
        backendUser.setUpdated(OffsetDateTime.now());

        userRepository.saveAndFlush(backendUser);

        return String.format("redirect:/admin/users/%s", userId);
    }

}
