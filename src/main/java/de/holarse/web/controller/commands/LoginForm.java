package de.holarse.web.controller.commands;

import javax.validation.constraints.NotEmpty;

public class LoginForm {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private boolean keep_signed_in;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isKeep_signed_in() {
        return keep_signed_in;
    }

    public void setKeep_signed_in(boolean keep_signed_in) {
        this.keep_signed_in = keep_signed_in;
    }
    
}
