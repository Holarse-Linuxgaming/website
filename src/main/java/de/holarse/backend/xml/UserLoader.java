package de.holarse.backend.xml;

import de.holarse.entity.User;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserLoader extends GenericLoader<User> {

    Logger log = LoggerFactory.getLogger(UserLoader.class);

    @Value("${holarse.document.path}")
    private String directory;
    
    @Override
    protected File getEntityDirectory() {
        return new File(directory, "users");
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }



}
