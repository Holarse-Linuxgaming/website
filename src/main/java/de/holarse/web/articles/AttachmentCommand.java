package de.holarse.web.articles;

import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.types.AttachmentGroup;
import de.holarse.backend.db.types.AttachmentType;

public class AttachmentCommand {
    
    private Long id;
    private Long ordering;
    private String description;
    private String attachmentData;
    private AttachmentType attachmentType;
    private AttachmentGroup attachmentGroup;

    public AttachmentGroup getAttachmentGroup() {
        return attachmentGroup;
    }

    public void setAttachmentGroup(AttachmentGroup attachmentGroup) {
        this.attachmentGroup = attachmentGroup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public AttachmentType getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(AttachmentType attachmentType) {
        this.attachmentType = attachmentType;
    }

    public String getAttachmentData() {
        return attachmentData;
    }

    public void setAttachmentData(String attachmentData) {
        this.attachmentData = attachmentData;
    }
    
    public static AttachmentCommand fromAttachment(final Attachment attachment) {
        final AttachmentCommand cmd = new AttachmentCommand();
        cmd.setId(attachment.getId());
        cmd.setOrdering(attachment.getOrdering());
        cmd.setDescription(attachment.getDescription());
        cmd.setAttachmentGroup(attachment.getAttachmentGroup());
        cmd.setAttachmentType(attachment.getAttachmentType());        
        cmd.setAttachmentData(attachment.getAttachmentData());

        
        return cmd;
    }
}
