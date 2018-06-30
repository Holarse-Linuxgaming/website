package de.holarse.web.users;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserCommand {

    private String password;
    private String passwordConfirmation;

    @NotEmpty
    @Email
    private String email;
    
    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }
    
}
