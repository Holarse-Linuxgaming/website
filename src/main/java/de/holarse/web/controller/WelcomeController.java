package de.holarse.web.controller;

import de.holarse.backend.db.ApiUser;
import de.holarse.backend.db.repositories.ApiUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/welcome")
public class WelcomeController {
    
    @Autowired
    ApiUserRepository apiUserRepository;
    
    @GetMapping("/")
    public ModelAndView index() {
        final ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        
        final ApiUser apiUser = apiUserRepository.findByLogin("dummy");
        mv.addObject("token", apiUser.getToken());
        
        return mv;
    }
    
}
