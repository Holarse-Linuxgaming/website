package de.holarse.backend.db;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "drueckblick")
public class Drueckblick extends Base {

    private String name;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL")    
    private OffsetDateTime dbStart;
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL")    
    private OffsetDateTime dbEnd;

    @OneToMany
    private List<DrueckblickEntry> entries = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getDbStart() {
        return dbStart;
    }

    public void setDbStart(OffsetDateTime dbStart) {
        this.dbStart = dbStart;
    }

    public OffsetDateTime getDbEnd() {
        return dbEnd;
    }

    public void setDbEnd(OffsetDateTime dbEnd) {
        this.dbEnd = dbEnd;
    }

}