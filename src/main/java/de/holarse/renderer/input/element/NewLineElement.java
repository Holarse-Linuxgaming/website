package de.holarse.renderer.input.element;

public class NewLineElement {

    private final static char NEWLINE = '\n';

    public static boolean matches(final char cchar) {
        return NEWLINE == cchar;
    }
}
