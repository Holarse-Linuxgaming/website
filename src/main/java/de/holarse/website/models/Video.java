/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.website.models;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name="videos")
public class Video extends EntityBase {
    
    @Column(length = 2048)
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
