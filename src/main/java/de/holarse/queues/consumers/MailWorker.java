package de.holarse.queues.consumers;

import static de.holarse.config.JmsQueueTypes.QUEUE_MAIL;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailWorker {
    
    @Autowired
    JavaMailSender mailSender;
    
    @JmsListener(destination = QUEUE_MAIL)
    public void sendRegisterMail(final de.holarse.queues.commands.RegisterMailMessage msg) throws MessagingException {
        
        MimeMessage m = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(m, false, "UTF-8");
        helper.setFrom(msg.getFrom());
        helper.setTo(msg.getRecipients().toArray(new String[msg.getRecipients().size()]));
        helper.setSubject(msg.getSubject());
        helper.setText(msg.getTemplate());
        
        mailSender.send(m);
    }
    
}
