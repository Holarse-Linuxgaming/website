package de.holarse.backend.views;

import java.util.ArrayList;
import java.util.List;

public class UserView extends AbstractLinkView implements SlugView {

    private Long id;
    private String login;
    private String email;
    private final List<String> roles = new ArrayList<>();
    private String slug;
    private String signature;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }
    
    public void setSlug(String slug) {
        this.slug = slug;
    }

    @Override
    public String getSlug() {
        return slug;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
    
}
