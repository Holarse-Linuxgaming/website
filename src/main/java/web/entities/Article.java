package web.entities;

import java.io.Serializable;
import web.entities.sub.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import web.entities.sub.Content;
import web.entities.sub.Title;
import web.entities.sub.TitleType;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Article implements Entity, Serializable {

    @XmlAttribute
    private Long uid;
    
    @XmlElementWrapper(name = "titles")    
    @XmlElement(name = "title")
    private List<Title> titles = new ArrayList<>();

    @XmlElementWrapper(name = "tags")
    @XmlElement(name = "tag")
    private List<Tag> tags = new ArrayList<>();
    
    @XmlElement
    private Content content;

    public Article() {
    }

    public Article(Long uid) {
        this.uid = uid;
    }

    @Override
    public Long getUid() {
        return uid;
    }

    @Override
    public void setUid(Long uid) {
        this.uid = uid;
    }

    public List<Title> getTitles() {
        return titles;
    }
    
    /**
     * TODO Das muss z.B. in einen Decorator verschoben werden.
     * @return 
     */
    public String getMainTitle() {
        return getTitles().stream().filter(t -> t.getType().equals(TitleType.MAIN)).findFirst().get().getTitle();
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
 
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Article{" + "uid=" + uid + ", titles=" + titles + ", tags=" + tags + ", content=" + content + '}';
    }
    
}
