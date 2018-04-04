package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="pagevisits")
@Entity
public class PageVisit extends Base {

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
