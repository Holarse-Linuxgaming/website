package de.holarse.backend.age;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "ageblock-labeltype")
public class AgeBlockLabelType 
{
    @JsonProperty("httpheader")
    private String httpHeader;
    @JsonProperty("htmlmeta")
    private String htmlMeta;
    @JsonProperty("phraselabel")    
    private String phraseLabel;
    @JsonProperty("agesubmit")    
    private String ageSubmit;
    @JsonProperty("label-z")    
    private String labelZ;
    @JsonProperty("alternate")    
    private String alternate;
    @JsonProperty("xmlfile")    
    private String xmlFile;    
    @JsonProperty("default-age")    
    private String defaultAge;

    public String getHttpHeader() {
        return httpHeader;
    }

    public void setHttpHeader(String httpHeader) {
        this.httpHeader = httpHeader;
    }

    public String getHtmlMeta() {
        return htmlMeta;
    }

    public void setHtmlMeta(String htmlMeta) {
        this.htmlMeta = htmlMeta;
    }

    public String getPhraseLabel() {
        return phraseLabel;
    }

    public void setPhraseLabel(String phraseLabel) {
        this.phraseLabel = phraseLabel;
    }

    public String getAgeSubmit() {
        return ageSubmit;
    }

    public void setAgeSubmit(String ageSubmit) {
        this.ageSubmit = ageSubmit;
    }

    public String getLabelZ() {
        return labelZ;
    }

    public void setLabelZ(String labelZ) {
        this.labelZ = labelZ;
    }

    public String getAlternate() {
        return alternate;
    }

    public void setAlternate(String alternate) {
        this.alternate = alternate;
    }

    public String getXmlFile() {
        return xmlFile;
    }

    public void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    public String getDefaultAge() {
        return defaultAge;
    }

    public void setDefaultAge(String defaultAge) {
        this.defaultAge = defaultAge;
    }

    
}