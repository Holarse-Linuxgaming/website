package de.holarse.api.imports;

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

import static de.holarse.config.JmsQueueTypes.*;
import static de.holarse.config.RoleApiTypes.*;

@Secured({ROLE_API_ADMIN, ROLE_API_IMPORT})
@RestController
@RequestMapping({"/api/import/news", "/api/import/news/"})
public class News {

    private final static transient Logger log = LoggerFactory.getLogger(News.class);

    @Autowired
    private JmsTemplate jmsTemplate;

    @Transactional
    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> upload(@Valid @RequestBody final de.holarse.backend.api.News importItem) throws Exception {
        try {
            jmsTemplate.convertAndSend(QUEUE_IMPORTS_NEWS, importItem);
        } catch (JmsException je) {
            throw new RuntimeException("error while jms send", je);
        }
        return new ResponseEntity<>("", HttpStatus.CREATED);
    }

}