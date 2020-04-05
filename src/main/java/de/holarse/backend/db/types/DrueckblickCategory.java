package de.holarse.backend.db.types;

/**
 * Kategorien für die Aufteilung des Drückblicks
 */
public enum DrueckblickCategory {

    // Für noch nicht organisierte Meldungen
    UNASSIGNED("Nicht sortiert"),

    ANNOUNCEMENTS("Ankündigungen"),
    UPDATES("Updates"),
    OPENSOURCE("Open Source"),
    DRIVERS("Treiber und Hardware"),
    COMMUNITY("Community"),
    STREAMING("Linux-Streamer"),
    HOLARSE("Holarse"),
    WINDOWS("Wine, Crossover, Proton"),
    SALES("Rabatte und Sales"),
    PICK_WEEK("Picks der Woche"),
    PROTON_WEEK("Proton-Spiel der Woche"),
    MENTIONS("Honorable Mentions");

    private final String label;

    private DrueckblickCategory(final String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}