package de.holarse.renderer.input.element;

public class CodeElement extends AbstractWikiElement {

    private static final String CODE_1 = "[CODE]";
    private static final String CODE_2 = "<CODE>";

    public static boolean matches(final char cchar, final String source, final int i) {
        return cchar == '[' && isStartMarker(getForwardChars(source, i, i+6));
    }

    protected static boolean isStartMarker(final String sequence) {
        return CODE_1.equalsIgnoreCase(sequence) || CODE_2.equalsIgnoreCase(sequence);
    }

}
