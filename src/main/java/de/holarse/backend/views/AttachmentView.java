package de.holarse.backend.views;

import de.holarse.backend.db.types.AttachmentDataType;
import de.holarse.backend.db.types.AttachmentGroup;
import de.holarse.backend.db.types.AttachmentType;

/**
 *
 * @author comrad
 */
public class AttachmentView {
    
    private Long ordering;
    private String description;
    private String data;
    
    /**
     * Die Gruppierung, z.B. Webseite, Links, Bilder, Videos
     */
    private AttachmentGroup group;
    
    /**
     * Die Untereinteilung innerhalb einer Gruppe
     */
    private AttachmentType type;
    
    /**
     * Datentyp, entweder eine URI (Pfad oder URL) oder BASE64.
     */
    private AttachmentDataType datatype;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public AttachmentGroup getGroup() {
        return group;
    }

    public void setGroup(AttachmentGroup group) {
        this.group = group;
    }

    public AttachmentType getType() {
        return type;
    }

    public void setType(AttachmentType type) {
        this.type = type;
    }

    public AttachmentDataType getDatatype() {
        return datatype;
    }

    public void setDatatype(AttachmentDataType datatype) {
        this.datatype = datatype;
    }
    
}
