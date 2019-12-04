package de.holarse.tools.helper;

import de.holarse.backend.export.Attachment;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

public class AttachmentHelper {
    
    /**
     * 
     * @param field
     * @param attachment
     */
    public static void extractInto(final String field, final Attachment attachment) {
        //if (field == null) return;
        
        // Fall1: Reiner Text, fängt mit http an.
        if (field.toUpperCase().startsWith("HTTP") || field.toUpperCase().startsWith("WWW")) {
            attachment.setContent(field);
            return;
        }
        
        // Wiki-Syntax, Klammern weg und das erste Leerzeichen ist der Trenner
        if (field.startsWith("[") && field.length() -1 > 1) {
            // Klammern weg
            String result = field.substring(1, field.length() - 1);

            String link, description = "";
            int firstSpace = result.indexOf(" ");
            if (firstSpace == -1) {
                // Kein Leerzeichen, gibts z.B. in Artikel 1972
                link = result;                
            } else {
                link = result.substring(0, firstSpace);
                description = result.substring(firstSpace, result.length());                
            }
            
            attachment.setContent(link.trim());
            attachment.setDescription(description.trim());
            return;            
        }
        
        if (StringUtils.countMatches(field, ":") > 1) {
            final String[] items = field.split(":", 2);
            System.out.println("items: " + Arrays.toString(items));
            if (items.length != 2) {
                throw new IllegalArgumentException("Mehr als zwei Doppelpunkte. Unbekanntes Format: '" + field + "'");
            }
            
            attachment.setDescription(items[0].trim());
            attachment.setContent(items[1].trim());
            return;
        }

        // Finde http und splitte da einfach.
        if (field.contains("http")) { 
            System.out.println("field mit http: '" + field + "'");
            final int index = StringUtils.indexOf(field, "http");
            if (index != -1) {
                attachment.setDescription(field.substring(0, index).trim());
                attachment.setContent(field.substring(index).trim());
                return;
            }
            
        }
        
        throw new IllegalArgumentException("Unbekannter Edge-Case: '" + field + "'");        
    }
    
}
