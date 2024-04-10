package de.holarse.queues.commands;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractMailMessage implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private final List<String> recipients = new ArrayList<>(10);
    private final String subject;
    private final Map<String, Object> kv = new HashMap<>(25);
    private String template;
    private String from;

    public AbstractMailMessage(final List<String> recipients, final String from, final String subject) {
        this.recipients.addAll(recipients);
        this.from = from;
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void addValues(final Map<String, Object> kv) {
        this.kv.putAll(kv);
    }

    public Map<String, Object> getKv() {
        return kv;
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
    
}
