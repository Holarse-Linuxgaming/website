package de.holarse.web.controller.commands;

import de.holarse.backend.view.AttachmentView;
import de.holarse.backend.view.SettingsView;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ArticleForm extends AbstractNodeForm {
    @NotBlank
    private String title1;
    private String title2;
    private String title3;
    private String title4;
    private String title5;
    private String title6;
    private String title7;

    @NotBlank
    private String tags;

    private List<FileUploadForm> filepond = new ArrayList<>();

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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public List<FileUploadForm> getFilepond() {
        return filepond;
    }

    public void setFilepond(List<FileUploadForm> filepond) {
        this.filepond = filepond;
    }
}
