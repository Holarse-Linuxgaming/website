package de.holarse.web.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.holarse.web.controller.commands.ArticleForm;
import de.holarse.web.controller.commands.FileUploadForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileUploadService {

    private final static transient Logger logger = LoggerFactory.getLogger(FileUploadService.class);

    public List<FileUploadForm> readFileUpload(final ArticleForm form) throws JsonProcessingException {
        // Filepond String als Json extrahieren
        final List<FileUploadForm> result = new ArrayList<>();

        logger.debug("FILEUPLOAD: {}", form.getFilepond().size());
        //logger.debug("RAW: {}", String.join(";", form.getFilepond()));
//        final ObjectMapper jsonMapper = new ObjectMapper();
//        for (final String fileUpload: form.getFilepond()) {
//            logger.debug("RAW: {}", fileUpload);
//            final FileUploadForm screenshotUpload = jsonMapper.readValue(fileUpload, FileUploadForm.class);
//            logger.debug("FILEPOND: {}, {}", screenshotUpload.getName(), screenshotUpload.getData());
//            result.add(screenshotUpload);
//        }

        return form.getFilepond();
    }

}
