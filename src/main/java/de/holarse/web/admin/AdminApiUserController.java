package de.holarse.web.admin;

import de.holarse.backend.db.repositories.ApiUserRepository;
import de.holarse.backend.views.PaginationView;
import de.holarse.backend.views.admin.ApiUserAdminView;
import de.holarse.factories.AdminViewFactory;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/api_users")
public class AdminApiUserController {

    @Autowired
    ApiUserRepository apiUserRepository;

    @Autowired
    AdminViewFactory viewFactory;
    
    @GetMapping
    public String index() {
        return "admin/api_users/index";
    }

    @GetMapping("index.json")
    public ResponseEntity<List<ApiUserAdminView>> indexData(@RequestParam(name= "page", defaultValue = "1") final int page, @RequestParam(name = "pageSize", defaultValue = "30") final int pageSize) {
        var pagination = new PaginationView("/admin/api_users/index.json", page, apiUserRepository.count(), pageSize);        
        return new ResponseEntity<List<ApiUserAdminView>>(apiUserRepository.findAll(pagination.getPageable())
                                                                           .stream()
                                                                           .map(viewFactory::fromApiUser)
                                                                           .collect(Collectors.toList()), HttpStatus.OK);
    }
   
}
