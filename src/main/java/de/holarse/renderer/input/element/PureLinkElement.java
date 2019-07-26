package de.holarse.renderer.input.element;

public class PureLinkElement extends AbstractWikiElement {

    final static String MARKER = "HTTP";

    public static boolean matches(final char cchar, final String source, final int i) {
        return (cchar == 'h' || cchar == 'H') && isStartMarker(getForwardChars(source, i, i + 4));
    }

    protected static boolean isStartMarker(String text) {
        return text.toUpperCase().startsWith(MARKER);
    }

}
