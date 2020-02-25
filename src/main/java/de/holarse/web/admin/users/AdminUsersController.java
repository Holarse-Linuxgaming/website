package de.holarse.web.admin.users;

import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.views.UserView;
import de.holarse.factories.ViewFactory;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/users")
public class AdminUsersController {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    ViewFactory viewFactory;
    
    @Transactional
    @GetMapping
    public String index(final ModelMap map, @PageableDefault(size=10, sort="login") final Pageable pageable, final HttpServletRequest request) {
        final Page<UserView> view = userRepository.findAll(pageable).map(viewFactory::fromUser);
        map.addAttribute("view", view);
       
        return "admin/users/index";
    }
    
}
