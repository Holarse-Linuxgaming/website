package de.holarse.web.admin.imports;

import org.springframework.web.multipart.MultipartFile;

public class UploadCommand {
    
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "UploadCommand{" + "file=" + file + '}';
    }
    
}
