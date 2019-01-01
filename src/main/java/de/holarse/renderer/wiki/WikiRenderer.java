package de.holarse.renderer.wiki;

import de.holarse.renderer.Renderer;
import de.holarse.services.NodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("wikiRenderer")
public class WikiRenderer implements Renderer {
    @Autowired NodeService nodeService;

    private String getForwardChars(String text, int start, int end) {
        try {
            return text.substring(start, end);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            return "";
        }
    }
    
    protected Mode selectMode(char cchar, String source, int i) {
        if (cchar == '[' && CodeMode.isStartMarker(getForwardChars(source, i, i+6))) {
            return new CodeMode();
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
        
        System.out.println("buffer: " + buffer.toString());
        
        return buffer.toString();      
    }
    
    public NodeService getNodeService() {
        return nodeService;
    }

    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

}
