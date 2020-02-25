package de.holarse.renderer.input.element;

import de.holarse.renderer.html.CodeMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractWikiElement {
    
    static Logger logger = LoggerFactory.getLogger(AbstractWikiElement.class);
    
    protected static String getForwardChars(final String text, final int start, final int end) {
        try {
            return text.substring(start, end);
        } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
            logger.error(text, e);
            return "";
        }
    }
}
