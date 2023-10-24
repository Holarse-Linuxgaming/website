package de.holarse.backend.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Table(name = "article_revisions")
@Entity
public class ArticleRevision extends TimestampedBase {
    
    private static final long serialVersionUID = 1L;
    
    @Column
    @SequenceGenerator(name = "node_sequence", sequenceName = "node_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "node_sequence")    
    private int nodeId;
    @Column
    @SequenceGenerator(name = "revision_sequence", sequenceName = "revision_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revision_sequence")    
    private int revision;
    @Column(length = 512)
    private String title1;
    @Column(length = 512)
    private String title2;
    @Column(length = 512)
    private String title3;
    @Column(length = 512)
    private String title4;
    @Column(length = 512)
    private String title5;
    @Column(length = 512)
    private String title6;
    @Column(length = 512)
    private String title7;
    @Column(length = 16384)    
    private String content;

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getTitle1() {
        return title1;
    }

    public void setTitle1(String title1) {
        this.title1 = title1;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getTitle3() {
        return title3;
    }

    public void setTitle3(String title3) {
        this.title3 = title3;
    }

    public String getTitle4() {
        return title4;
    }

    public void setTitle4(String title4) {
        this.title4 = title4;
    }

    public String getTitle5() {
        return title5;
    }

    public void setTitle5(String title5) {
        this.title5 = title5;
    }

    public String getTitle6() {
        return title6;
    }

    public void setTitle6(String title6) {
        this.title6 = title6;
    }

    public String getTitle7() {
        return title7;
    }

    public void setTitle7(String title7) {
        this.title7 = title7;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
