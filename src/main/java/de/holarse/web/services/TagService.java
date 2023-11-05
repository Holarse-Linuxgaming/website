package de.holarse.web.services;

import de.holarse.backend.db.Tag;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.web.controller.commands.ArticleForm;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    
    @Autowired
    private TagRepository tagRepository;
    
    @Autowired
    private SlugService slugService;
    
    public Set<Tag> extract(final ArticleForm form) {
        final Set<Tag> articleTags = new HashSet<>();
        
        // Tags auslesen, in Entities umwandeln und ggf. erzeugen
        final String[] tagNames = form.getTags().split(",");
        for (final String tagName : tagNames) {
            final Tag tag = tagRepository.findByName(tagName.trim()).orElseGet(() -> new Tag(tagName.trim()));
            articleTags.add(tag);
        }
        // Alle Tags mit Slugs versehen, bestehende werden nicht angefasst
        articleTags.stream().forEach(t -> slugService.slugify(t));        
        
        return articleTags;
    }
    
}
