package de.holarse.tools.holarseexport;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class ExportWriter {
 
    private final static XmlMapper mapper;
    
    static {
        final JacksonXmlModule module = new JacksonXmlModule();
        mapper = new XmlMapper(module);
        mapper.setDateFormat(Configuration.DATE_FORMAT);          
    }
    
    public static void writeXml(Object value, String type, Long uid) throws Exception {
            String xml = mapper.writeValueAsString(value);
            final StringBuffer buffer = new StringBuffer();
            buffer.append("/tmp/export/").append(type).append("-").append(uid).append(".xml");
            try (final BufferedWriter bw = new BufferedWriter(new FileWriter(buffer.toString()))) {
                bw.append(xml);
            }        
    }    
    
}
