package de.holarse.backend.export;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import java.io.Serializable;

@JacksonXmlRootElement(localName = "tag")
public class Tag implements Serializable {

    @JacksonXmlText
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
}
