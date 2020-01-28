package de.holarse.backend.db;

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
    
    
    
}
