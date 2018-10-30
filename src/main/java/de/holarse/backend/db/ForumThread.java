package de.holarse.backend.db;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Ein Thread in einem Forum
 * @author comrad
 */
@Entity
@Table(name = "forumthreads")
public class ForumThread extends SluggableNode implements LinkableNode {

    @ManyToOne
    private Forum forum;
    
    private String title;
    
    @Transient
    private String url;
    
    @Transient    
    private String urlid;
    
    @Override
    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public String getUrlid() { 
        return urlid;
    }    

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }
    
    @PostLoad
    private void threadPostLoad() {
        this.url = "/forum/" + getForum().getSlug() + "/" + getSlug();
        this.urlid = "/forum/" + getForum().getId() + "/" + getId();
    }    
    
    
}
