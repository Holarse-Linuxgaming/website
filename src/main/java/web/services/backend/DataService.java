package web.services.backend;

import java.util.Collection;
import static java.util.concurrent.TimeUnit.SECONDS;
import javax.cache.CacheManager;
import javax.cache.annotation.CacheDefaults;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.TouchedExpiryPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import web.entities.Article;
import web.entities.Entity;
import web.entities.User;
import web.services.backend.file.PersistenceBackend;

@Service
public class DataService {

    @Autowired
    private PersistenceBackend<Article> pb;
    
    public Article getArticle(final Long uid) throws Exception {
        return pb.read(uid, Article.class);
    }
    
    public Collection<Article> getAllArticles() throws Exception {
        return pb.getAll(Article.class);
    }
    
    public User getUser(final Long uid) throws Exception {
        return pb.read(uid, User.class);
    }
    
}
