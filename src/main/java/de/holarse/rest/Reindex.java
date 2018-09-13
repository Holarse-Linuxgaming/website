package de.holarse.rest;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.TagGroup;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.TagGroupRepository;
import de.holarse.search.SearchEngine;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reindex")
public class Reindex {
     Logger log = LoggerFactory.getLogger(Reindex.class);
     
     @Autowired
     SearchEngine searchEngine;
     
     @Autowired
     TagGroupRepository tagGroupRepository;
     
     @Autowired
     ArticleRepository articleRepository;
     
     @GetMapping
     public void reindexAll() throws Exception {
         reindexTags();
         reindexNodes();
     }
     
     @GetMapping("tags")
     @Transactional(readOnly = true)
     public void reindexTags() throws Exception {
         final Iterable<TagGroup> result = tagGroupRepository.findAll();
         result.forEach(tg -> Hibernate.initialize(tg.getTags()));
         
         searchEngine.updateTags(result);
     }
     
     @GetMapping("nodes")
     @Transactional(readOnly = true)     
     public void reindexNodes() throws Exception {
         final Iterable<Article> articles = articleRepository.findAll();
         articles.forEach(a -> Hibernate.initialize(a.getComments()));
         articles.forEach(a -> Hibernate.initialize(a.getTags()));
         
         searchEngine.update(articles);
     }     
}
