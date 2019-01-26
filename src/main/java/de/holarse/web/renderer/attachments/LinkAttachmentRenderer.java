package de.holarse.web.renderer.attachments;

import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.types.AttachmentType;
import de.holarse.web.renderer.attachments.engine.TemplateRenderEngine;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("LINK")
public class LinkAttachmentRenderer implements AttachmentRenderer {

    @Autowired
    private TemplateRenderEngine renderEngine;
    
    @Override
    public String render(final Attachment attachment) throws Exception {
        final Map<String, Object> properties = new HashMap<>();
        properties.put("url", attachment.getAttachmentData());
        properties.put("description", attachment.getDescription());
        
        return renderEngine.execute("attachments/link.ftl", properties);
    }

    @Override
    public boolean canRender(final Attachment attachment) {
        return attachment.getAttachmentType().equals(AttachmentType.LINK);
    }
    
}
