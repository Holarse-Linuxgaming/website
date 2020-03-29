package de.holarse.backend.miracle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

public class AgeDeclaration
{
    @JsonProperty("issuer")
    private Issuer issuer;

    @JsonProperty("scope")
    private Scope scope;

    @JsonProperty("rating")    
    private Rating rating;

    public Issuer getIssuer() {
        return issuer;
    }

    public void setIssuer(Issuer issuer) {
        this.issuer = issuer;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }
   
    
}