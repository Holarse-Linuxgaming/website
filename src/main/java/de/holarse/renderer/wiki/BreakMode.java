/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.renderer.wiki;

/**
 *
 * @author comrad
 */
public class BreakMode implements Mode {

    private char lastChar = 0;
    
    public static boolean isStartMarker(String sequence) {
        return "<!--break-->".equalsIgnoreCase(sequence);
    }    
    
    @Override
    public void handle(char ch) {
        lastChar = ch;
    }

    @Override
    public StringBuilder render() {
        return new StringBuilder("<br />");
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean isComplete() {
        return lastChar == '>';
    }
    
}
