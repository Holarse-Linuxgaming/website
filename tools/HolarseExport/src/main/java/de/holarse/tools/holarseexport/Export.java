package de.holarse.tools.holarseexport;

import java.sql.Connection;

public interface Export {
    
    void export(Connection c) throws Exception;
    
}
