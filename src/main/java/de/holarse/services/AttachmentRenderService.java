package de.holarse.services;

import de.holarse.backend.db.Attachment;
import de.holarse.web.register.RegisterService;
import de.holarse.web.renderer.attachments.AttachmentRenderer;
import de.holarse.web.renderer.attachments.engine.TemplateRenderEngine;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AttachmentRenderService {

    Logger log = LoggerFactory.getLogger(AttachmentRenderService.class);    
    
    @Autowired
    @Qualifier("LINK")
    private AttachmentRenderer linkAttachmentRenderer;
    
    @Autowired
    @Qualifier("YOUTUBE")
    private AttachmentRenderer youtubeAttachmentRenderer;
    
    public String render(final Attachment attachment) throws Exception {
        final AttachmentRenderer[] renderers = new AttachmentRenderer[] { linkAttachmentRenderer, youtubeAttachmentRenderer };
        
        for (final AttachmentRenderer ar : renderers) {
            if (ar.canRender(attachment)) {
                return ar.render(attachment);
            }
        }
        
        log.error("Kein Renderer für Attachment {} gefunden", attachment);
        
        return "";
    }
}
