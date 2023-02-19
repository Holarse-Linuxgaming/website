package de.holarse.web.controller.commands;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;

import javax.validation.constraints.NotBlank;
public class RegisterForm {

    @NotBlank(message = "Benutzername darf nicht leer sein.")
    private String username;
    @Email(message = "Muss eine gültige Email-Adresse sein")
    @NotBlank(message = "Email-Adresse darf nicht leer sein.")
    private String email;
    @NotBlank(message = "Passwort darf nicht leer sein")
    private String password;
    @NotBlank(message = "Passwortbestätigung darf nicht leer sein.")
    private String passwordConfirmation;
    @AssertTrue(message = "Unsere Communityregeln müssen akzeptiert werden.")
    private boolean rules;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public boolean isRules() {
        return rules;
    }

    public void setRules(boolean rules) {
        this.rules = rules;
    }

    @Override
    public String toString() {
        return "RegisterForm{" + "username=" + username + ", email=" + email + ", password=" + password + ", passwordConfirmation=" + passwordConfirmation + ", rules=" + rules + '}';
    }
    
}
