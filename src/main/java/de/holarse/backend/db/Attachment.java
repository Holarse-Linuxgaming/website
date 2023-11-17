/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.holarse.backend.db;

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
    private AttachmentGroup group;            
    
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

    public AttachmentGroup getGroup() {
        return group;
    }

    public void setGroup(AttachmentGroup group) {
        this.group = group;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
}
