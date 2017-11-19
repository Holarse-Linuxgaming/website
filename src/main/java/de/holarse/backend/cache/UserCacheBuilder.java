package de.holarse.backend.cache;

import de.holarse.backend.xml.UserLoader;
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

    protected de.holarse.view.User migrate(final de.holarse.entity.User user) {
        final de.holarse.view.User uv = new de.holarse.view.User();
        uv.setUid(user.getUid());
        uv.setLogin(user.getLogin());

        return uv;
    }

}
