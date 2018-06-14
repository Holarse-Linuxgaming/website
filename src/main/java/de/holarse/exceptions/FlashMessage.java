package de.holarse.exceptions;

public class FlashMessage {

    private Throwable throwable;
    private String title;
    private String message;
    private String solution;
    private ErrorMode mode;

    public Throwable getThrowable() {
        return throwable;
    }

    public String getCssMode() {
        return mode.toString().toLowerCase();
    }
    
    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public ErrorMode getMode() {
        return mode;
    }

    public void setMode(ErrorMode mode) {
        this.mode = mode;
    }

}
