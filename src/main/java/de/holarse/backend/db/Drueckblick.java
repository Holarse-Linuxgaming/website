package de.holarse.backend.db;

import java.time.LocalDate;
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

    @Column(length = 512)
    private String title;

    private LocalDate coverageBegin;
    private LocalDate coverageEnd;

    @OneToMany(mappedBy = "drueckblick")
    private List<DrueckblickEntry> entries = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getCoverageBegin() {
        return coverageBegin;
    }

    public void setCoverageBegin(LocalDate coverageBegin) {
        this.coverageBegin = coverageBegin;
    }

    public LocalDate getCoverageEnd() {
        return coverageEnd;
    }

    public void setCoverageEnd(LocalDate coverageEnd) {
        this.coverageEnd = coverageEnd;
    }

    public List<DrueckblickEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<DrueckblickEntry> entries) {
        this.entries = entries;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}