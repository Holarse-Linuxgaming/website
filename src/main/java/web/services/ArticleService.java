package web.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import web.entities.Article;
import web.services.backend.index.Index;

@Service
public class ArticleService extends AbstractService<Article> {

    @Value("${holarse.directories.base}/articles")
    private String directory;

    @Autowired
    @Qualifier("articleIndex")
    private Index<Article> index;
    
    @Override
    protected String getDirectory() {
        return directory;
    }
    
    @Override
    protected Index<Article> getIndex() {
        return index;
    }
    
}
