package de.holarse.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.springframework.stereotype.Component;

@Component
@Path("version")
public class Version {
    
    @GET
    public String getVersion() {
        return "1.0";
    }
    
}
