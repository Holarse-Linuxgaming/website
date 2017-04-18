package web.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.entities.Article;
import web.services.backend.DataService;

@Service
public class ArticleService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
   
    @Autowired
    private DataService ds;
    
    public Article get(final Long uid) throws Exception {
        return ds.getArticle(uid);
    }

}
