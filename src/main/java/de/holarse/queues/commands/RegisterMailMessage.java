package de.holarse.queues.commands;

import de.holarse.backend.db.User;
import de.holarse.backend.db.UserStatus;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RegisterMailMessage extends AbstractMailMessage {

    private static final long serialVersionUID = 1L;
    
    public RegisterMailMessage(final User user) {
        super(List.of(user.getEmail()), "noreply@holarse.de", "Dein Benutzerkonto auf Holarse.de - Spielen unter Linux");
        
        final UserStatus us = user.getStatus();
        
        final StringBuffer buffer = new StringBuffer();
        buffer.append(String.format("Hallo, %s", user.getLogin())).append("\n");
        buffer.append("bitte aktiviere dein Konto mit diesem Link:\n\n");
        buffer.append(String.format("http://asdadas/%s", us.getVerificationHash())).append("\n\n");
        buffer.append(String.format("Der Link ist noch gültig bis %s\n", us.getVerificationHashValidUntil().format(DateTimeFormatter.ISO_DATE_TIME))).append("\n\n");
        buffer.append("Dein Holarse-Team");
        setTemplate(buffer.toString());        
        
        getKv().put("username", user.getLogin());
        getKv().put("verification_hash", us.getVerificationHash());
        getKv().put("valid_until", us.getVerificationHashValidUntil().format(DateTimeFormatter.ISO_DATE_TIME));
    }
    
}
