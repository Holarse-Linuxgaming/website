package de.holarse.web.revisions;

import de.holarse.backend.db.repositories.RevisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RevisionController {
    
    @Autowired
    RevisionRepository revisionRepository;
    
    @RequestMapping("/wiki/{nodeId}/revisions")
    public String revisionIndex(final Long nodeId, final Model map) {
        map.addAttribute("revisions", revisionRepository.findByNodeId(nodeId));
        
        return "revisions/index";
    }
    
}
