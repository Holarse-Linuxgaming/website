package de.holarse.web.services;

import de.holarse.mail.AbstractMailMessage;
import de.holarse.queues.commands.MailCommand;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailRenderService {

    @Qualifier("emailTemplateEngine")
    @Autowired
    TemplateEngine templateEngine;
    
    protected String render(final AbstractMailMessage mailMessage) {
        return templateEngine.process(mailMessage.getTemplate(), new Context(Locale.GERMANY, mailMessage.getKv()));
    }
    
    /**
     * Konvertiert die MailMessage in ein Mail-Transport-Objekt für JMS
     * @param mailMessage
     * @return 
     */
    public MailCommand to(final AbstractMailMessage mailMessage) {
        final MailCommand cmd = new MailCommand();
        
        cmd.setSender(mailMessage.getFrom());
        cmd.setSubject(mailMessage.getSubject());
        cmd.setRecipients(mailMessage.getRecipients().toArray(new String[mailMessage.getRecipients().size()]));
        cmd.setBody(render(mailMessage));
                
        return cmd;
    }
    
}
