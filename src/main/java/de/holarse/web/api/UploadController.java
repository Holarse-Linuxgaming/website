package de.holarse.web.api;

import java.time.OffsetDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.types.AttachmentType;
import de.holarse.backend.views.AttachmentView;
import de.holarse.exceptions.HolarseException;
import de.holarse.services.WebUtils;
import de.holarse.services.YoutubeService;

@Controller
public class UploadController {

    Logger log = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    YoutubeService youtubeService;

    @Secured("ROLE_USER")
    @PostMapping("/webapi/upload")
    public void upload(final AttachmentView view) {
        final Attachment attachment = new Attachment();
        attachment.setNodeId(view.getNodeId());
        attachment.setOrdering(view.getOrdering());
        attachment.setCreated(OffsetDateTime.now());
        attachment.setDescription(view.getDescription());

        // Wo soll es später angezeigt werden?
        attachment.setAttachmentGroup(AttachmentType.lookupGroup(view.getType()));
        // Was ist das Attachment semantisch?
        attachment.setAttachmentType(view.getType());

        final String handledData;

        // Vorbehandeln je nach Datatype
        attachment.setAttachmentDataType(AttachmentType.lookupDatatype(view.getType()));
        switch (attachment.getAttachmentDataType()) {
            case URI:
                handledData = WebUtils.antispyUrl(view.getData());
                break;
            case PARTIAL:
            case BASE64:
                handledData = view.getData();
                break;
            case STORAGE:
                /** 
                 * Der Upload kommt als BASE64 an. Wir müssen es entpacken, ein Hash darüber
                 * bilden und es dann im Storage ablegen (anderer Service)
                 */
                handledData = "hello.jpg";
                break;
            default:
                throw new HolarseException("Behandlung des Attachment-Datentyp " + attachment.getAttachmentDataType() + " noch nicht implementiert.");
        }

        attachment.setAttachmentData(view.getData());
    }

}