package de.holarse.backend.sitemap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

@JsonRootName(value = "urlset", namespace = "http://www.sitemaps.org/schemas/sitemap/0.9")
public class UrlSet {

    @JsonProperty(value = "url")
    @JacksonXmlElementWrapper(useWrapping = false)
    private final List<Url> urls = new ArrayList<Url>(1024);

    public List<Url> getUrls() {
        return urls;
    }
}
