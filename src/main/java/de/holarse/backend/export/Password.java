package de.holarse.backend.export;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import java.io.Serializable;

@JacksonXmlRootElement(localName = "password")
public class Password implements Serializable {
    
    @JacksonXmlProperty(localName = "type", isAttribute = true)
    private String type;
    
    @JacksonXmlText
    private String digest;    

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
    
}
