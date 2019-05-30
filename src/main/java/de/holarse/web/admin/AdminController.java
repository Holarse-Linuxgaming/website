package de.holarse.web.admin;

import de.holarse.backend.db.repositories.PageVisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    PageVisitRepository pageVisitRepository;
    
    @GetMapping(value = "")
    public String index(final Model map) {
        map.addAttribute("mainPageVisits", pageVisitRepository.getMainResults());
        map.addAttribute("searches", pageVisitRepository.getSearches());
        return "admin/admin/index";
    }
    
}
