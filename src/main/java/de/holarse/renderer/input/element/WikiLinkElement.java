package de.holarse.renderer.input.element;

public class WikiLinkElement extends AbstractWikiElement {

    private final static char START_BRACKET = '[';

    public static boolean matches(final char cchar) {
        return START_BRACKET == cchar;
    }

}
