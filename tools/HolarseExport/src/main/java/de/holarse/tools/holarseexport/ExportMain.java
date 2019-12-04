package de.holarse.tools.holarseexport;

import java.sql.Connection;
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
        exports.add(new NewsExport());
        
        try (final Connection c = ExportConnection.Connect()) {
            for (Export e : exports) {
                e.export(c);
            }
        }
    }
    



    

    
            

}
