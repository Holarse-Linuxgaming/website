package de.holarse.web.search;

import javax.validation.constraints.Size;

public class SearchCommand {

    @Size(min = 3, max=30)
    private String term;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
    
}
