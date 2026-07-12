package de.holarse.tools.holarseexport;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;

public class ExportWriter {
 
    private final static XmlMapper mapper;
    
    static {
        final JacksonXmlModule module = new JacksonXmlModule();
        mapper = new XmlMapper(module);
        mapper.setDateFormat(new SimpleDateFormat(Configuration.get("xml_dateformat")));
    }
    
    public static void writeXml(final Object value, final String type, final Long uid) throws Exception {
            String xml = mapper.writeValueAsString(value);
            final StringBuffer buffer = new StringBuffer();
            buffer.append(Configuration.get("export_path")).append(type).append("-").append(uid).append(".xml");
            try (final BufferedWriter bw = new BufferedWriter(new FileWriter(buffer.toString()))) {
                bw.append(xml);
            }        
    }    
    
}
