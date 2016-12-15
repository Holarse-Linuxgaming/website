package web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Entity Not Found") 
public class EntityNotFoundException extends Exception {

    /**
     * Creates a new instance of <code>EntityNotFound</code> without detail
     * message.
     */
    public EntityNotFoundException() {
        super();
    }

    /**
     * Constructs an instance of <code>EntityNotFound</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public EntityNotFoundException(String msg) {
        super(msg);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotFoundException(Throwable cause) {
        super(cause);
    }
    
    
}
