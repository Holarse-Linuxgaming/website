package de.holarse.backend.types;

/**
 * Eine AttachmentGroup ist eine Gruppe von Links mit möglichen verschiedenen Ausprägungen
 */
public enum AttachmentGroupType {

    /** Links zu Webseiten **/
    website,
    /** WineHQ, CrossOver, Proton, usw **/
    wine,
    /**
     * Steam, GOG, usw
     */
    shop,
    /**
     * Youtube-Videos
     */
    video,
    /**
     * Screenshots
     */
    image,
    /**
     * Dateidownloads
     */
    file,
    /**
     * Git-Repos
     */
    repo,
    /**
     * Interne Links
     */
    internal
}
