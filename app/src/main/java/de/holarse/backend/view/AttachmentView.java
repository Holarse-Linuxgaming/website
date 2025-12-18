package de.holarse.backend.view;

import de.holarse.backend.db.Attachment;

public class AttachmentView {

    private Integer id;
    private String description;
    private String data;
    private Integer weight;
    private boolean markAsDeleted;

    public static AttachmentView of(final Attachment attachment) {
        final AttachmentView view = new AttachmentView();
        view.setId(attachment.getId());
        view.setData(attachment.getData());
        view.setDescription(attachment.getDescription());
        view.setWeight(attachment.getWeight());
        
        return view;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isMarkAsDeleted() {
        return markAsDeleted;
    }

    public void setMarkAsDeleted(boolean markAsDeleted) {
        this.markAsDeleted = markAsDeleted;
    }

    @Override
    public String toString() {
        return "AttachmentView{" + "id=" + id + ", description=" + description + ", data=" + data + ", weight=" + weight + ", markAsDeleted=" + markAsDeleted + '}';
    }
    
}
