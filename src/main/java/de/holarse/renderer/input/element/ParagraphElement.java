package de.holarse.renderer.input.element;

public class ParagraphElement extends AbstractWikiElement {
    public static boolean matches(final char cchar, final String source, final int i) {
        return cchar == '\n' && isStartMarker(getForwardChars(source, i, i+2));
    }

    protected static boolean isStartMarker(String chs) {
        return chs.equals("\n\n");
    }
}
