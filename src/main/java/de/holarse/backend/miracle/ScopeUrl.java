package de.holarse.backend.miracle;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class ScopeUrl 
{
    public ScopeUrl() {
    }

    public ScopeUrl(final String url, final String clazz) {
        this.value = url;
        this.clazz = clazz;
    }

    @JacksonXmlProperty(isAttribute = true, localName = "class")
    private String clazz;

    @JacksonXmlText
    private String value;

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    

}