package de.holarse.backend.views;

public class TagView {

    private final String label;
    private final String name;
    private final long useCount;

    public TagView(String label, String name, long useCount) {
        this.label = label;
        this.name = name;
        this.useCount = useCount;
    }
    
    public String getLabel() {
        return label;
    }

    public String getName() {
        return name;
    }

    public long getUseCount() {
        return useCount;
    }
   
}
