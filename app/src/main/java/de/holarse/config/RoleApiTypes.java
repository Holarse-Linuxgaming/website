package de.holarse.config;

import java.util.Arrays;
import java.util.List;

public final class RoleApiTypes {
    
    /**
     * Kann Drückblick eingeben
     */
    public static final String ROLE_API_DRÜCKBLICK = "ROLE_API_DRÜCKBLICK";
    /**
     * Kann Admin-Aufgaben eingeben
     */    
    public static final String ROLE_API_ADMIN = "ROLE_API_ADMIN";
    /**
     * Kann Versionsabfragen ausführen
     */
    public static final String ROLE_API_VERSION = "ROLE_API_VERSION";
    /**
     * Kann Importe eingeben
     */
    public static final String ROLE_API_IMPORT = "ROLE_API_IMPORT";
    
    /**
     * Generische kleinere Rolle für allgemeine API-Calls
     */
    public static final String ROLE_API = "ROLE_API";
    
    private static final List<String> types = Arrays.asList(ROLE_API_DRÜCKBLICK, ROLE_API_ADMIN, ROLE_API_VERSION, ROLE_API_IMPORT, ROLE_API);
    
    public static List<String> getTypes() {
        return types;
    }
}
