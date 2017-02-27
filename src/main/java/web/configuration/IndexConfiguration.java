/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import web.entities.Article;
import web.entities.User;
import web.services.backend.index.EntityIndex;
import web.services.backend.index.Index;

@Configuration
public class IndexConfiguration {
    
    @Bean
    @Qualifier("articleIndex")
    public Index<Article> getArticleIndex() {
        return new EntityIndex<>();
    }
    
    @Bean
    @Qualifier("userIndex")
    public Index<User> getUserIndex() {
        return new EntityIndex<>();
    }
    
}
