package de.holarse.backend.view;

import de.holarse.backend.db.Attachment;
import org.springframework.stereotype.Component;

public class ScreenshotView extends AttachmentView {

    public static ScreenshotView of(final Attachment attachment) {
        final ScreenshotView view = new ScreenshotView();
        view.setId(attachment.getId());
        view.setData(attachment.getData());
        view.setDescription(attachment.getDescription());
        view.setWeight(attachment.getWeight());

        return view;
    }
}
