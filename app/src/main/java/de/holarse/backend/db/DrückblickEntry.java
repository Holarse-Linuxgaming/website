package de.holarse.backend.db;

import de.holarse.backend.types.DrückblickSourceType;
import de.holarse.backend.types.NodeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.Type;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

@Table(name = "drückblick_entries")
@Entity
public class DrückblickEntry extends TimestampedBase implements LockableEntity {
    
    private static final long serialVersionUID = 1L;

    @Column(length = 255)
    private String reporter;    
    @Column(length = 255)
    private String name;
    @Column(length = 255)    
    private String message;
    @Column(length = 2048)    
    private String url;
    @Column(length = 2048)    
    private String changelog;    
    @Column(length = 255)    
    private String category;
    
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private DrückblickSourceType source;

    @Column    
    private boolean done;
    @Column    
    private boolean trash;

    @Override
    public NodeType getNodeType() {
        return NodeType.drückblick;
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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public boolean isTrash() {
        return trash;
    }

    public void setTrash(boolean trash) {
        this.trash = trash;
    }
    
}
