package web.entities;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import web.entities.sub.Content;
import web.entities.sub.Title;
import web.entities.sub.TitleType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Article {

    @XmlAttribute
    private Long uid;
    
    @XmlElement(name="title")    
    private List<Title> titles = new ArrayList<>();
    
    @XmlElement
    private Content content;

    public Article() {
    }

    public Article(Long uid) {
        this.uid = uid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void addNewTitle(final TitleType type, final String title) {
        titles.add(new Title(type, title));
    }


    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
    
}
