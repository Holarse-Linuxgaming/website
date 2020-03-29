package de.holarse.backend.miracle;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class Scope 
{
    @JsonProperty("scope-url")
    @JacksonXmlElementWrapper(localName = "scope-urls")        
    private List<ScopeUrl> scopeUrls = new ArrayList<>();

    public List<ScopeUrl> getScopeUrls() {
        return scopeUrls;
    }

    public void setScopeUrls(List<ScopeUrl> scopeUrls) {
        this.scopeUrls = scopeUrls;
    }



}

