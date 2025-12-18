package de.holarse.web.controller.commands;

import jakarta.validation.constraints.NotBlank;

public class NewsForm extends AbstractNodeForm {

    @NotBlank
    private String title;

    private int newsCategoryId;

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public int getNewsCategoryId() {
        return newsCategoryId;
    }

    public void setNewsCategoryId(int newsCategoryId) {
        this.newsCategoryId = newsCategoryId;
    }
}
