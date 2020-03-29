package de.holarse.backend.age;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "age-declaration")
public class AgeDeclaration
{
    @JsonProperty("ageblock-basic")
    private AgeBlockBasic basic;

    @JsonProperty("ageblock-country")    
    private AgeBlockCountry country;

    @JsonProperty("ageblock-labeltype")    
    private AgeBlockLabelType labelType;

    @JsonProperty("ageblock-labeltype-definition")    
    private AgeBlockLabelTypeDefinition labelTypeDefinition;

    public AgeBlockBasic getBasic() {
        return basic;
    }

    public void setBasic(AgeBlockBasic basic) {
        this.basic = basic;
    }

    public AgeBlockCountry getCountry() {
        return country;
    }

    public void setCountry(AgeBlockCountry country) {
        this.country = country;
    }

    public AgeBlockLabelType getLabelType() {
        return labelType;
    }

    public void setLabelType(AgeBlockLabelType labelType) {
        this.labelType = labelType;
    }

    public AgeBlockLabelTypeDefinition getLabelTypeDefinition() {
        return labelTypeDefinition;
    }

    public void setLabelTypeDefinition(AgeBlockLabelTypeDefinition labelTypeDefinition) {
        this.labelTypeDefinition = labelTypeDefinition;
    }

    
}