package de.holarse.mail;

import de.holarse.backend.db.User;
import de.holarse.backend.db.UserStatus;
import java.util.List;

public class RegisterMailMessage extends AbstractMailMessage {

    public RegisterMailMessage(final User user) {
        super(List.of(user.getEmail()), 
              "noreply@holarse.de", 
              "Dein Benutzerkonto auf Holarse.de - Spielen unter Linux", 
              "mails/register.txt");
        
        final UserStatus us = user.getStatus();

        getKv().put("username", user.getLogin());
        getKv().put("verification_hash", us.getVerificationHash());
        getKv().put("valid_until", us.getVerificationHashValidUntil());
    }
    
}
