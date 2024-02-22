package de.holarse.backend.types;

public enum RenderCacheType {
    /** Einfach nur leer **/
    empty,
    /** Unverändert **/
    unchanged,
    /** Für den Browser **/
    web,
    /** Für die Suche ohne Syntax **/
    search,
    /** Für die Vorschau **/
    preview;
}
