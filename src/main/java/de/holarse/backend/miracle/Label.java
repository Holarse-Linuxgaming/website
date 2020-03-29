package de.holarse.backend.miracle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "label", namespace = "http://www.miracle-label.eu/ns/2.0/")
public class Label 
{
    @JsonProperty("age-declaration")
    private AgeDeclaration ageDeclaration;

    public AgeDeclaration getAgeDeclaration() {
        return ageDeclaration;
    }

    public void setAgeDeclaration(AgeDeclaration ageDeclaration) {
        this.ageDeclaration = ageDeclaration;
    }
    
}