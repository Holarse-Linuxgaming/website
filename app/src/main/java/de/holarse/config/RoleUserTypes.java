package de.holarse.config;

import java.util.List;

public class RoleUserTypes {

    public final static String ROLE_GUEST = "ROLE_GUEST"; // Anonymous user
    public final static String ROLE_USER = "ROLE_USER";
    public final static String ROLE_USER_REPORTER = "ROLE_REPORTER";
    public final static String ROLE_USER_MODERATOR = "ROLE_MODERATOR";
    public final static String ROLE_USER_CORE = "ROLE_HOLARSE_CORE";
    public final static String ROLE_USER_ADMIN = "ROLE_ADMIN";
 
    private final static List<String> privilegedRoles = List.of(ROLE_USER_ADMIN, ROLE_USER_CORE, ROLE_USER_MODERATOR, ROLE_USER_REPORTER);
    
    public static List<String> getPrivilegedRoles() {
        return privilegedRoles;
    }
    
}
