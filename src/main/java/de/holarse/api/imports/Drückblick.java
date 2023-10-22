package de.holarse.api.imports;

import static de.holarse.config.RoleApiTypes.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

@Secured({ROLE_API_DRÜCKBLICK, ROLE_API_ADMIN})
@RestController
@RequestMapping("/api/import/drückblick/")
public class Drückblick {
    
    private final static transient Logger log = LoggerFactory.getLogger(Drückblick.class);    
    
    @Autowired
    private JmsTemplate jmsTemplate;
       
    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> upload(@Valid @RequestBody final de.holarse.backend.api.drückblick.DrückblickEntry importItem) throws Exception {
        try {
            jmsTemplate.convertAndSend("drueckblick", importItem);
        } catch (JmsException je) {
            throw new RuntimeException("error while jms send", je);
        }
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }
           
    
}
