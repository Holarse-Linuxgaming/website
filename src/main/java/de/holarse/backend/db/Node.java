package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import javax.persistence.Version;

@MappedSuperclass
public abstract class Node extends Base implements LegacyImport {
    
    /**
     * Node gelöscht
     */
    @Column(nullable = false, columnDefinition = "boolean default false")    
    private Boolean deleted;
    /**
     * Entwurf
     */
    @Column(nullable = false, columnDefinition = "boolean default false")    
    private Boolean draft;

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
    @Column(nullable = false)
    private ContentType contentType;
        
    @Column(nullable = false, length = 32768)
    private String content;
    
    @ManyToOne
    private User author;

    private Long oldId;
    
    @Version
    private int version;
    
    @Transient
    private int wordCount;

    public abstract String getUrl();

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

    public Boolean getDraft() {
        return draft;
    }

    public void setDraft(Boolean draft) {
        this.draft = draft;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public Boolean getArchived() {
        return archived;
    }

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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
    
}
