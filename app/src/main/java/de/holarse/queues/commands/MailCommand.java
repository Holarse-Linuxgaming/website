package de.holarse.queues.commands;

import java.io.Serializable;

public class MailCommand implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String sender;
    private String[] recipients;
    private String subject;
    private String body;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String[] getRecipients() {
        return recipients;
    }

    public void setRecipients(String[] recipients) {
        this.recipients = recipients;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "MailCommand{" + "sender=" + sender + ", recipients=" + recipients + ", subject=" + subject + '}';
    }
    
}
