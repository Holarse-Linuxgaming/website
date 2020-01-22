package de.holarse.renderer.html;

import de.holarse.drupal.Drupal6PasswordEncoder;
import de.holarse.renderer.Mode;
import de.holarse.renderer.Renderer;
import de.holarse.renderer.input.element.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("wikiRenderer")
public class HtmlRenderer implements Renderer {

    Logger log = LoggerFactory.getLogger(Drupal6PasswordEncoder.class);
    
    protected Mode selectMode(char cchar, String source, int i) {
        if (UnorderedListElement.matches(cchar)) {
            return new UnorderedListMode();
        }
        
        if (NumberedListElement.matches(cchar)) {
            return new NumberedListMode();
        }        
        
        if (PureLinkElement.matches(cchar, source, i)) {
            return new PureLinkMode();
        }

        if (BreakElement.matches(cchar, source, i)) {
            return new BreakMode();
        } 
               
        if (CodeElement.matches(cchar, source, i)) {
            return new CodeMode();
        }
        
        if (WikiLinkElement.matches(cchar)) {
            return new WikiLinkMode();
        }                 

        if (TickElement.matches(cchar)) {
            return new TickMode();
        } 
        
        if (ParagraphElement.matches(cchar, source, i)) {
            return new ParagraphMode();
        }

        if (NewLineElement.matches(cchar)) {
            return new NewLineMode();
        } 

        if (HeaderElement.matches(cchar)) {
            return new HeaderMode();
        }     
        
        return null; //new ParagraphMode();
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
