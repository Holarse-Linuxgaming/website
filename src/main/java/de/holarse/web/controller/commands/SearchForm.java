package de.holarse.web.controller.commands;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

public class SearchForm {

    @NotBlank(message = "Suchfeld darf nicht leer sein")
    @Size(min = 3, max = 50, message = "Suche muss zwischen 3 und 50 Zeichen sein")
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
