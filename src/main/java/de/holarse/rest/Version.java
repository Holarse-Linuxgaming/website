package de.holarse.rest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/version")
@Secured({"ROLE_API_VERSION", "ROLE_API_ADMIN"})
public class Version {
    
    @GetMapping
    public String getVersion() {
        return "1.0";
    }
    
}
