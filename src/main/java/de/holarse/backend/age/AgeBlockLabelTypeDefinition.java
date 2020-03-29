package de.holarse.backend.age;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "ageblock-labeltype-definition")
public class AgeBlockLabelTypeDefinition 
{
    @JsonProperty("labeltype-xmlfile")
    private LabelTypeXmlFile xmlfile;

    public LabelTypeXmlFile getXmlfile() {
        return xmlfile;
    }

    public void setXmlfile(LabelTypeXmlFile xmlfile) {
        this.xmlfile = xmlfile;
    }

    
}