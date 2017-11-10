package de.holarse.backend;

import de.holarse.entity.Comment;
import java.io.File;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommentLoader extends GenericLoader<Comment> {

    Logger log = LoggerFactory.getLogger(CommentLoader.class);

    @Value("${holarse.document.path}")
    private String directory;

    public List<Comment> getUsers() {
        return entities;
    }
    
    @Override
    protected Class<Comment> getEntityClass() {
        return Comment.class;
    }
    
    @Override
    protected File getEntityDirectory() {
        return new File(directory, "comments");
    }




}
