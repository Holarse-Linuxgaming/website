package de.holarse.services;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author comrad
 */
public class WebUtils {
    private WebUtils() {};
    
    //private final String[] removeWords = new String[]{"a","an","as","at","before","but","by","for","from","is","in","into","like","of","off","on","onto","per","since","than","the","this","that","to","up","via","with"};    
    
    public static String slugify(final String title) {
        final String r1 = title.toLowerCase()
                                .replaceAll(" of ", " ")
                                .replaceAll("^of ", " ")
                                .replaceAll(" the ", " ")
                                .replaceAll("^the ", " ")
                                .replaceAll(" to ", " ")
                                .replaceAll("^to ", " ")
                                .replaceAll(" this ", " ")
                                .replaceAll("^this ", " ")                
                                .replaceAll("_", " ")  
                                .replaceAll(",", " ")                                
                                .trim()
                                .replaceAll(" ", "_")
                                .replaceAll("\\[", "")
                                .replaceAll("\\]", "")
                                .replaceAll("\\{", "")
                                .replaceAll("\\}", "")                
                                .replaceAll("-", " ")
                                .replaceAll("\\.", "")
                                .replaceAll("&", "")                
                                .replaceAll("!", "")                               
                                .replaceAll(":", "")
                                .replaceAll("'", "")
                                .replaceAll("\\+", "")
                                .trim()
                                .replaceAll(" ", "_")                
                                .replaceAll("_+", "_")
                                .replaceAll("#", "")
                                .trim();

        if (r1.length() > 95) {
            return r1.substring(0, 95);
        }
        
        return r1;
    }    
}
