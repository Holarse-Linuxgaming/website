package de.holarse.renderer.html;

import de.holarse.renderer.Mode;

public class PureLinkMode implements Mode {

    private final StringBuilder buffer = new StringBuilder(100);
    private boolean complete = false;

    @Override
    public void handle(char ch) {
        if (ch == ' ' || ch == '\r') { 
            complete = true;            
        } else {
            buffer.append(ch);
        }
        
    }

    @Override
    public StringBuilder render() {
        final String url = buffer.toString();
        final String upcaseUrl = url.toUpperCase();
        
        final StringBuilder output = new StringBuilder(100);
        if (upcaseUrl.endsWith(".JPG") || upcaseUrl.endsWith(".PNG") || upcaseUrl.endsWith(".GIF") || upcaseUrl.endsWith(".JPEG")) {
            output.append("<img src=\"").append(url).append("\" />");
        } else {
            output.append("<a href=\"").append(buffer).append("\">").append(buffer).append("</a>");
        }
        
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
    
}
