
package de.holarse.renderer.html;

public class UnorderedListMode extends AbstractListMode {

    @Override
    protected char getItemChar() {
        return '*';
    }

    @Override
    protected String getHtmlTag() {
        return "ul";
    }
    
}
