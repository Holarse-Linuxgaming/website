package de.holarse.factories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.holarse.backend.db.Drueckblick;
import de.holarse.backend.db.DrueckblickEntry;
import de.holarse.backend.views.admin.DrueckblickAdminView;
import de.holarse.backend.views.admin.DrueckblickEntryAdminView;
import de.holarse.services.WebUtils;

@Component
public class AdminViewFactory {

    Logger logger = LoggerFactory.getLogger(AdminViewFactory.class);

    public DrueckblickEntryAdminView fromDrueckblickEntry(final DrueckblickEntry entity) {
        DrueckblickEntryAdminView view = new DrueckblickEntryAdminView();

        view.setId(entity.getId());
        view.setBearer(entity.getBearer());
        view.setCategory(entity.getCategory());
        if (entity.getDrueckblick() != null) {
            view.setDrueckblick(fromDrueckblick(entity.getDrueckblick()));
        }
        view.setLink(entity.getLink());
        view.setMessage(entity.getMessage());
        view.setCreated(WebUtils.format(entity.getCreated()));
        view.setDeleted(entity.isDeleted());

        return view;
    }

    public DrueckblickAdminView fromDrueckblick(final Drueckblick entity) {
        DrueckblickAdminView view = new DrueckblickAdminView();

        view.setName(entity.getName());

        return view;
    }

}