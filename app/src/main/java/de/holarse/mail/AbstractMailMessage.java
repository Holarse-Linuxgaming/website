package de.holarse.mail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMailMessage {
    
    private final List<String> recipients = new ArrayList<>(10);
    private final String subject;
    private String template;
    private String from;
    private Map<String, Object> kv = new HashMap<>(10);

    public AbstractMailMessage(final List<String> recipients, final String from, final String subject, final String template) {
        this.recipients.addAll(recipients);
        this.from = from;
        this.subject = subject;
        this.template = template;
    }

    public String getSubject() {
        return subject;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Map<String, Object> getKv() {
        return kv;
    }

    public void setKv(Map<String, Object> kv) {
        this.kv = kv;
    }
    
}
