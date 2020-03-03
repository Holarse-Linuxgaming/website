package de.holarse.backend.db.types;

/**
 * Eine AttachmentGroup ist die Kategorie zu der der Anhang zugehört. Regelt in welchem
 * Teil der Seitenansicht der Anhang zugehörig dargestellt wird.
 */
public enum AttachmentGroup {
    
    /**
     * Gruppe der Webseiten-Links
     */
    WEBSITE,
    /**
     * Wine-bezogene Links oder Informationen
     */
    WINE,
    /**
     * Onlineshop-Links
     */
    SHOP,
    /**
     * Videos
     */
    VIDEO,
    /**
     * Screenshots
     */
    IMAGE,
    /**
     * Medien-, Text oder Konfigurationsdateien
     */
    FILE,
    /**
     * Repository wie Flatpak, AppImage oder Snap
     */
    REPO
    
}
