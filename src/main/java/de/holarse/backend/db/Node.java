package de.holarse.backend.db;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import javax.persistence.FetchType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@MappedSuperclass
public abstract class Node extends Base implements LegacyImport, LinkableNode, NodeState {
    
    /**
     * Node gelöscht
     */
    @Column(nullable = false, columnDefinition = "boolean default false")    
    private Boolean deleted;

    /**
     * Für Änderungen ausser Admins gesperrt
     */
    @Column(nullable = false, columnDefinition = "boolean default false")    
    private Boolean locked;
    /**
     * Veröffentlicht
     */
    @Column(nullable = false, columnDefinition = "boolean default true")    
    private Boolean published;
    /**
     * Archiviert
     */
    @Column(nullable = false, columnDefinition = "boolean default false")    
    private Boolean archived;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'WIKI'")
    private ContentType contentType;
        
    @Column(nullable = false, length = 32768)
    private String content;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    // ID aus Drupal
    private Long oldId;
    
    @Transient
    private int wordCount;
    
    @OneToMany(mappedBy = "nodeId", fetch = FetchType.LAZY)
    @Cascade({CascadeType.DELETE, CascadeType.SAVE_UPDATE})    
    private List<Attachment> attachments = new ArrayList<>();

    @Override
    public Long getOldId() {
        return oldId;
    }

    @Override
    public void setOldId(Long oldId) {
        this.oldId = oldId;
    }
    
    public int getWordCount() {
        return wordCount;
    }

    @PostLoad
    private void nodePostLoad() {
        this.wordCount = getContent().split(" ").length;
    }    
    
    
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public Boolean getLocked() {
        return locked;
    }

    @Override    
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Override    
    public Boolean getPublished() {
        return published;
    }

    @Override    
    public void setPublished(Boolean published) {
        this.published = published;
    }

    @Override    
    public Boolean getArchived() {
        return archived;
    }

    @Override    
    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
    
}
