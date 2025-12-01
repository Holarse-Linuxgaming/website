package de.holarse.backend.db;

import de.holarse.backend.types.AttachmentDataType;
import io.hypersistence.utils.hibernate.type.basic.PostgreSQLEnumType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "attachment_types")
public class AttachmentType extends Base {
    
    private static final long serialVersionUID = 1L;
    
    @Column(length = 255)
    private String code;
    
    @Column(length = 255)
    private String label;
    
    @ManyToOne
    @JoinColumn(name="attachment_group_id", nullable=false, referencedColumnName = "id")
    private AttachmentGroup attachmentGroup;
    
    @Enumerated(EnumType.STRING)
    @Type(PostgreSQLEnumType.class)
    @Column(columnDefinition = "attachment_data_type", name = "datatype")    
    private AttachmentDataType dataType;

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

    public AttachmentGroup getAttachmentGroup() {
        return attachmentGroup;
    }

    public void setAttachmentGroup(AttachmentGroup attachmentGroup) {
        this.attachmentGroup = attachmentGroup;
    }

    public AttachmentDataType getDataType() {
        return dataType;
    }

    public void setDataType(AttachmentDataType dataType) {
        this.dataType = dataType;
    }

    @Override
    public String toString() {
        return "AttachmentType{" + "code=" + code + ", label=" + label + ", attachmentGroup=" + attachmentGroup + ", dataType=" + dataType + '}';
    }
    
}
