package de.holarse.backend.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "attachment_groups")
@Entity
public class AttachmentGroup extends Base {

    private static final long serialVersionUID = 1L;
    
    @Column(length = 255)
    private String code;
    @Column(length = 255)
    private String label;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
}
