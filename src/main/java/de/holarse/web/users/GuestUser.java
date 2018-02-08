/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.web.users;

import de.holarse.backend.db.User;

/**
 *
 * @author comrad
 */
public class GuestUser extends User {
    
    @Override
    public boolean isGuest() { return true; }
    
}
