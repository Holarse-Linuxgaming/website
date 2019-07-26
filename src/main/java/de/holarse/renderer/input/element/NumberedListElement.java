package de.holarse.renderer.input.element;

public class NumberedListElement extends AbstractWikiElement {

    private final static char HASHTAG = '#';

    public static boolean matches(final char cchar) {
        return HASHTAG == cchar;
    }

}
