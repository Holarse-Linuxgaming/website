package de.holarse.backend.api.drückblick;

import de.holarse.backend.types.DrückblickSourceType;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;

public class DrückblickEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String reporter;
    private String name;
    @NotBlank
    private String message;
    private String url;
    private String changelog;
    private String category;
    private DrückblickSourceType source;    

    public DrückblickEntry() {
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public DrückblickSourceType getSource() {
        return source;
    }

    public void setSource(DrückblickSourceType source) {
        this.source = source;
    }

    public String getChangelog() {
        return changelog;
    }

    public void setChangelog(String changelog) {
        this.changelog = changelog;
    }

    @Override
    public String toString() {
        return "Dr\u00fcckblickEntry{" + "reporter=" + reporter + ", name=" + name + ", message=" + message + ", url=" + url + ", changelog=" + changelog + ", category=" + category + ", source=" + source + '}';
    }
    
}
