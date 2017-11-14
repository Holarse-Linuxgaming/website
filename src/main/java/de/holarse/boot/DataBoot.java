/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.boot;

import de.holarse.backend.cache.UserCacheBuilder;
import de.holarse.backend.xml.ArticleLoader;
import de.holarse.backend.xml.CommentLoader;
import de.holarse.backend.xml.NewsLoader;
import de.holarse.backend.xml.UserLoader;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataBoot {
    
    @Autowired UserLoader ul;
    @Autowired CommentLoader cl;
    @Autowired ArticleLoader al;
    @Autowired NewsLoader nl;
    
    @Autowired UserCacheBuilder ucb;
    
    @PostConstruct
    public void loadData() {
        ul.loadFromFileSystem();
        al.loadFromFileSystem();
        nl.loadFromFileSystem();
        cl.loadFromFileSystem();
        
        ucb.buildCache();
    }
    
}
