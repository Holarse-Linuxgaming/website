package de.holarse.web.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import de.holarse.backend.api.admin.RandomToken;

@Service
public class ApiUserPasswordGeneratorService {
    
    @Qualifier(value = "bcryptEncoder")
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public RandomToken createNewToken() {
        final String randomToken = RandomStringUtils.secure().nextAlphanumeric(20);
        final String digest = passwordEncoder.encode(randomToken);
        
        return new RandomToken(randomToken, digest);        
    }

}
