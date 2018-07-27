package de.holarse.backend.db;

public enum AttachmentType {

    /**
     * Link ins Internet
     */
    LINK,
    /**
     * Lokaler Link auf NodeId
     */
    LOCALLINK,
    /**
     * Bild, URI auf lokalen Pfad
     */
    IMAGE,
    /**
     * Generische Datei
     */
    FILE,
    /**
     * Youtube-Video
     */
    YOUTUBE,
    /**
     * Youtube-Kanal
     */
    YTCHANNEL,
    /**
     * Link auf Twitch-Kanal
     */
    TWITCH;
    
}
