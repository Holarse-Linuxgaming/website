package de.holarse.web.admin;

import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.views.PaginationView;
import de.holarse.backend.views.admin.UserAdminView;
import de.holarse.factories.AdminViewFactory;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/users")
public class AdminUsersController {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    AdminViewFactory viewFactory;
    
    @GetMapping
    public String index() {
        return "admin/users/index";
    }

    @Transactional
    @GetMapping(value = "index.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserAdminView>> indexJson(@PageableDefault(size=10, sort="login") final Pageable pageable) {
        var pagination = new PaginationView("/admin/api_users/index.json", pageable, userRepository.count());                
        final List<UserAdminView> items = userRepository.findAll(pagination.getPageable())
                                                       .stream()
                                                       .map(viewFactory::fromUser)
                                                       .collect(Collectors.toList());
                                                       
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
    
}
