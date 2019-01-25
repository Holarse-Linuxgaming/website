package de.holarse.backend.db;

import de.holarse.backend.db.types.AttachmentDataType;
import de.holarse.backend.db.types.AttachmentGroup;
import de.holarse.backend.db.types.AttachmentType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Table(name = "attachments")
@Entity
public class Attachment extends Base {

    // Sortierung
    private Long ordering;
    
    // Beschreibung des Eintrags
    private String description;

    // Die Attachment-Gruppe
    @Enumerated(EnumType.STRING)
    private AttachmentGroup attachmentGroup;
    
    // Der Attachment-Typ
    @Enumerated(EnumType.STRING)
    private AttachmentType attachmentType;    
    
    // Der Attachment-Datentyp
    @Enumerated(EnumType.STRING)
    private AttachmentDataType attachmentDataType;
    
    @Column(length = 4096)
    private String attachmentData;
    
    private Long nodeId;

    public AttachmentGroup getAttachmentGroup() {
        return attachmentGroup;
    }

    public void setAttachmentGroup(AttachmentGroup attachmentGroup) {
        this.attachmentGroup = attachmentGroup;
    }
    
    public Long getOrdering() {
        return ordering;
    }

    public void setOrdering(Long ordering) {
        this.ordering = ordering;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AttachmentDataType getAttachmentDataType() {
        return attachmentDataType;
    }

    public void setAttachmentDataType(AttachmentDataType attachmentDataType) {
        this.attachmentDataType = attachmentDataType;
    }

    public String getAttachmentData() {
        return attachmentData;
    }

    public void setAttachmentData(String attachmentData) {
        this.attachmentData = attachmentData;
    }

    public AttachmentType getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }
    
}
