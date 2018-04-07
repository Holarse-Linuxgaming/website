package de.holarse.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.view.RedirectView;

public class RedirectException extends Exception {

    private final String redirectTo;
    
    public RedirectException(String redirectTo) {
        this.redirectTo = redirectTo;
    }
    
    public RedirectView getRedirect() {
        final RedirectView r = new RedirectView(redirectTo);
        r.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return r;
    }
    
}
