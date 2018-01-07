package de.holarse.backend.decorator;

import de.holarse.backend.repository.UserRepository;
import de.holarse.backend.xml.UserLoader;
import de.holarse.view.User;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCacheBuilder implements CacheBuilder<de.holarse.entity.importer.User, de.holarse.view.User> {

    Logger log = LoggerFactory.getLogger(UserCacheBuilder.class);
    
    @Autowired
    private UserLoader ul;
    
    @Autowired
    private UserRepository ur;

    @Override
    public void buildCache() {
        final List<User> users = ul.getAll().stream().map(u -> migrate(u)).collect(Collectors.toList());
        ur.saveAll(users);
    }

    @Override
    public de.holarse.view.User migrate(final de.holarse.entity.importer.User user) {
        final de.holarse.view.User uv = new de.holarse.view.User();
        uv.setUid(user.getUid());
        uv.setLogin(user.getLogin());

        return uv;
    }

}
