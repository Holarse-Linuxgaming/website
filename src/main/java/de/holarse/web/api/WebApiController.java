package de.holarse.web.api;

import de.holarse.backend.views.PageVisitResult;
import de.holarse.renderer.html.HtmlRenderer;
import de.holarse.services.TrafficService;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webapi")
public class WebApiController {

    Logger logger = LoggerFactory.getLogger(WebApiController.class);    
    
    @Autowired
    private HtmlRenderer wikiRenderer;
    
    @Autowired
    private TrafficService trafficService;
       
    @Secured("ROLE_USER")
    @PostMapping("/render/preview")
    public String renderPreview(String content) {
        return wikiRenderer.render(content);
    }

    @GetMapping(value = "/pagevisits", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PageVisitResult>> pageVisits(@RequestParam("nodeId") final Long nodeId, 
            @RequestParam("fromDate") @DateTimeFormat(pattern="yyyy-MM-dd") final Date fromDate, 
            @RequestParam(required = false, name = "untilDate") @DateTimeFormat(pattern="yyyy-MM-dd") final Date untilDate) {
        return new ResponseEntity<>(trafficService.getPageVisits(nodeId, fromDate, untilDate), HttpStatus.OK);
    }
}
