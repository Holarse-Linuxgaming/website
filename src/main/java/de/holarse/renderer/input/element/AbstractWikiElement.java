package de.holarse.renderer.input.element;

public abstract class AbstractWikiElement {
    protected static String getForwardChars(final String text, final int start, final int end) {
        try {
            return text.substring(start, end);
        } catch (ArrayIndexOutOfBoundsException aioobe) {
            return "";
        }
    }
}
