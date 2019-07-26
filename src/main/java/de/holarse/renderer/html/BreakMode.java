/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.renderer.html;

import de.holarse.renderer.Mode;

/**
 *
 * @author comrad
 */
public class BreakMode implements Mode {

    private char lastChar = 0;

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
