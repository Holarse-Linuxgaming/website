package web.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import web.entities.Article;

@Service
public class ArticleService extends AbstractService<Article> {

    @Value("${holarse.directories.articles}")
    private String directory;

    @Override
    protected String getDirectory() {
        return directory;
    }
    
}
