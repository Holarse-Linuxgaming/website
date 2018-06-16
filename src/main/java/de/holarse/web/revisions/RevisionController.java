package de.holarse.web.revisions;

import de.holarse.backend.db.repositories.RevisionRepository;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RevisionController {
    
    Logger logger = LoggerFactory.getLogger(RevisionController.class);
    
    @Autowired
    RevisionRepository revisionRepository;
    
    @GetMapping("/node/{nodeId}/revisions")
    @Transactional
    public String revisionIndexArticles(@PathVariable("nodeId") final Long nodeId, final Model map) {
        return lookupRevisions(nodeId, map);
    }
    
    protected String lookupRevisions(final Long nodeId, final Model map) {
        logger.debug("Looking up NodeID " + nodeId);
        map.addAttribute("revisions", revisionRepository.findByNodeId(nodeId));
        map.addAttribute("nodeId", nodeId);
        
        return "revisions/index";        
    }
    
}
