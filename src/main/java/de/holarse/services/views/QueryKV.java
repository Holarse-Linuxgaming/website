package de.holarse.services.views;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Ein Parameter-Paar für URL-Query-Parameter
 */
public class QueryKV {

    private final String key;
    private final String value;
    private boolean filtered = false;

    public QueryKV(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public boolean isFiltered() {
        return filtered;
    }

    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

    public String toQuery() {
        try {
            return String.format("%s=%s", URLEncoder.encode(key, "UTF-8"), 
                                          URLEncoder.encode(value, "UTF-8"));
        } catch (UnsupportedEncodingException ue) {
            return "";
        }
    }
    

}