package de.holarse.web.messages;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.Message;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.MessageRepository;
import de.holarse.exceptions.HolarseException;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("messages")
public class MessageController {

    @Autowired
    MessageRepository messageRepository;
    
    @Secured("ROLE_USER")
    @GetMapping
    public String index(final ModelMap map, final Authentication authentication) {
        final User user = ((HolarsePrincipal) authentication.getPrincipal()).getUser();
        
        final List<Message> messages = messageRepository.allMyMessages(user.getLogin());
        
        map.addAttribute("messages", messages);
        
        return "messages/index";
    }
    
    @Transactional
    @Secured("ROLE_USER")
    @GetMapping("{uuid}")
    public String index(@PathVariable("uuid") final String uuid, final ModelMap map, final Authentication authentication) throws Exception {
        final User user = ((HolarsePrincipal) authentication.getPrincipal()).getUser();
        // TODO Prüfen ob der Benutzer an dieser Unterhaltung beteiligt ist
        
        final Message message = messageRepository.findByUuid(uuid).orElseThrow(() -> new HolarseException("Nachricht " + uuid + " nicht gefunden"));
        
        Hibernate.initialize(message.getRecipients());
        map.addAttribute("message", message);
        
        return "messages/show";
    }    
    
}
