package de.holarse.exceptions;

import de.holarse.services.TrafficService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class NodeNotFoundController {

    @Autowired
    TrafficService trafficService;
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NodeNotFoundException.class)
    public String nodeNotFoundError(final HttpServletRequest req, final HttpServletResponse response) {
        trafficService.saveRequest(req, response);
        return "404";
    }
    
}
