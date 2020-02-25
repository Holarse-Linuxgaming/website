package de.holarse.backend.views;

public class CommentView implements ContentView {

    private Long id;
    private Long nodeId;
    private UserView author;

    private String created;
    private String updated;
    
    private String content;
    private String formattedContent;
    private String plainContent;
    private String teaser;
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public UserView getAuthor() {
        return author;
    }

    public void setAuthor(UserView author) {
        this.author = author;
    }


    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    @Override
    public String getFormattedContent() {
        return formattedContent;
    }

    @Override
    public void setFormattedContent(String formattedContent) {
        this.formattedContent = formattedContent;
    }

    @Override
    public String getPlainContent() {
        return plainContent;
    }

    @Override
    public void setPlainContent(String plainContent) {
        this.plainContent = plainContent;
    }

    @Override
    public String getTeaser() {
        return teaser;
    }

    @Override
    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }
    
}
