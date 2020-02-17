package de.holarse.backend.views.factories;

import de.holarse.backend.views.AttachmentView;
import de.holarse.backend.db.Attachment;

public class AttachmentViewFactory {

    public static AttachmentView create(final Attachment attachment) {
        final AttachmentView view = new AttachmentView();
        view.setData(attachment.getAttachmentData());
        view.setDescription(attachment.getDescription());

        view.setOrdering(attachment.getOrdering());

        view.setType(attachment.getAttachmentType());
        view.setGroup(attachment.getAttachmentGroup());        
        view.setDatatype(attachment.getAttachmentDataType());
        
        return view;
    }
    
    private AttachmentViewFactory() {}
    
}
