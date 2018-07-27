package de.holarse.web.renderer.attachments;

import de.holarse.backend.db.Attachment;

public interface AttachmentRenderer {

    String render(Attachment attachment) throws Exception;
    boolean canRender(Attachment attachment);
    
}
