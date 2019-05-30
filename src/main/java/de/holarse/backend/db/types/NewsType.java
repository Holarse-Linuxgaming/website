package de.holarse.backend.db.types;

public enum NewsType {
 
    /**
     * Normale News
     */
    REPORT,
    /** 
     * Kurznews mit direkter Weiterleitung
     */
    SHORT,
    /**
     * News bestehend aus einem Youtube-Video
     */
    VIDEO,
    /**
     * News über eine neue Version
     */
    VERSION
}
