package de.holarse.tools.holarseexport;

import java.sql.Connection;
import java.sql.DriverManager;

public class ExportConnection {
    
    public final static String URL = "jdbc:mysql://localhost:13306/holarse?user=holarse&password=geheim";    
    
    public static Connection Connect() throws Exception {
        return DriverManager.getConnection(URL);
    }
    
}
