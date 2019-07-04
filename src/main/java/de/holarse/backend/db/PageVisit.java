package de.holarse.backend.db;

import java.io.Serializable;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="accesslog")
@Entity
public class PageVisit implements Serializable {

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE default CURRENT_TIMESTAMP")    
    private OffsetDateTime accessed;
    
    /**
     * JPA Entities benötigen eine Id, die aber nicht unbedingt der realen Tabelle entsprechen muss
     */
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    
    private Long nodeId;
    private String visitorId; // sessionid hashed
    private String ipaddress; // anonymized 
    private String userAgent;
    private String referer;
    @Column(length = 2083) // as per rfc
    private String url;
    private String searchword;
    private String campaignName;
    private String campaignKeyword;
    private Integer httpStatus;
   
    public OffsetDateTime getAccessed() {
        return accessed;
    }

    public void setAccessed(OffsetDateTime accessed) {
        this.accessed = accessed;
    }
    
    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }
    
    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }

    public String getCampaignKeyword() {
        return campaignKeyword;
    }

    public void setCampaignKeyword(String campaignKeyword) {
        this.campaignKeyword = campaignKeyword;
    }

    public String getSearchword() {
        return searchword;
    }

    public void setSearchword(String searchword) {
        this.searchword = searchword;
    }
    
    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    
    
}
