package de.holarse.backend.xml;

import de.holarse.entity.News;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author comrad
 */
@Component
public class NewsLoader extends GenericLoader<News> {

    private final static transient Logger log = LoggerFactory.getLogger(NewsLoader.class);

    @Value("${holarse.document.path}")
    private String directory;

    @Override
    protected Class<News> getEntityClass() {
        return News.class;
    }

    @Override
    protected File getEntityDirectory() {
        return new File(directory, "news");
    }
}
