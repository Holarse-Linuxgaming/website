package de.holarse.api.imports;

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
    
    @Value("${holarse.web.version}")
    private String gitCommitId;
    
    @GetMapping(produces = "application/json")
    public de.holarse.backend.types.Version getVersion() {
        return new de.holarse.backend.types.Version(gitCommitId);
    }
    
}
