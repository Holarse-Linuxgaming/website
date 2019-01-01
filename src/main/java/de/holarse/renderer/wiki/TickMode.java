package de.holarse.renderer.wiki;

public class TickMode implements Mode {

    private StringBuilder buffer = new StringBuilder(30);
    private int depth = 0;
    private int depthOut = 0;
    
    public static boolean isStartMarker(final char ch) {
        return ch == '\'';
    }

    @Override
    public void handle(final char ch) {
        if (isStartMarker(ch)) {
            if (buffer.length() == 0) {
                depth++;
            } else {
                depthOut++;
            }
            
            if (depth > 3) {
                depth = 3;
            }
        } else {
            buffer.append(ch);
        }
        
        System.out.println("handling: char: " + ch + ", depth: " + depth + ", buffer: " + buffer.toString() + " (" + buffer.length() + ")");
    }

    @Override
    public StringBuilder render() {
        System.out.println("Rendering: depth: " + depth + ", depthOut: " + depthOut + ", buffer: " + buffer.toString() + " (" + buffer.length() + ")");
        final StringBuilder n = new StringBuilder(35);
        switch (depth)
        {
            case 1:
                n.append("'").append(buffer.toString()).append("'"); break;
            case 2:
                n.append("<i>").append(buffer.toString()).append("</i>"); break;
            case 3:
                n.append("<b>").append(buffer.toString()).append("</b>"); break;                        
        }

        return n;        
    }
    
    @Override
    public boolean isComplete() {
        return depth == 0 && buffer.length() > 0;
    }
    
    // Eventuell um Speicher zu sparen
    @Override
    public void clear() {
        buffer = new StringBuilder(30);
        depth = 0;
    }
            

    
    
}
