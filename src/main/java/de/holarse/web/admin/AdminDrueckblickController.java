package de.holarse.web.admin;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.holarse.backend.db.repositories.DrueckblickEntryRepository;
import de.holarse.backend.db.repositories.DrueckblickRepository;
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
        var list = drueckblickEntryRepository.findAllNew().stream()
                                                          .map(viewFactory::fromDrueckblickEntry)
                                                          .collect(Collectors.toList());
        map.put("entries", list);

        return "admin/drueckblick/index";
    }

}