package de.holarse.renderer.html;

import de.holarse.renderer.Mode;
import de.holarse.renderer.input.element.HeaderElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HeaderMode implements Mode {

    Logger logger = LoggerFactory.getLogger(HeaderMode.class);
    
    private StringBuilder buffer = new StringBuilder(30);
    private int depth = 0;
    private int depthOut = 0;
    
    @Override
    public void handle(final char ch) {
        if (HeaderElement.matches(ch)) {
            if (buffer.length() == 0) {
                depth++;
            } else {
                depthOut++;
            }
            
            if (depth > 6) {
                depth = 6;
            }
        } else {
            buffer.append(ch);
        }
        
        logger.debug("handling: char: {}, depth: {}, buffer: {} ({})", new Object[] { ch, depth, buffer.toString(), buffer.length()});
    }

    @Override
    public StringBuilder render() {
        final StringBuilder n = new StringBuilder(35);
        n.append("<h").append(depth).append(">").append(buffer.toString().trim()).append("</h").append(depth).append(">");
        return n;        
    }
    
    @Override
    public boolean isComplete() {
        return depth == depthOut && buffer.length() > 0;
    }
    
    // Eventuell um Speicher zu sparen
    @Override
    public void clear() {
        buffer = new StringBuilder(30);
        depth = 0;
    }
}
