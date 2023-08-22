/*
 * Copyright (C) 2023 comrad
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.holarse.backend.db;

import java.io.Serializable;
import java.time.OffsetDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author comrad
 */
@Table(name="node_pagevisits")
@Entity
public class PageVisit implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  
    
    @Column(name = "nodeid")
    private Integer nodeId;
    
    @Column(name="visitorid")
    private String visitorId;
    
    @Column(name = "campaign_keyword", length = 255)
    private String campaignKeyWord;
    
    @Column(name = "campaign_name", length = 255)
    private String campaginName;
    
    @Column(name="httpstatus")
    private Integer httpStatus;
    
    @Column(name = "ipaddress", length = 255)
    private String ipAddress;    
    
    @Column(name = "referer", length = 255)
    private String referer;        
    
    @Column(name = "searchword", length = 255)
    private String searchWord;            
    
    @Column(name = "url", length = 2083)
    private String url;                
    
    @Column(name = "useragent", length = 255)
    private String userAgent;                    
    
    @Column(name="bot", columnDefinition = "boolean default false")
    private boolean bot;
    
    @Column(insertable = false, name = "accessed", columnDefinition = "TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP")    
    private OffsetDateTime accessed;      

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNodeId() {
        return nodeId;
    }

    public void setNodeId(Integer nodeId) {
        this.nodeId = nodeId;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getCampaignKeyWord() {
        return campaignKeyWord;
    }

    public void setCampaignKeyWord(String campaignKeyWord) {
        this.campaignKeyWord = campaignKeyWord;
    }

    public String getCampaginName() {
        return campaginName;
    }

    public void setCampaginName(String campaginName) {
        this.campaginName = campaginName;
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public OffsetDateTime getAccessed() {
        return accessed;
    }

    public void setAccessed(OffsetDateTime accessed) {
        this.accessed = accessed;
    }
    
}
