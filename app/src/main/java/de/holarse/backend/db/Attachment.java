/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.holarse.backend.db;

import de.holarse.backend.view.AttachmentView;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "attachments")
@Entity
public class Attachment extends TimestampedBase {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name = "nodeid")
    private Integer nodeId;
    
    @Column
    private int weight;
    
    @Column(length = 255)
    private String description;
    
    @ManyToOne
    @JoinColumn(name="attachment_type_id", nullable=false, referencedColumnName = "id")
    private AttachmentType attachmentType;            
    
    @Column(name = "attachment_data", length = 4096)
    private String data;

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Attachment{" + "nodeId=" + nodeId + ", weight=" + weight + ", description=" + description + ", attachmentType=" + attachmentType + ", data=" + data + '}';
    }
    
    /**
     * Ein neues Attachment aus einem View erzeugen
     * @param av
     * @param nodeId
     * @param attachmentType
     * @return 
     */
    public static Attachment build(final AttachmentView av, final Integer nodeId, final AttachmentType attachmentType) {
        final Attachment att = new Attachment();
        att.setData(av.getData());
        att.setDescription(av.getDescription());
        att.setWeight(av.getWeight());
        att.setNodeId(nodeId);
        att.setAttachmentType(attachmentType);
        
        return att;
    }
    
}
