package de.holarse.backend.xml;

import de.holarse.entity.Comment;
import java.io.File;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommentLoader extends GenericLoader<Comment> {

    private final static transient Logger log = LoggerFactory.getLogger(CommentLoader.class);

    @Value("${holarse.document.path}")
    private String directory;
    
    @Override
    protected Class<Comment> getEntityClass() {
        return Comment.class;
    }
    
    @Override
    protected File getEntityDirectory() {
        return new File(directory, "comments");
    }

}
