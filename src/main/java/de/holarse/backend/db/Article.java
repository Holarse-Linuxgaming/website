/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.holarse.backend.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Table(name = "articles")
@Entity
public class Article extends Base {

    private static final long serialVersionUID = 1L;
    
    private int nodeId;
    private int versionId;
    private int drupalId;

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getDrupalId() {
        return drupalId;
    }

    public void setDrupalId(int drupalId) {
        this.drupalId = drupalId;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }
        
}
