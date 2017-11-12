package de.holarse.backend;

import de.holarse.entity.Article;
import de.holarse.entity.Comment;
import de.holarse.entity.News;
import java.io.File;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author comrad
 */
@Component
public class ArticleLoader extends GenericLoader<Article> {

    private final static transient Logger log = LoggerFactory.getLogger(ArticleLoader.class);

    @Value("${holarse.document.path}")
    private String directory;

    @Override
    protected Class<Article> getEntityClass() {
        return Article.class;
    }

    @Override
    protected File getEntityDirectory() {
        return new File(directory, "articles");
    }
}
