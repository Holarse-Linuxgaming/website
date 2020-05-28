package de.holarse.web.forums;

import de.holarse.backend.db.types.ContentType;
import de.holarse.backend.db.Forum;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ThreadCommand {

    @NotNull
    private Forum forum;
    
    @NotEmpty
    private String title;
    
    @NotEmpty
    private String content;
    
    @NotNull
    private ContentType contentType;

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }
    
    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
   
}
