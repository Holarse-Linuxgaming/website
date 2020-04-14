package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.holarse.backend.db.types.DrueckblickCategory;

@Entity
@Table(name = "drueckblick_entries")
public class DrueckblickEntry extends Base {

    @ManyToOne
    private Drueckblick drueckblick;

    /** Die Kategorie unter der das später im Drückblick erscheinen soll */
    @Enumerated(EnumType.STRING)
    private DrueckblickCategory category;

    /** Der Überbringer der Nachricht */
    @Column(nullable = true)
    private String bearer;
    
    @Column(length = 4096)
    private String message;

    @Column(length = 2083)
    private String link;

    public DrueckblickCategory getCategory() {
        return category;
    }

    public void setCategory(DrueckblickCategory category) {
        this.category = category;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Drueckblick getDrueckblick() {
        return drueckblick;
    }

    public void setDrueckblick(Drueckblick drueckblick) {
        this.drueckblick = drueckblick;
    }
}