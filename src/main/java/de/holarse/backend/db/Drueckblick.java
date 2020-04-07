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
    private OffsetDateTime coverageBegin;
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE NOT NULL")    
    private OffsetDateTime coverageEnd;

    @OneToMany(mappedBy = "drueckblick")
    private List<DrueckblickEntry> entries = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getCoverageBegin() {
        return coverageBegin;
    }

    public void setCoverageBegin(OffsetDateTime coverageBegin) {
        this.coverageBegin = coverageBegin;
    }

    public OffsetDateTime getCoverageEnd() {
        return coverageEnd;
    }

    public void setCoverageEnd(OffsetDateTime coverageEnd) {
        this.coverageEnd = coverageEnd;
    }

    public List<DrueckblickEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<DrueckblickEntry> entries) {
        this.entries = entries;
    }


}