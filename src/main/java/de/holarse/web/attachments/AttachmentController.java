package de.holarse.web.attachments;

import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.repositories.AttachmentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Zuständig für die Schreibe und Lösch-Operationen bei Attachments von Artikeln, News und Kommentaren.
 * @author comrad
 */
@RestController
public class AttachmentController {
    
    @Autowired
    private AttachmentRepository ar; 
    
    @GetMapping(value = "/node/{nodeId}/attachments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Attachment>> index(@PathVariable("nodeId") final Long nodeId) {
        return new ResponseEntity<>(ar.findByNodeId(nodeId), HttpStatus.OK);
    }
    
    @GetMapping(value = "/node/{nodeId}/attachments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Attachment> show(@PathVariable("nodeId") final Long nodeId, @PathVariable("id") final Long id) {
        return new ResponseEntity<>(ar.findById(id).get(), HttpStatus.OK);
    }
    
}
