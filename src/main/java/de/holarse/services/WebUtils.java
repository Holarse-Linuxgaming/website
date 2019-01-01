package de.holarse.services;

/**
 *
 * @author comrad
 */
public class WebUtils {
    private WebUtils() {};
    
    //private final String[] removeWords = new String[]{"a","an","as","at","before","but","by","for","from","is","in","into","like","of","off","on","onto","per","since","than","the","this","that","to","up","via","with"};    
    
    public static String slugify(final String title) {
        return title.toLowerCase()
                .replaceAll(" of ", " ")
                .replaceAll("^of ", " ")
                .replaceAll(" the ", " ")
                .replaceAll("^the ", " ")
                .replaceAll(" to ", " ")
                .replaceAll("^to ", " ")
                .replaceAll(" this ", " ")
                .replaceAll("^this ", " ")                
                .replaceAll("_", " ")  
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
                .replaceAll("_+", "_");
    }    
}
