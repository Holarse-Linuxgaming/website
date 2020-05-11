package de.holarse.backend.views;

import java.util.ArrayList;
import java.util.List;

/**
 * Der verlinkbare Benutzer für die allgemein öffentliche Seite.
 * Daher fehlen hier persönliche Informationen wie Email (und ggf. andere)
 */
public class UserView extends AbstractLinkView implements SlugView {

    private Long id;
    private String login;
    private final List<String> roles = new ArrayList<>();
    private String created;
    private String lastLogin;
    private String lastAction;
    private String lastActionAgo;
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

    public List<String> getRoles() {
        return roles;
    }

    public String getRolesText() {
        if (!roles.isEmpty())
            return String.join(",", roles);

        return "";
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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getLastAction() {
        return lastAction;
    }

    public void setLastAction(String lastAction) {
        this.lastAction = lastAction;
    }

    public String getLastActionAgo() {
        return lastActionAgo;
    }

    public void setLastActionAgo(String lastActionAgo) {
        this.lastActionAgo = lastActionAgo;
    }
    
}
