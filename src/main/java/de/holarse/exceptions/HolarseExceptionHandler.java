package de.holarse.exceptions;

import de.holarse.services.TrafficService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HolarseExceptionHandler {

    @Autowired
    TrafficService trafficService;

    @ExceptionHandler(NodeNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String checkoutException(final NodeNotFoundException e, final HttpServletRequest httpServletRequest, final HttpServletResponse response) throws IOException {
        // TODO Response ist noch nicht 404, lässt sich auch nicht ändern
        response.setStatus(HttpStatus.NOT_FOUND.value());
        trafficService.saveRequest(httpServletRequest, response);
        return "errors/404";
    }

    @ExceptionHandler(NodeNotVisibleException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public String checkoutException(final NodeNotVisibleException e, final HttpServletRequest httpServletRequest, final HttpServletResponse response) throws IOException {
        // TODO Response ist noch nicht 403, lässt sich auch nicht ändern
        response.setStatus(HttpStatus.FORBIDDEN.value());
        trafficService.saveRequest(httpServletRequest, response);
        return "errors/403";
    }    
     
}
