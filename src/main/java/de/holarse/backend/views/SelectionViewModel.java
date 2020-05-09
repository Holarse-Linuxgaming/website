package de.holarse.backend.views;

/**
 * Generisches ViewModel für Selection-Boxes
 */
public class SelectionViewModel implements View {

    private final String label;
    private final String value;

    public SelectionViewModel(final String label, final String value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public String getValue() {
        return value;
    }

}