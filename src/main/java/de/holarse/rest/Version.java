package de.holarse.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/version")
public class Version {
    
    @PreAuthorize("hasRole('ROLE_API_VERSION')")
    @GetMapping
    public String getVersion() {
        return "1.0";
    }
    
}
