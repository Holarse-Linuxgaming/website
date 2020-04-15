package de.holarse.backend.views.admin;

import de.holarse.backend.db.types.DrueckblickCategory;

/**
 * ViewModel für Drückblick-Einträge im Adminbereich
 */
public class DrueckblickEntryAdminView implements AdminView {

    private Long id;
    private DrueckblickAdminView drueckblick;
    private DrueckblickCategory category;
    private String bearer;
    private String message;
    private String link;
    private String created;
    private boolean deleted;    

    public DrueckblickAdminView getDrueckblick() {
        return drueckblick;
    }

    public void setDrueckblick(DrueckblickAdminView drueckblick) {
        this.drueckblick = drueckblick;
    }

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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    

    
}