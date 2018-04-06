package de.holarse.backend.db;

import java.util.StringJoiner;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="articles")
@Entity
public class Article extends SluggableNode {
    
    private String title;
    private String alternativeTitle1;
    private String alternativeTitle2;
    private String alternativeTitle3;
    
    @Transient
    private String alternativeTitles;

    @Transient
    private String url;


    public String getAlternativeTitles() {
        return alternativeTitles;
    }
    
    @Override    
    public String getUrl() {
        return url;
    }    
    
    @PostLoad
    private void articlePostLoad() {
        final StringJoiner titles = new StringJoiner(", ");
        if (alternativeTitle1 != null) { titles.add(alternativeTitle1); }
        if (alternativeTitle2 != null) { titles.add(alternativeTitle2); }
        if (alternativeTitle3 != null) { titles.add(alternativeTitle3); }
        this.alternativeTitles = titles.toString();
        
        this.url = "/wiki/" + getId();
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlternativeTitle1() {
        return alternativeTitle1;
    }

    public void setAlternativeTitle1(String alternativeTitle1) {
        this.alternativeTitle1 = alternativeTitle1;
    }

    public String getAlternativeTitle2() {
        return alternativeTitle2;
    }

    public void setAlternativeTitle2(String alternativeTitle2) {
        this.alternativeTitle2 = alternativeTitle2;
    }

    public String getAlternativeTitle3() {
        return alternativeTitle3;
    }

    public void setAlternativeTitle3(String alternativeTitle3) {
        this.alternativeTitle3 = alternativeTitle3;
    }
  
}
