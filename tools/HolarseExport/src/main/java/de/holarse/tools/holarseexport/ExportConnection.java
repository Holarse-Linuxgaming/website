package de.holarse.tools.holarseexport;

import java.sql.Connection;
import java.sql.DriverManager;

public class ExportConnection {
    
    public final static String URL = "jdbc:mysql://holarse-data/holarse?user=export&password=export";    
    
    public static Connection Connect() throws Exception {
        return DriverManager.getConnection(URL);
    }
    
}
