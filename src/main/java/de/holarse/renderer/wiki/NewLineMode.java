package de.holarse.renderer.wiki;

public class NewLineMode implements Mode {

    public static boolean isStartMarker(char ch) {
        return '\n' == ch;
    }
    
    @Override
    public void handle(char ch) {
        // Nix
    }

    @Override
    public StringBuilder render() {
        return new StringBuilder().append("<br />");
    }

    @Override
    public void clear() {
        // Nix
    }

    @Override
    public boolean isComplete() {
        return true;
    }
    
}
