package de.holarse.web.contact;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class ContactCommand {

    @NotNull
    private String name;
    
    private Long userId;
    
    @NotNull
    @Email    
    private String email;
    
    private String topic;
    
    @NotNull    
    private String message;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ContactCommand{" + "name=" + name + ", email=" + email + ", message=" + message + '}';
    }
    
}
