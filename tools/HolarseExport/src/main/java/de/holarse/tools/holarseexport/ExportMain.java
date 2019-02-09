package de.holarse.tools.holarseexport;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.holarse.backend.export.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ExportMain {

    final static Logger log = Logger.getLogger(ExportMain.class.getName());    

    public static void main(String[] args) throws Exception {
        log.setLevel(Level.WARNING);
        
        ExportMain e = new ExportMain();
    }

    public ExportMain() throws Exception {
        // Finally export
        final List<Export> exports = new ArrayList<>();
        exports.add(new UserExport());
        exports.add(new ArticleExport());
        
        try (final Connection c = ExportConnection.Connect()) {
            for (Export e : exports) {
                e.export(c);
            }
        }
    }
    



    

    
            

}
