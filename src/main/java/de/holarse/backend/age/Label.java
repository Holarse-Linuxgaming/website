package de.holarse.backend.age;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonRootName(value = "label")
public class Label 
{
    @JsonProperty("class")
    @JacksonXmlProperty(isAttribute = true, localName = "class")
    private String clazz;

    @JsonProperty("age")
    private String age;

    @JacksonXmlElementWrapper(useWrapping = false)
    @JsonProperty("scope")
    private List<String> scopes = new ArrayList<>();

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    
}