package de.holarse.backend.api.drückblick;

import de.holarse.backend.types.DrückblickSourceType;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DrückblickEntry implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String reporter;
    private String name;
    @NotBlank
    private String message;
    private String url;
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
    
}
