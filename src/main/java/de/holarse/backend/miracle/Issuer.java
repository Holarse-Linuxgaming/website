package de.holarse.backend.miracle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Issuer 
{
    @JsonProperty("age-issuer")
    private String ageIssuer;

    @JsonProperty("last-change")
    private String lastChange;

    @JsonProperty("country")
    private Country country;

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    
}