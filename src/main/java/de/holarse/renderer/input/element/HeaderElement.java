package de.holarse.renderer.input.element;

public class HeaderElement {

    private final static char SIGN = '=';

    public static boolean matches(final char cchar) {
        return SIGN == cchar;
    }

}
