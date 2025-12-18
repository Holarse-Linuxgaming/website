package de.holarse.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebUtils {

    private final static transient Logger logger = LoggerFactory.getLogger(WebUtils.class);

    private WebUtils() {};
    
    private final static String SLUGWORD_DELIMITER = "_";

    //private final String[] removeWords = new String[]{"a","an","as","at","before","but","by","for","from","is","in","into","like","of","off","on","onto","per","since","than","the","this","that","to","up","via","with"};    
    
    public static String transliterate(final String title) {
        return title.toLowerCase()
        .replaceAll(" of ", " ")
        .replaceAll("^of ", " ")
        .replaceAll(" the ", " ")
        .replaceAll("^the ", " ")
        .replaceAll(" to ", " ")
        .replaceAll("^to ", " ")
        .replaceAll(" in ", " ")
        .replaceAll("^in ", " ")        
        .replaceAll(" this ", " ")
        .replaceAll("^this ", " ")
        .replaceAll("€", "E");
    }

    public static String slugify(final String title) {
        // Ungewollte Wörter raus
        // Ungewollte Zeichen raus
        final String r2 = transliterate(title)
                            .replaceAll("_", " ")  
                            .replaceAll(",", " ")                                
                            .replaceAll("\\[", "")
                            .replaceAll("\\]", "")
                            .replaceAll("\\{", "")
                            .replaceAll("\\}", "")   
                            .replaceAll("/", "")             
                            .replaceAll("-", " ")
                            .replaceAll("\\.", "")
                            .replaceAll("&", "")                
                            .replaceAll("!", "")                               
                            .replaceAll(":", " ")
                            .replaceAll("'", "")
                            .replaceAll("\\+", "")
                            .replaceAll("#", "");

        // In Wörter aufsplitten
        final String[] words = r2.split(" ");

        // Wörter zusammenfügen, solange keine 95 Zeichen überschritten sind
        final StringBuffer buffer = new StringBuffer(95);
        for(final String word : words) {
            final String w = word.trim();
            //System.out.println("WORD='" + w + "', buffer: '" + buffer.toString() + "', len: " + buffer.length());
            if (StringUtils.isBlank(w))
                continue;

            // bisheriger slug + "_" + neues wort                
            if ((buffer.length() + w.length() + 1) > 95) { 
                break;
            }

            // Nur am Anfang kein _ hinzufügen
            if (buffer.length() > 0) {
                buffer.append(SLUGWORD_DELIMITER);
            }

            buffer.append(word);
        }

        return buffer.toString();
    }

    
    
}
