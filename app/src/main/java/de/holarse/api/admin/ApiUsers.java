package de.holarse.api.admin;


import de.holarse.backend.api.admin.RandomToken;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Secured({"ROLE_API_ADMIN", "ROLE_ADMIN"})
@RestController
@RequestMapping(value = "/admin/api/apiusers/")
public class ApiUsers {
    
    @Qualifier(value = "bcryptEncoder")
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @GetMapping(value = "token.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public RandomToken createRandomToken() {
        final String randomToken = RandomStringUtils.randomAlphabetic(20);
        final String hash = passwordEncoder.encode(randomToken);
        
        return new RandomToken(randomToken, hash);
    }
    
}
