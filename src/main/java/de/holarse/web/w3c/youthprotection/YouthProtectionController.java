package de.holarse.web.w3c.youthprotection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class YouthProtectionController {

    @GetMapping("/age.xml")
    public String ageXml() {
        return "";
    }

    @GetMapping("/age-de.xml")
    public String ageDeXml() {
        return "";
    }

    @GetMapping("/miracle.xml")
    public String miracle() {
        return "";
    }

}