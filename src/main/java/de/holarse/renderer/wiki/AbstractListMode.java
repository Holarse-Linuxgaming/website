package de.holarse.renderer.wiki;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractListMode implements Mode {

    private final List<String> elements = new ArrayList<>(10);
    private StringBuilder buffer = new StringBuilder(30);

    private int newlines = 0;
    private boolean complete = false;
    
    @Override
    public void handle(char ch) {
        // Neues Element weil Zeilenende
        if (ch == '\n') {
            if (buffer.length() > 0) {
                elements.add(buffer.toString());
                buffer = new StringBuilder(30);
            }
            newlines++;
            return;
        }
        
        if (ch == getItemChar()) {
            // Neues Element anlegen
            buffer = new StringBuilder(30);
            newlines = 0; // Doch noch weitere Zeile
            return;
        }
        
        if (newlines >= 2) {
            complete = true;
        }
        
        buffer.append(ch);
        newlines = 0;
    }

    @Override
    public StringBuilder render() {
        final StringBuilder output = new StringBuilder(100);
        output.append("<").append(getHtmlTag()).append(">");

        for(String element : elements) {
            output.append("<li>").append(element.trim()).append("</li>");
        };
        
        output.append("</").append(getHtmlTag()).append(">");
        
        return output;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isComplete() {
        return complete;
    }
    
    protected abstract char getItemChar();
    protected abstract String getHtmlTag();
    
}
