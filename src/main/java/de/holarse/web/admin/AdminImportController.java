package de.holarse.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/import")
public class AdminImportController {

    @GetMapping("/")
    public String index() {
        return "admin/import/index";
    }
    
}
