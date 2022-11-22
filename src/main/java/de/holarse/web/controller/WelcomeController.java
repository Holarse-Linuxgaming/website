package de.holarse.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/welcome")
public class WelcomeController {
    
    @GetMapping("/")
    public ModelAndView index() {
        final ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        
        return mv;
    }
    
}
