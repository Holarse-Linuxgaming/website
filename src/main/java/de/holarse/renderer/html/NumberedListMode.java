package de.holarse.renderer.html;

public class NumberedListMode extends AbstractListMode {

    @Override
    protected char getItemChar() {
        return '#';
    }

    @Override
    protected String getHtmlTag() {
        return "ol";
    }
    
}
