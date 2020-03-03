package de.holarse.services;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author comrad
 */
public class WebUtils {
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
                            .replaceAll(":", "")
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
    
    public static String format(final OffsetDateTime datetime) {
        if (datetime == null) return "";
        
        return datetime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.MEDIUM).withLocale(Locale.GERMAN));
    }

    /**
     * Entfernt Google Analytics, Partner, Tracking- oder Session-IDs aus der URL
     * @param url
     * @return
     */
    public static String antispyUrl(final String url) {
        if (url.toLowerCase().contains("humblebundle")) {
            return url.replaceAll("\\?partner=.*", "");
        }

        return url;
    }
}
