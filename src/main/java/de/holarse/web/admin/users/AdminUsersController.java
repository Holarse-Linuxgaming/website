package de.holarse.web.admin.users;

import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.views.UserView;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin/users")
public class AdminUsersController {
    
    @Autowired
    UserRepository userRepository;
    
    private final Function<User, UserView> mapToView = u -> {
        final UserView view = new UserView();
        view.setId(u.getId());
        view.setEmail(u.getEmail());
        view.setLogin(u.getLogin());
        
        view.getRoles().addAll( u.getRoles().stream().map(r -> r.getCode()).collect(Collectors.toList()) );
        
        return view;
    };
    
    @Transactional
    @GetMapping(value = "")
    public String index(final ModelMap map, @PageableDefault(size=10, sort="login") final Pageable pageable) {
        final List<UserView> userViews = userRepository.findAll(pageable).stream().map(mapToView).collect(Collectors.toList());
        map.addAttribute("users", userViews);
        map.addAttribute("page", pageable);
       
        return "admin/users/index";
    }
    
}
