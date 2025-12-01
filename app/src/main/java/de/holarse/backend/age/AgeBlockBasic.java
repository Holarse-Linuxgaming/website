package de.holarse.backend.age;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "ageblock-basic")
public class AgeBlockBasic 
{
    @JsonProperty("age-issuer")
    private String ageIssuer;
    @JsonProperty("last-change")
    //@JsonSerialize(using = W3cDateOnlyFormatter.class)    
    private String lastChange;
    @JsonProperty("country")    
    private String country;
    @JsonProperty("label-version")    
    private String version;
    @JsonProperty("revisit-after")    
    private String revisitAfter;

    public String getAgeIssuer() {
        return ageIssuer;
    }

    public void setAgeIssuer(String ageIssuer) {
        this.ageIssuer = ageIssuer;
    }

    public String getLastChange() {
        return lastChange;
    }

    public void setLastChange(String lastChange) {
        this.lastChange = lastChange;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRevisitAfter() {
        return revisitAfter;
    }

    public void setRevisitAfter(String revisitAfter) {
        this.revisitAfter = revisitAfter;
    }

    
}