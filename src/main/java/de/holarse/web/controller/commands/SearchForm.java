package de.holarse.web.controller.commands;

import jakarta.validation.constraints.Size;

public class SearchForm {

    @Size(min = 0, max = 50, message = "Suche muss zwischen 3 und 50 Zeichen sein")
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "SearchForm{" + "query=" + query + '}';
    }
    
}
