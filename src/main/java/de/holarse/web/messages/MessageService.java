package de.holarse.web.messages;

import de.holarse.backend.db.Message;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.MessageRepository;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.exceptions.HolarseException;
import de.holarse.web.contact.MessageCommand;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    Logger logger = LoggerFactory.getLogger(MessageService.class);
    
    private final static transient String MSG_DELIMITER = ";";
    
    @Autowired
    MessageRepository messageRepository;
    
    @Autowired
    UserRepository userRepository;
    
    public String createNewUUID() {
       return UUID.randomUUID().toString();
    }
    
    public Collection<String> getUsers(String userList) {
        return Arrays.asList(userList.split(MSG_DELIMITER));
    }
    
    public String toHolarseUser(final User user) {
        return "@" + user.getLogin();
    }
    
    public boolean isHolarseUser(final String user) {
        return user != null && user.startsWith("@");
    }
    
    public boolean isEmailUser(final String user) {
        return user != null && user.contains("@") && user.contains(".");
    }
    
    public Message replicate(final Message message) {
        final Message clone = new Message();
        clone.setAuthor(message.getAuthor());
        clone.setContent(message.getContent());
        clone.setCreated(message.getCreated());
        clone.setRead(false);
        clone.setSent(false);
        clone.setMailbox(null); // Wird von "deliver" gesetzt
        clone.setRecipients(message.getRecipients());
        clone.setThread(message.getThread());
        clone.setUuid(null); // Wird von "deliver" gesetzt
        
        return clone;
    }
    
    /**
     * Versendet eine PM/Mail und verteilt sie an die jeweiligen Empfänger
     * @param message 
     */
    public void deliver(final Message message) {
        // Für den Versender speichern
        final Message senderMessage = replicate(message);
        senderMessage.setMailbox(message.getAuthor());
        senderMessage.setUuid(createNewUUID());
        messageRepository.save(senderMessage);
        
        // Über die Empfänger gehen und für jeden eine Nachricht hinterlegen.
        getUsers(message.getRecipients()).stream().map(recipient -> {
            final Message thisMessage = replicate(message);
            thisMessage.setMailbox(recipient);
            thisMessage.setUuid(createNewUUID());
            
            logger.debug("MSG: recipient: {} with message {}", recipient, thisMessage);
            
            return thisMessage;            
        }).forEach(m -> messageRepository.save(m));
    }
    
    public void validateSender(final MessageCommand command, final User currentUser) throws Exception {
        final User givenUser = command.getUserId() != null ? userRepository.findById(command.getUserId()).get() : null;
        // Wenn wir einen User haben, dann prüfen, ob dieser dem aktuellen eingeloggten Benutzer enspricht
        if (givenUser != null && currentUser != null && !givenUser.equals(currentUser) && currentUser.getRoles().stream().noneMatch(r -> r.getCode().equalsIgnoreCase("ADMIN"))) {
                throw new AuthenticationCredentialsNotFoundException("Nachrichtenbenutzer weicht vom eingegebene Benutzer ab.");
        }
    }
    
    /**
     * Erstellt eine Nachricht aus dem Kontakformular mit Voreinstellung dafür
     * @param command
     * @param author
     * @param recipients
     * @return 
     */
    public Message createContactMessage(final MessageCommand command, final User author, final String recipients) {
        final Message contactMessage = new Message();
        contactMessage.setRecipients(recipients);
        
        logger.debug("Recipients for Contact Form are: {}", recipients);
        
        return createMessage(command, author, contactMessage);
    }
    
    /**
     * Erstellt eine Nachricht aus dem Formular
     * @param command
     * @param author
     * @param message
     * @return
     * @throws HolarseException 
     */
    public Message createMessage(final MessageCommand command, final User author, final Message message) throws HolarseException {
        message.setCreated(OffsetDateTime.now());
        if (author != null) {
            message.setAuthor(toHolarseUser(author));
        } else {
            message.setAuthor(command.getName());
        }
        
        // Empfänger sind noch nicht vordefiniert
        if (message.getRecipients() == null) {
            message.setRecipients(command.getRecipients());
        }
        
        message.setTopic(command.getTopic());
        message.setContent(command.getContent());
        message.setThread(command.getThread());
        message.setRead(false);
        message.setSent(false);

        logger.debug("Current Message: {}", message);
        
        return message;
    }
            
    
}
