package de.holarse.renderer.wiki;

import de.holarse.renderer.Renderer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("wikiRenderer")
public class WikiRenderer implements Renderer {

    private String getForwardChars(String text, int start, int end) {
        try {
            return text.substring(start, end);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            return "";
        }
    }
    
    protected Mode selectMode(char cchar, String source, int i) {
        if (cchar == '*') {
            return new UnorderedListMode();
        }
        
        if (cchar == '#') {
            return new NumberedListMode();
        }        
        
        if ((cchar == 'h' || cchar == 'H') && PureLinkMode.isStartMarker(getForwardChars(source, i, i+4))) {
            return new PureLinkMode();
        }
        
        if (cchar == '<' && BreakMode.isStartMarker(getForwardChars(source, i, i+12))) {
            return new BreakMode();
        } 
               
        if (cchar == '[' && CodeMode.isStartMarker(getForwardChars(source, i, i+6))) {
            return new CodeMode();
        }
        
        if (cchar == '[' && WikiLinkMode.isStartMarker(cchar)) {
            return new WikiLinkMode();
        }                 

        if (TickMode.isStartMarker(cchar)) {
            return new TickMode();
        } 

        if (NewLineMode.isStartMarker(cchar)) {
            return new NewLineMode();
        } 

        if (HeaderMode.isStartMarker(cchar)) {
            return new HeaderMode();
        }     
        
        return null;
    }
            
    
    @Override
    public String render(final String source) {
        if (StringUtils.isBlank(source)) {
            return "";
        }
        
        Mode currentMode = null;
        
        final StringBuilder buffer = new StringBuilder();

        for (int i=0; i < source.length(); i++)
        {
            // Aktuelles Zeichen
            char cchar = source.charAt(i);
            
            //System.out.println("Current Char: " + cchar + ", mode: " + currentMode);
            
            // Modus wählen, wenn aktueller keiner aktiv ist
            if (currentMode == null) {
                currentMode = selectMode(cchar, source, i);
            }
            
            if (currentMode != null) {
                currentMode.handle(cchar);

                if (currentMode.isComplete()) {
                    buffer.append(currentMode.render());
                    currentMode = null;
                }
            } else {
                // Kein besonderer Modus, dann direkt durchreichen
                buffer.append(cchar);
            }
        }
        
        // Sollte noch irgendwas offen sein (durch einen Syntax-Fehler z.B.)
        if (currentMode != null) {
            buffer.append(currentMode.render());
        }
        
        return buffer.toString();      
    }

}
