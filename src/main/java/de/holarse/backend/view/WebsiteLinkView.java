package de.holarse.backend.view;

import de.holarse.backend.db.Attachment;

public class WebsiteLinkView {

    private Attachment link1; 
    private Attachment link2;
    private Attachment link3;

    public Attachment getLink1() {
        return link1;
    }

    public void setLink1(Attachment link1) {
        this.link1 = link1;
    }

    public Attachment getLink2() {
        return link2;
    }

    public void setLink2(Attachment link2) {
        this.link2 = link2;
    }

    public Attachment getLink3() {
        return link3;
    }

    public void setLink3(Attachment link3) {
        this.link3 = link3;
    }

    @Override
    public String toString() {
        return "WebsiteLinkView{" + "link1=" + link1 + ", link2=" + link2 + ", link3=" + link3 + '}';
    }

}
