package de.holarse.backend.db.types;

/**
 * Ein Datentyp kann als Link oder als "binär"-Daten vorkommen.
 * @author comrad
 */
public enum AttachmentDataType {

    /**
     * Der Anhang zeigt auf eine Datenquelle (Internet)
     */
    URI,
    /**
     * Dateiname zum lokalen Storage
     */
    STORAGE,
    /**
     * Enthält einen zu interpretierenden Anteil an Daten. Beispielsweise
     * die Youtube-ID zu einem Youtube-Video oder die Kanal-ID zu einem YT-Kanal.
     */
    PARTIAL,
    /**
     * Der Anhang besteht inline aus den gespeicherten Daten.
     * Sollte eigentlich nicht genutzt werden.
     */
    BASE64;
    
}
