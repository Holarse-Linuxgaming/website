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
    
    private boolean internalLink, externalLink = false;
    
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
            externalLink = false;
            internalLink = true;
        } else if (count == 1) {
            internalLink = false;
            externalLink = true;
        }

        // Zeichen hinzufügen, mit Sonderzeichenunterstützung
        if (ch != '[' && ch != ']') {
            if (internalLink) {
                if (ch == '|') {
                    buffer = labelBuffer; // Wechseln
                    labelMode = true;
                } else {
                    buffer.append(ch);
                }
            } else if (externalLink) {
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
        String target = targetBuffer.toString();
    
        if (!target.toUpperCase().startsWith("HTTP")) {
            targetResult.append("/html/").append(WebUtils.slugify(target));
        } else {
            targetResult.append(target);
        }
        
        if (labelBuffer.length() == 0) {
            labelBuffer.append(targetBuffer);
        }
        String label = labelBuffer.toString().trim();        
        
        final StringBuilder result = new StringBuilder(100);
        result.append("<a href=\"").append(targetResult).append("\">").append(label).append("</a>");
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
