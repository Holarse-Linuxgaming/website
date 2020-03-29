package de.holarse.backend.sitemap;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ChangeFrequencyType {
    @JsonProperty("always")
    ALWAYS,
    @JsonProperty("hourly")
    HOURLY,
    @JsonProperty("daily")
    DAILY,
    @JsonProperty("weekly")
    WEEKLY,
    @JsonProperty("monthly")
    MONTHLY,
    @JsonProperty("yearly")
    YEARLY,
    @JsonProperty("never")
    NEVER
}
