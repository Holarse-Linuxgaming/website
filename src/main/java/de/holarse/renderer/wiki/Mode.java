package de.holarse.renderer.wiki;

public interface Mode {

    public void handle(char ch);
    public StringBuilder render();
    public void clear();
    
    public boolean isComplete();
}
