package de.holarse.tools.holarseexport;

import java.sql.Connection;
import java.sql.DriverManager;

public class ExportConnection {
    
    public static Connection connect() throws Exception {
        return DriverManager.getConnection(Configuration.get("database_url"));
    }
    
}
