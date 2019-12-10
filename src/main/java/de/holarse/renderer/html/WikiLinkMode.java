package de.holarse.renderer.html;

import de.holarse.renderer.Mode;
import de.holarse.services.WebUtils;

public class WikiLinkMode implements Mode {

    private StringBuilder buffer;
    private final StringBuilder labelBuffer = new StringBuilder(100);
    private final StringBuilder targetBuffer = new StringBuilder(100);
    
    private int count = 0;
    private int decount = 0;
    
    private boolean complete = false;
    
    private enum LinkType {
        internal,
        external
    }
    
    private LinkType linkType = LinkType.internal;
    
    private boolean labelMode = false;

    public WikiLinkMode() {
        buffer = targetBuffer; // Zuerst immer alles als Target sehen
    }
    
    @Override
    public void handle(char ch) {
        if (ch == '[') {
            count++;
            return;
        }
        
        if (ch == ']') {
            decount++;
        }
        
        if (count == 2) {
            linkType = LinkType.internal;
        } else if (count == 1) {
            linkType = LinkType.external;
        }

        // Zeichen hinzufügen, mit Sonderzeichenunterstützung
        if (ch != '[' && ch != ']') {
            switch (linkType) {
                case internal:
                    if (ch == '|') {
                        buffer = labelBuffer; // Wechseln
                        labelMode = true;
                    } else {
                        buffer.append(ch);
                    }                    
                    break;
                case external:
                    switch(ch) {
                        case ' ':
                            // Wechseln, wenn noch im Targetmodus
                            if (!labelMode) {
                                buffer = labelBuffer;
                                labelMode = true;
                            } else {
                                buffer.append(ch);
                            }
                            break;                        
                        case '|':
                            // Wechseln, wenn noch im Targetmodus
                            if (!labelMode) {
                                buffer = labelBuffer;
                                labelMode = true;
                            }                        
                            break;
                        default:
                            // In den Buffer eintragen
                            buffer.append(ch);
                    }                    
                    break;
                default:
                    throw new IllegalStateException("Unbehandelter Linkmodus: " + linkType);
                    
            }
        }
        
        // Ausgang fertig, jetzt URL erzeugen falls nötig
        if (ch == ']' && decount == count) {
//            if (targetBuffer.length() == 0) {
//                targetBuffer.append(buffer);
//            }
//            
//            if (labelBuffer.length() == 0) {
//                labelBuffer.append(buffer);
//            }
            complete = true;
        }
    }

    @Override
    public StringBuilder render() {
        
        
        StringBuilder targetResult = new StringBuilder(100);
        String linkTarget = targetBuffer.toString();
        String targetMode = null;
        String cssClass = null;
    
        if (LinkType.external.equals(linkType) && linkTarget.toUpperCase().startsWith("HTTP")) { 
            // external link
            targetResult.append(linkTarget);
            targetMode = "_blank";
            cssClass = "external-link";
        } else {
            // internal link
            targetResult.append("/wiki/").append(WebUtils.slugify(linkTarget));
        }
        
        if (labelBuffer.length() == 0) {
            labelBuffer.append(targetBuffer);
        }
        String label = labelBuffer.toString().trim();        
        
        final StringBuilder result = new StringBuilder(100);
        result.append("<a").append(" href=\"").append(targetResult).append("\"");

        // CSS-Klasse setzen
        if (cssClass != null) {
            result.append(" class=\"") .append(cssClass).append("\"");        
        }        
        
        // Target-Modus setzen        
        if (targetMode != null) {        
            result.append(" target=\"").append(targetMode)     .append("\"");
        }
        
        result.append(">").append(label).append("</a>");

        return result;
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
