package de.holarse.renderer.input.element;

public class UnorderedListElement extends AbstractWikiElement {

    private final static char ASTERISK = '*';

    public static boolean matches(final char cchar) {
        return ASTERISK == cchar;
    }
}
