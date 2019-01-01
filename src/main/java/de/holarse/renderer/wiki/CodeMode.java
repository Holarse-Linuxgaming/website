package de.holarse.renderer.wiki;

public class CodeMode implements Mode {

    private final StringBuilder buffer = new StringBuilder(30);    
    
    public static boolean isStartMarker(String sequence) {
        return "[CODE]".equalsIgnoreCase(sequence);
    }
        
    @Override
    public void handle(char ch) {
        buffer.append(ch);
    }

    @Override
    public StringBuilder render() {
        System.out.println("Before: " + buffer.toString());
        
        final StringBuilder n = new StringBuilder(30);    
        n.append( buffer.toString().replaceAll("\\[code\\]", "<pre>").replaceAll("\\[/code\\]", "</pre>") );
        
        System.out.println("After: " + n.toString());
        return n;
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isComplete() {
        return buffer.toString().toUpperCase().endsWith("[/CODE]");
    }
    
}
