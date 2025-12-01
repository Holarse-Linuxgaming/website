package de.holarse.web.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.holarse.web.controller.commands.FileUploadForm;
import org.springframework.core.convert.converter.Converter;

public class StringToFilepondConverter implements Converter<String, FileUploadForm> {

    @Override
    public FileUploadForm convert(final String source) {
        final ObjectMapper jsonMapper = new ObjectMapper();
        try {
            return jsonMapper.readValue(source, FileUploadForm.class);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
