package de.holarse.backend.decorator;

import de.holarse.backend.repository.ArticleRepository;
import de.holarse.backend.xml.ArticleLoader;
import de.holarse.entity.importer.sub.Titletype;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleCacheBuilder implements CacheBuilder<de.holarse.entity.importer.Article, de.holarse.view.Article> {

    Logger log = LoggerFactory.getLogger(ArticleCacheBuilder.class);
    
    @Autowired
    private ArticleLoader al;
    
    @Autowired
    private ArticleRepository ar;

    @Override
    public void buildCache() {
        final List<de.holarse.view.Article> articles = al.getAll().stream().map(u -> migrate(u)).collect(Collectors.toList());
        ar.saveAll(articles);
    }

    @Override
    public de.holarse.view.Article migrate(final de.holarse.entity.importer.Article entity) {
        de.holarse.view.Article view = new de.holarse.view.Article();
        view.setUid(entity.getUid());
        view.setTitle(entity.getTitles().stream().filter(t -> Titletype.MAIN.equals(t.getTitleType())).collect(Collectors.toList()).get(0).getTitle());
        
        return view;
    }

}
