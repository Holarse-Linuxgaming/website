package de.holarse.backend.export;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName="attachment")
public class Attachment {

    @JacksonXmlProperty(isAttribute = true)
    private Long prio;
    
    @JacksonXmlProperty(isAttribute = true)
    private String type;
    
    @JacksonXmlProperty(isAttribute = true)
    private String group;    
    
    @JacksonXmlProperty
    private String content;

    @JacksonXmlProperty
    private String description;

    public Long getPrio() {
        return prio;
    }

    public void setPrio(Long prio) {
        this.prio = prio;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Attachment{" + "prio=" + prio + ", type=" + type + ", group=" + group + ", content=" + content + ", description=" + description + '}';
    }
   
    
}
