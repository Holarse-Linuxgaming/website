package de.holarse.backend.db;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "messages")
public class Message extends CommentableNode {

    private String topic;
    @ManyToMany
    private Set<User> recipients;
    private String uuid;
    private String authorEmail;
    private String authorName;

    @Override
    public String getUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("messages").append("/").append(getUuid());
        return sb.toString();
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Set<User> getRecipients() {
        return recipients;
    }

    public void setRecipients(Set<User> recipients) {
        this.recipients = recipients;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
}
