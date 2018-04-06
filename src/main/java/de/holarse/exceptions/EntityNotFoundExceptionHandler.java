package de.holarse.exceptions;

import de.holarse.services.TrafficService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EntityNotFoundExceptionHandler {

    @Autowired
    TrafficService trafficService;

    @ExceptionHandler(NodeNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String checkoutException(NodeNotFoundException e, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        // TODO Response ist noch nicht 404, lässt sich auch nicht ändern
        trafficService.saveRequest(httpServletRequest, response);
        return "errors/404";
    }
}
