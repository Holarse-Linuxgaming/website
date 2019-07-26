package de.holarse.renderer.input.element;

public class BreakElement extends AbstractWikiElement {

    private final static String BREAK = "<!--break-->";

    public static boolean matches(final char cchar, final String source, final int i) {
        return (cchar == '<' && isStartMarker(getForwardChars(source, i, i+12)));
    }

    protected static boolean isStartMarker(final String sequence) {
        return BREAK.equalsIgnoreCase(sequence);
    }

}
