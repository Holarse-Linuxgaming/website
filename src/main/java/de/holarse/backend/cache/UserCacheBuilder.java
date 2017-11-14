package de.holarse.backend.cache;

import de.holarse.backend.xml.UserLoader;
import de.holarse.entity.User;
import de.holarse.view.UserView;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCacheBuilder implements CacheBuilder {

    Logger log = LoggerFactory.getLogger(UserCacheBuilder.class);
    
    @Autowired
    private UserLoader ul;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("holarse-pu");
    EntityManager eman = emf.createEntityManager();

    @Override
    public void buildCache() {
        eman.getTransaction().begin();
        ul.getAll().forEach((u) -> {
            log.debug("Saving " + u);
            eman.persist(migrate(u));
        });
        eman.getTransaction().commit();
    }

    protected UserView migrate(final User user) {
        final UserView uv = new UserView();
        uv.setUid(user.getUid());
        uv.setLogin(user.getLogin());

        return uv;
    }

}
