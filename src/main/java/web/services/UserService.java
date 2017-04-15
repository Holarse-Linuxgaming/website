package web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import web.entities.User;
import web.services.backend.index.Index;

@Service
public class UserService extends AbstractService<User> {

    @Value("${holarse.directories.base}/users")
    private String directory;

    @Autowired
    @Qualifier("userIndex")
    private Index<User> index;

    @Override
    protected String getDirectory() {
        return directory;
    }

    @Override
    protected Index<User> getIndex() {
        return index;
    }

}
