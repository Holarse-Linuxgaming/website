package de.holarse.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping({"/", "index.html"})
    public String index() {
        return "admin/index";
    }
    
}
