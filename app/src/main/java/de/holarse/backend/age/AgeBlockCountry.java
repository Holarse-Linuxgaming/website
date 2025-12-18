package de.holarse.backend.age;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "ageblock-country")
public class AgeBlockCountry 
{
    @JsonProperty("country-default")
    private String countryDefault;

    public String getCountryDefault() {
        return countryDefault;
    }

    public void setCountryDefault(String countryDefault) {
        this.countryDefault = countryDefault;
    }
}