/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.views;

import java.util.List;
import java.util.stream.Collectors;
import web.entities.Article;

/**
 *
 * @author comrad
 */
public class ArticleView extends AbstractView<Article> {

    public ArticleView(final Article article) {
        super(article);
    }

    public String getMainTitle() {
        return getEntity().getMainTitle();
    }
    
    public List<String> getTags() {
        return getEntity().getTags().stream().map(t -> t.getName()).collect(Collectors.toList());
    }
    
    
}
