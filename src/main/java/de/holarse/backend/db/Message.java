package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * @author comrad
 */
@Entity
@Table(name = "messages")
public class Message extends Base implements LinkableNode, Cloneable {

    /**
     * Postfach in dem diese Nachricht liegt.
     */
    private String mailbox;
    
    /**
     * Der Betreff der Nachricht
     */
    private String topic;
    
    /**
     * Der Nachrichteninhalt
     */
    @Column(length = 4096)
    private String content;
    
    /**
     * Der Autor, kann entweder ein @holarseuser sein, oder eine email@email.com, wenn
     * sie zum Beispiel von einem Webseiten-Gast stammt. Möglich soll das zum Beispiel
     * über das Kontaktformular oder die Melde- oder Feedback-Funktion sein.
     */
    private String author;
    
    /**
     * Eine semikolongetrennte Liste der Empfänger. Kann gemischt @holarseusern und
     * Email-Adressen sein.
     */
    private String recipients;

    /**
     * Eine UUID für einen Thread, damit Nachrichten organisiert angezeigt werden können (Replys).
     */
    private String thread;
    
    /**
     * Eine eindeutige Nachrichten-ID
     */
    private String uuid;

    /**
     * Nachricht bereits gelesen?
     */
    @Column(columnDefinition = "boolean default false")
    private boolean read;

    /**
     * Nachricht verschickt? Nur relevant bei Email-Nachrichten
     */
    @Column(columnDefinition = "boolean default false")    
    private boolean sent;

    
    @Override
    public String getUrlid() { return null; }
    
    @Override
    public String getUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("messages").append("/").append(getUuid());
        return sb.toString();
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    @Override
    public String toString() {
        return "Message{" + "mailbox=" + mailbox + ", topic=" + topic + ", content=" + content + ", author=" + author + ", recipients=" + recipients + ", thread=" + thread + ", uuid=" + uuid + ", read=" + read + ", sent=" + sent + '}';
    }
    
}
