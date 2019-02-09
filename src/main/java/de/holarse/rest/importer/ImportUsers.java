package de.holarse.rest.importer;

import de.holarse.backend.db.PasswordType;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.RoleRepository;
import de.holarse.backend.db.repositories.UserRepository;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Secured({"ROLE_API_IMPORT", "ROLE_API_ADMIN"})
@RestController
@RequestMapping("/api/import/users")
public class ImportUsers {
    
    Logger log = LoggerFactory.getLogger(ImportUsers.class);    
    
    @Autowired
    private UserRepository ur;
    
    @Autowired
    private RoleRepository rr;
    
    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> upload(@RequestBody final de.holarse.backend.export.User importUser) throws Exception {
        // Bstehenden Drupal-Import finden oder neuen anlegen
        final User user  = ur.findByOldId(importUser.getUid()).orElseGet(() -> new User());
        user.setCreated(OffsetDateTime.ofInstant(importUser.getCreated().toInstant(), ZoneOffset.UTC));
        
        user.setLogin(importUser.getLogin());        
        user.setEmail(importUser.getEmail());
        user.setPasswordType(PasswordType.valueOf(importUser.getPassword().getType()));
        user.setDigest(importUser.getPassword().getDigest());
        
        user.setSignature(importUser.getSignature());
        user.setAvatar(importUser.getAvatar());
        user.setVerified(true); // Automatisch verifiziert durch den Import
        user.setLocked(importUser.isLocked());
        user.setOldId(importUser.getUid());
        
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        } else {
            user.getRoles().clear();    
        }
        
        if (importUser.getRoles() != null) {
            user.setRoles( importUser.getRoles().stream().map(r -> rr.findByCodeIgnoreCase(r.getValue()))
                                                         .filter(Optional::isPresent)
                                                         .map(Optional::get)
                                                         .collect(Collectors.toSet()) );
        }
        ur.save(user);
        
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }    
    
}
