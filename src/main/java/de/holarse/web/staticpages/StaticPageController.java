package de.holarse.web.staticpages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticPageController {
    
    @GetMapping(value = {"/impressum", "/imprint"})
    public String Imprint() {
        return "static/imprint";
    }
    
    @GetMapping(value = {"/about", "/wiki/über_holarse"})
    public String About() {
        return "static/about";
    }
    
}
