package de.holarse.backend;

import java.io.File;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserLoader {
    
    Logger log = LoggerFactory.getLogger(UserLoader.class);

    @Value("${holarse.document.path}")
    private String directory;
    
    @PostConstruct
    public void loadFromFileSystem() {
        log.debug("loading users");
    }
    
}
