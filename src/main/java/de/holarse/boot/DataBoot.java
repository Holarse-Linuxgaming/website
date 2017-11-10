/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.boot;

import de.holarse.backend.CommentLoader;
import de.holarse.backend.UserLoader;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataBoot {
    
    @Autowired UserLoader ul;
    @Autowired CommentLoader cl;
    
    @PostConstruct
    public void loadData() {
        ul.loadFromFileSystem();
        cl.loadFromFileSystem();
    }
    
}
