package de.holarse.backend.miracle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country 
{
    @JsonProperty("country-code")
    private String countryCode;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    
}