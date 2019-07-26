package de.holarse.renderer.input.element;

public class TickElement extends AbstractWikiElement {

    private final static char TICK = '\'';

    public static boolean matches(final char cchar) {
        return TICK == cchar;
    }
}
