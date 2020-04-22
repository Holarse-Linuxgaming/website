package de.holarse.web.admin;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.holarse.backend.db.Drueckblick;
import de.holarse.backend.db.repositories.DrueckblickEntryRepository;
import de.holarse.backend.db.repositories.DrueckblickRepository;
import de.holarse.backend.views.PaginationView;
import de.holarse.backend.views.admin.DrueckblickAdminView;
import de.holarse.factories.AdminViewFactory;
import de.holarse.services.DateUtils;

@Controller
@RequestMapping("/admin/drueckblick/collections/")
public class AdminDrueckblickController {

    @Autowired
    private DrueckblickRepository drueckblickRepository;    

    @Autowired
    private DrueckblickEntryRepository drueckblickEntryRepository;    

    @Autowired
    private AdminViewFactory viewFactory;    

    @GetMapping
    public String index() {
        return "admin/drueckblick/collections/index";
    }    

    @GetMapping(value="index.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DrueckblickAdminView>> getList(@RequestParam(name= "page", defaultValue = "1") final int page, @RequestParam(name = "pageSize", defaultValue = "30") final int pageSize) {
        var pagination = new PaginationView("/wiki", page, drueckblickRepository.count(), pageSize);

        var result = drueckblickRepository.findAll(PageRequest.of(pagination.getPageRequestPage(), pagination.getPageSize(), Sort.by(Sort.Direction.DESC, "updated", "created")))
        .stream().map(viewFactory::fromDrueckblick).collect(Collectors.toList());

        return new ResponseEntity<List<DrueckblickAdminView>>(result, HttpStatus.OK);
    }

    /**
     * Stellt einen möglichen Drückblick-Eintrag her
     * @return
     */
    @GetMapping(value="propose.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DrueckblickAdminView> proposeNewDrueckblick() {
        final Drueckblick dbl = new Drueckblick();

        // Letzter Samstag
        dbl.setCoverageBegin(findLastSaturday(LocalDate.now())); 
        // Heute
        dbl.setCoverageEnd(LocalDate.now());

        // Namensvorschlag auf Basis der Jahres-Woche
        dbl.setName(OffsetDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-ww"))); 

        return new ResponseEntity<DrueckblickAdminView>(viewFactory.fromDrueckblick(dbl), HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping(value="join.json")
    @ResponseStatus(HttpStatus.CREATED)
    public void joinToDrueckblick(@ModelAttribute final DrueckblickAdminView view) {
        final Drueckblick dbl;
        if (view.getId() != null) {
            dbl = drueckblickRepository.findById(view.getId()).orElseThrow(() -> new EntityNotFoundException("Drückblick-ID nicht gefunden"));
        } else {
            dbl = new Drueckblick();
            dbl.setCreated(OffsetDateTime.now());
        }

        dbl.setName(view.getName());
        dbl.setTitle(view.getTitle());
        dbl.setCoverageBegin(DateUtils.parseIsoDate(view.getBegin()));
        dbl.setCoverageEnd(DateUtils.parseIsoDate(view.getEnd()));

        drueckblickRepository.save(dbl);                    

        // Alle noch offenen diesem Drückblick zuweisen
        drueckblickEntryRepository.assignOpenTo(dbl);
    }       

    /**
     * Findet den letzten Samstag. Unsere Drückblickberichte sind meist
     * von Samstag zu Samstag.
     * @param tp
     * @return
     */
    private LocalDate findLastSaturday(final LocalDate tp) {
        LocalDate _instance = tp;
        
        // Falls heute Samstag ist, wir wollen ja den letzten Samstag finden
        if (_instance.getDayOfWeek().equals(DayOfWeek.SATURDAY))
            return _instance.minusDays(7);

        // Solange suchen bis wieder Samstag ist.
        while(!_instance.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
            _instance = _instance.minusDays(1);
        }

        return _instance;
    }

}