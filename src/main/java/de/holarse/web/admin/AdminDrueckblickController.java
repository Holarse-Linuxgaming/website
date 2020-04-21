package de.holarse.web.admin;

import java.time.DayOfWeek;
import java.time.OffsetDateTime;
import java.time.temporal.TemporalField;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import de.holarse.backend.db.Drueckblick;
import de.holarse.backend.db.repositories.DrueckblickEntryRepository;
import de.holarse.backend.db.repositories.DrueckblickRepository;
import de.holarse.backend.views.admin.DrueckblickAdminView;
import de.holarse.factories.AdminViewFactory;

@Controller
@RequestMapping("/admin/drueckblick/")
public class AdminDrueckblickController {

    @Autowired
    private DrueckblickRepository drueckblickRepository;    

    @Autowired
    private DrueckblickEntryRepository drueckblickEntryRepository;    

    @Autowired
    private AdminViewFactory viewFactory;    

    @GetMapping
    public String index() {
        return "admin/drueckblick/main/index";
    }    

    @GetMapping(value="propose", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DrueckblickAdminView> proposeNewDrueckblick() {
        final Drueckblick dbl = new Drueckblick();

        // Letzter Samstag
        dbl.setCoverageBegin(findLastSaturday(OffsetDateTime.now())); 
        // Heute
        dbl.setCoverageEnd(OffsetDateTime.now());

        // Sollte wohl am besten aus einer Sequence kommen
        dbl.setName(""); 

        return new ResponseEntity<DrueckblickAdminView>(viewFactory.fromDrueckblick(dbl), HttpStatus.CREATED);
    }

    @PostMapping(value="join_to_drueckblick")
    @ResponseStatus(HttpStatus.CREATED)
    public void joinToDrueckblick(@ModelAttribute final DrueckblickAdminView view) {
        final Drueckblick dbl = drueckblickRepository.findById(view.getId()).orElseThrow(() -> new EntityNotFoundException("Drückblick-ID nicht gefunden"));

        // Alle noch offenen diesem Drückblick zuweisen
        drueckblickEntryRepository.assignOpenTo(dbl);
    }       

    /**
     * Findet den letzten Samstag. Unsere Drückblickberichte sind meist
     * von Samstag zu Samstag.
     * @param tp
     * @return
     */
    private OffsetDateTime findLastSaturday(final OffsetDateTime tp) {
        OffsetDateTime _instance = tp;
        
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