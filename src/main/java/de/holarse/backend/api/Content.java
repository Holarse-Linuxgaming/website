package de.holarse.backend.api;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import java.io.Serializable;

@JacksonXmlRootElement(localName = "content")
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @JacksonXmlProperty(localName = "format", isAttribute = true)
    private String format;
    
    @JacksonXmlText
    @JacksonXmlCData    
    private String value;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
