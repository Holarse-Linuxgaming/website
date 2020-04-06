package de.holarse.services;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UrlPathHelper;

import de.holarse.services.views.QueryKV;

/**
 *
 * @author comrad
 */
public class WebUtils {

    static Logger logger = LoggerFactory.getLogger(WebUtils.class);

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

    private static String filterQueryParams(final String host, final String uriQuery) {
        if (StringUtils.isBlank(uriQuery))
            return null;

        // Parameter extrahieren
        List<QueryKV> queryParameters = new ArrayList<>();
        for(final String query : uriQuery.split("&")) {
            if (query.contains("=")) {
                String[] kv = query.split("=");
                queryParameters.add(new QueryKV(kv[0], kv[1]));
            } else {
                queryParameters.add(new QueryKV(query, ""));
            }
        }

        // Parameter filtern
        for (final QueryKV queryParam : queryParameters) {
            if (host.contains("humblebundle")) {
                if (queryParam.getKey().equalsIgnoreCase("partner")) {
                    queryParam.setFiltered(true);
                    continue;
                }
            }

            if (TrafficService.CAMPAIGN_KEYWORDS.contains(queryParam.getKey()))
                queryParam.setFiltered(true);

            if (TrafficService.CAMPAIGN_NAMES.contains(queryParam.getKey())) 
                queryParam.setFiltered(true);

        }

        // Querystring wieder zusammenstellen
        return queryParameters.stream().filter(p -> !p.isFiltered())
                                       .map(p -> p.toQuery())
                                       .collect(Collectors.joining("&"));
    }

    /**
     * Entfernt Google Analytics, Partner, Tracking- oder Session-IDs aus der URL
     * @param url
     * @return
     */
    public static String antispyUrl(final String url) {
        try {
            final URI uri = new URI(url);
            final String host = uri.getHost().toLowerCase();
        
            final String newQuery = filterQueryParams(host, uri.getQuery());
            
            return new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), newQuery, uri.getFragment()).toString();
        } catch (final URISyntaxException ue) {
            logger.debug("Malformed URL: {}", url);
            return "";
        }
    }
}
