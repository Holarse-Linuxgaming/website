/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.exceptions;

/**
 *
 * @author comrad
 */
public class HolarseException extends RuntimeException {

    public HolarseException(String string) {
        super(string);
    }

    public HolarseException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public HolarseException(Throwable thrwbl) {
        super(thrwbl);
    }

}
