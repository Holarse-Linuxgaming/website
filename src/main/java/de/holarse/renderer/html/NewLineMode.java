package de.holarse.renderer.html;

import de.holarse.renderer.Mode;

public class NewLineMode implements Mode {

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
