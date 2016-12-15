package web.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import web.entities.User;

@Service
public class UserService extends AbstractService<User> {

    @Value("${holarse.directories.users}")
    private String directory;    
    
    @Override
    protected String getDirectory() {
        return directory;
    }
    
}
