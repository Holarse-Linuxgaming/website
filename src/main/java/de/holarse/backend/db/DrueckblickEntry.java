package de.holarse.backend.db;

import de.holarse.backend.types.DrueckblickSourceType;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

@Table(name = "drueckblick_entries")
@Entity
public class DrueckblickEntry extends Base implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(length = 255)
    private String reporter;    
    @Column(length = 255)
    private String name;
    @Column(length = 255)    
    private String description;
    @Column(length = 255)    
    private String url;
    @Column(length = 255)    
    private String category;
    
    @Enumerated(EnumType.STRING)
    @Type(type = "com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType")
    private DrueckblickSourceType source;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public DrueckblickSourceType getSource() {
        return source;
    }

    public void setSource(DrueckblickSourceType source) {
        this.source = source;
    }
    
}
