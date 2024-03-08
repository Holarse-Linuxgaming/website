package de.holarse.web.controller.commands;

import de.holarse.backend.view.AttachmentView;
import de.holarse.backend.view.SettingsView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNodeForm {

    private int nodeId;
    @NotBlank
    @Size(min = 10)
    private String content;

    private List<AttachmentView> websiteLinks = new ArrayList<>();

    private SettingsView settings = new SettingsView();

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<AttachmentView> getWebsiteLinks() {
        return websiteLinks;
    }

    public void setWebsiteLinks(List<AttachmentView> websiteLinks) {
        this.websiteLinks = websiteLinks;
    }

    public SettingsView getSettings() {
        return settings;
    }

    public void setSettings(SettingsView settings) {
        this.settings = settings;
    }

}
