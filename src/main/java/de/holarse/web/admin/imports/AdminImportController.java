package de.holarse.web.admin.imports;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/imports")
public class AdminImportController {

    @GetMapping("/")
    public String index() {
        return "admin/imports/index";
    }
    
}
