package de.holarse.backend.db;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.elasticsearch.common.xcontent.XContentBuilder;

public interface Searchable {
 
    Long getId();
    String getType();
    
    String getTitle();
    Set<String> getAlternativeTitles();
    String getContent();
    Set<Tag> getTags();
    
    List<Comment> getComments();
    
    String getUrl();
    
    XContentBuilder toJson() throws IOException;
    
}
