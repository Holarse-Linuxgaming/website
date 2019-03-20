package de.holarse.web.contact;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.Message;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.MessageRepository;
import de.holarse.backend.db.repositories.RoleRepository;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.web.messages.MessageService;
import java.util.Set;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("contact")
public class ContactController {

    Logger logger = LoggerFactory.getLogger(ContactController.class);
    
    @Autowired
    MessageRepository messageRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    MessageService messageService;
    
    @GetMapping
    public String index(final MessageCommand command, ModelMap map, final Authentication authentication) {
        // Automatisch Nutzername nehmen, wenn eingeloggt.
        if (authentication != null) {
            final User user = ((HolarsePrincipal) authentication.getPrincipal()).getUser();
            command.setUserId(user.getId());
            command.setName(messageService.toHolarseUser(user));
        }

        map.addAttribute("contactCommand", command);

        return "contact/index";
    }
    
    // TODO EDIT
    // TODO DELETE


    // TODO
    protected Set<User> getHolarseCoreUsers() {
        //return roleRepository.findByCodeIgnoreCase("HOLARSE-CORE").get().getUsers();
        return null;
    }
    
    @Transactional
    @PostMapping("send")
    public String send(@Validated @ModelAttribute final MessageCommand command, ModelMap map, final Authentication authentication) throws Exception {
        logger.debug("Recieved message: {}", command);
        map.addAttribute("name", command.getName());
        
        final User currentUser = ((HolarsePrincipal) authentication.getPrincipal()).getUser();
        
        messageService.validateSender(command, currentUser);
        
        final Message m = messageService.createContactMessage(
                command, 
                currentUser, 
                getHolarseCoreUsers().stream().map(u -> messageService.toHolarseUser(u)).collect(Collectors.joining(";")));
        
        messageService.deliver(m);

        return "contact/send";
    }

}
