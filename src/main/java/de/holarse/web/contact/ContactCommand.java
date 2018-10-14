package de.holarse.web.contact;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class ContactCommand {

    @NotNull
    private String name;
    @NotNull
    @Email    
    private String email;
    @NotNull    
    private String message;

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
