package de.holarse.web.admin;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.holarse.backend.db.DrueckblickEntry;
import de.holarse.backend.db.repositories.DrueckblickEntryRepository;
import de.holarse.backend.db.repositories.DrueckblickRepository;
import de.holarse.backend.db.types.DrueckblickCategory;
import de.holarse.backend.views.SelectionViewModel;
import de.holarse.backend.views.admin.DrueckblickEntryAdminView;
import de.holarse.exceptions.HolarseException;
import de.holarse.factories.AdminViewFactory;

@Controller
@RequestMapping("/admin/drueckblick")
public class AdminDrueckblickController {

    @Autowired
    private DrueckblickRepository drueckblickRepository;

    @Autowired
    private DrueckblickEntryRepository drueckblickEntryRepository;

    @Autowired
    private AdminViewFactory viewFactory;

    @GetMapping
    public String index() {
        return "admin/drueckblick/index";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DrueckblickEntryAdminView>> GetOpen() {
        var list = drueckblickEntryRepository.findAllNew().stream()
                                                          .map(viewFactory::fromDrueckblickEntry)
                                                          .collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Nimmt ein Update eines Drückblick-Entries entgegen.
     * @param view
     * @return
     */
    @PostMapping(value="update_entry")
    public ResponseEntity<DrueckblickEntryAdminView> autoUpdateEntry(@ModelAttribute final DrueckblickEntryAdminView view) {
        final DrueckblickEntry entry = drueckblickEntryRepository.findById(view.getId()).orElseThrow(() -> new HolarseException("id not found"));
        entry.setBearer(view.getBearer());
        entry.setCategory(view.getCategory());
        entry.setLink(view.getLink());
        entry.setMessage(view.getMessage());
        entry.setUpdated(OffsetDateTime.now());

        drueckblickEntryRepository.save(entry);
        return new ResponseEntity<>(viewFactory.fromDrueckblickEntry(entry), HttpStatus.ACCEPTED);
    }

    /**
     * Nimmt ein Löschen eines Drückblick-Entries entgegen.
     * @param view
     * @return
     */
    @PostMapping(value="delete_entry")
    public ResponseEntity<DrueckblickEntryAdminView> deleteEntry(@ModelAttribute final DrueckblickEntryAdminView view) {
        final DrueckblickEntry entry = drueckblickEntryRepository.findById(view.getId()).orElseThrow(() -> new HolarseException("id not found"));
        entry.setDeleted(view.isDeleted());
        entry.setUpdated(OffsetDateTime.now());

        drueckblickEntryRepository.save(entry);
        return new ResponseEntity<>(viewFactory.fromDrueckblickEntry(entry), HttpStatus.ACCEPTED);
    } 
    
    @GetMapping(value="categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SelectionViewModel>> categories() {
        return new ResponseEntity<>(Arrays.asList(DrueckblickCategory.values())
                                    .stream()
                                    .map(e -> new SelectionViewModel(e.getLabel(), e.name()))
                                    .collect(Collectors.toList()), HttpStatus.OK);
    }

}