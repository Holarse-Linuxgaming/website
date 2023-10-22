package de.holarse.api.imports;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import static de.holarse.config.RoleApiTypes.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/version")
@Secured({ROLE_API, ROLE_API_VERSION, ROLE_API_ADMIN})
public class Version {
    
    @Value("${git.commit.id.abbrev}")
    private String gitCommitId;
    
    @GetMapping(produces = "application/json")
    public String getVersion() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode version = mapper.createObjectNode();
        version.put("git-commit-id", gitCommitId);
        
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(version);
    }
    
}
