package de.holarse.tools.holarseexport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.binary.Base64OutputStream;

public class MediaExporter {
    
    private MediaExporter() {}

    public static String readAsBase64(final Path path) throws Exception {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream(1024 * 1024 * 1024); // 1 MB initial space

        final int buffer_size = Integer.parseInt(Configuration.get("buffer_size"));

        final byte[] buffer = new byte[buffer_size];
        int len = 0;
        
        try (final GZIPOutputStream gzos = new GZIPOutputStream(new Base64OutputStream(baos), buffer_size); 
             final FileInputStream fis = new FileInputStream(path.toFile())) {
                while ((len = fis.read(buffer)) != -1) {
                    gzos.write(buffer, 0, len);
                }
        } catch (IOException e) {
            throw(e);
        }
        
        return new String(baos.toByteArray());
    }
}
