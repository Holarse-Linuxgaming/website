package de.holarse.backend.db;

import java.time.OffsetDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author comrad
 */
@Entity
@Table(name = "menuitems")
public class Menu extends Base {
    
    @Column(length = 512)
    private String label;
    @Column(length = 2048)
    private String url;
    @Column(columnDefinition = "int default 0")
    private int weight;
    
    @Column(length = 2048)
    private String description;
    
    private String image;
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")    
    private OffsetDateTime validFrom;
    
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")    
    private OffsetDateTime validUntil;    
    
    @Column(nullable = false, columnDefinition = "boolean default true")     
    private boolean active;
    
    @ManyToOne
    private Menu parent;
    
    @OneToMany(mappedBy = "parent")
    private List<Menu> children;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public OffsetDateTime getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(OffsetDateTime validFrom) {
        this.validFrom = validFrom;
    }

    public OffsetDateTime getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(OffsetDateTime validUntil) {
        this.validUntil = validUntil;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
}
