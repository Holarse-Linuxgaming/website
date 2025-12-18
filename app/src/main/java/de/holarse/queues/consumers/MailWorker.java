package de.holarse.queues.consumers;

import static de.holarse.config.JmsQueueTypes.QUEUE_MAIL;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailWorker {
    
    private final static transient Logger logger = LoggerFactory.getLogger(MailWorker.class);
    
    @Autowired
    JavaMailSender mailSender;
    
    @JmsListener(destination = QUEUE_MAIL)
    public void sendRegisterMail(final de.holarse.queues.commands.MailCommand msg) throws MessagingException {
        
        MimeMessage m = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(m, false, "UTF-8");
        helper.setFrom(msg.getSender());
        helper.setTo(msg.getRecipients());
        helper.setSubject(msg.getSubject());
        helper.setText(msg.getBody());
        
        logger.debug("Sending mail {}", msg);
        
        mailSender.send(m);
    }
    
}
