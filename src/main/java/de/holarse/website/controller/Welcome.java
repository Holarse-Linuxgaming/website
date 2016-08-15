package de.holarse.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class Welcome {
    
    public Welcome() {
        System.out.println("I AM THERE!");
    }
    
    @RequestMapping("hello.html")
    public String hello() {
        System.out.println("I AM THERE too!");
        return "/welcome/hello";
    }
    
    @RequestMapping("jo.html")
    public String jo() {
        return "/articles/jo";
    }
    
}
