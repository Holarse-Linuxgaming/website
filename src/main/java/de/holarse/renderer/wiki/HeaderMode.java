package de.holarse.renderer.wiki;


public class HeaderMode implements Mode {

    private StringBuilder buffer = new StringBuilder(30);
    private int depth = 0;
    private int depthOut = 0;    
    
    public static boolean isStartMarker(final char ch) {
        return ch == '=';
    }    
    
    @Override
    public void handle(final char ch) {
        if (isStartMarker(ch)) {
            if (buffer.length() == 0) {
                depth++;
            } else {
                depthOut++;
            }
            
            if (depth > 6) {
                depth = 6;
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
        n.append("<h").append(depth).append(">").append(buffer.toString().trim()).append("</h").append(depth).append(">");
        return n;        
    }
    
    @Override
    public boolean isComplete() {
        return depth == depthOut && buffer.length() > 0;
    }
    
    // Eventuell um Speicher zu sparen
    @Override
    public void clear() {
        buffer = new StringBuilder(30);
        depth = 0;
    }
}
