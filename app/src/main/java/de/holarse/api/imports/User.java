package de.holarse.api.imports;

import de.holarse.config.JmsQueueTypes;
import static de.holarse.config.JmsQueueTypes.*;
import static de.holarse.config.RoleApiTypes.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Secured({ROLE_API_IMPORT, ROLE_API_ADMIN})
@RestController
@RequestMapping("/api/import/users")
public class User {
    
    private final static transient Logger log = LoggerFactory.getLogger(User.class);    
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> upload(@RequestBody final de.holarse.backend.api.User importUser) throws Exception {
        try {
            jmsTemplate.convertAndSend(QUEUE_IMPORTS_USERS, importUser);
        } catch (JmsException je) {
            throw new RuntimeException("error while jms send", je);
        }        
        return new ResponseEntity<>("OK", HttpStatus.CREATED);
    }    
    
}
