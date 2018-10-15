package de.holarse.web.contact;

import de.holarse.auth.web.HolarsePrincipal;
import de.holarse.backend.db.ContentType;
import de.holarse.backend.db.Message;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.MessageRepository;
import de.holarse.backend.db.repositories.RoleRepository;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.services.NodeService;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
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
    NodeService nodeService;
    
    @GetMapping
    public String index(final ContactCommand command, ModelMap map, final Authentication authentication) {
        // Automatisch Nutzername nehmen, wenn eingeloggt.
        if (authentication != null) {
            final User user = ((HolarsePrincipal) authentication.getPrincipal()).getUser();
            command.setUserId(user.getId());
            command.setEmail(user.getEmail());
            command.setName(user.getLogin());
        }

        map.addAttribute("contactCommand", command);

        return "contact/index";
    }
    
    protected User createVirtualUser(ContactCommand command) {
        final User user = new User();
        user.setVirtual(Boolean.TRUE);
        
        user.setLogin(command.getName());
        user.setEmail(command.getEmail());
        
        return user;
    }
    
    protected Set<User> getHolarseCoreUsers() {
        return roleRepository.findByCodeIgnoreCase("HOLARSE-CORE").get().getUsers();
    }
    
    @PostMapping("send")
    public String send(@Validated @ModelAttribute final ContactCommand command, ModelMap map) throws Exception {
        logger.debug("Recieved message: {}", command);
        map.addAttribute("name", command.getName());
        
        final User user = command.getUserId() != null ? userRepository.findById(command.getUserId()).get() : createVirtualUser(command);
        
        final Message m = nodeService.initCommentableNode(Message.class);
        m.setContentType(ContentType.PLAIN);
        m.setRecipients(getHolarseCoreUsers());
        m.setTopic(command.getTopic());
        m.setContent(command.getMessage());
        m.setUuid(UUID.randomUUID().toString());
        m.setCreated(OffsetDateTime.now());
        
        if (!user.getVirtual()) {
            m.setAuthor(user);
        } else {
            m.setAuthorName(command.getName());
            m.setAuthorEmail(command.getEmail());            
        }
        
        messageRepository.save(m);
        
        return "contact/send";
    }

}
