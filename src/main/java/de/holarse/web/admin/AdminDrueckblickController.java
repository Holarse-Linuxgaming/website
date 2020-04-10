package de.holarse.web.admin;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.holarse.backend.db.repositories.DrueckblickEntryRepository;
import de.holarse.backend.db.repositories.DrueckblickRepository;
import de.holarse.backend.views.admin.DrueckblickEntryAdminView;
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
    public String index(final ModelMap map) {
        return "admin/drueckblick/index";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DrueckblickEntryAdminView>> GetOpen() {
        var list = drueckblickEntryRepository.findAllNew().stream()
                                                          .map(viewFactory::fromDrueckblickEntry)
                                                          .collect(Collectors.toList());

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}