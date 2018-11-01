package de.holarse.backend.db;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Ein Forum
 * @author comrad
 */
@Entity
@Table(name = "forums")
public class Forum extends Base implements Sluggable, LinkableNode {

    private String title;
    private String description;
    @Column(columnDefinition = "int default 0")    
    private int weight;
    
    @Column(unique = true)    
    private String slug;

    @OneToMany(mappedBy = "forum")    
    private List<ForumThread> forumThreads;

    @Transient
    private String url;
    
    @Transient
    private String urlid;
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<ForumThread> getForumThreads() {
        return forumThreads;
    }

    public void setForumThreads(List<ForumThread> forumThreads) {
        this.forumThreads = forumThreads;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    public int getThreadCount() {
        return getForumThreads().size();
    }

    @Override
    public String getSlug() {
        return slug;
    }

    @Override
    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String getUrl() {
        return url;
    }
    
    @Override
    public String getUrlid() {
        return urlid;
    }   
    
    @PostLoad
    private void forumPostLoad() {
        this.url = "/forum/" + getSlug();
        this.urlid = "/forum/" + getId();
    }    
    
    public long getMessageCount() {
        return getThreadCount() + getForumThreads().stream().mapToInt(t -> t.getComments().size()).sum();
    }
    
}
