package de.holarse.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @GetMapping
    public ModelAndView index() {
        final ModelAndView mv = new ModelAndView();
        mv.setViewName("layouts/bare");
        mv.addObject("title", "Die Linuxspiele-Seite für Linuxspieler");
        mv.addObject("content", "partials/login");

        return mv;
    }

}
