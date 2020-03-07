package de.holarse.backend.sitemap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import de.holarse.web.sitemap.W3cDateFormatter;
import java.io.Serializable;
import java.time.OffsetDateTime;

@JsonRootName(value = "url")
@JsonPropertyOrder({"loc", "lastmod", "changefreq", "priority"})
public class Url implements Serializable {

    @JsonProperty("loc")
    private final String location;

    @JsonProperty("lastmod")
    @JsonSerialize(using = W3cDateFormatter.class)
    @JsonInclude(Include.NON_NULL)
    private OffsetDateTime updated;

    @JsonProperty("changefreq")
    private final ChangeFrequencyType changeFrequency;

    @JsonProperty("priority")
    private final float priority;

    public Url(
            final String location,
            final OffsetDateTime updated,
            final ChangeFrequencyType changeFrequency) {
        this.location = location;
        this.updated = updated;
        this.changeFrequency = changeFrequency;
        this.priority = 0.5f;
    }

    public Url(
            final String location,
            final OffsetDateTime updated,
            final ChangeFrequencyType changeFrequency,
            float priority) {
        this.location = location;
        this.updated = updated;
        this.changeFrequency = changeFrequency;
        this.priority = priority;
    }

    public Url(final String location, final ChangeFrequencyType changeFrequency, float priority) {
        this.location = location;
        this.changeFrequency = changeFrequency;
        this.priority = priority;
    }

    public String getLocation() {
        return location;
    }

    public OffsetDateTime getUpdated() {
        return updated;
    }

    public ChangeFrequencyType getChangeFrequency() {
        return changeFrequency;
    }

    public float getPriority() {
        return priority;
    }
}
