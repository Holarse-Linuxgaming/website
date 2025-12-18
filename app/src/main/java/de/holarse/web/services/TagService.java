package de.holarse.web.services;

import de.holarse.backend.api.Article;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.repositories.TagGroupRepository;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.web.controller.commands.ArticleForm;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {
    
    @Autowired
    private TagRepository tagRepository;
    
    @Autowired
    private TagGroupRepository tagGroupRepository;
    
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
    
    public Set<Tag> extract(final Article importArticle) {
        final Set<Tag> articleTags = new HashSet<>();
        
        // Tags auslesen, in Entities umwandeln und ggf. erzeugen
        final List<String> tagNames = importArticle.getTags().stream().map(de.holarse.backend.api.Tag::getValue).collect(Collectors.toList());
        for (final String tagName : tagNames) {
            final Tag tag = tagRepository.findByName(tagName.trim()).orElseGet(() -> new Tag(tagName.trim()));
            // Bei Neuanlage müssen wir noch die Taggruppe setzen
            if (tag.getId() == null) {
                tag.setTagGroup(tagGroupRepository.findByCode("OTHER"));
            }
            articleTags.add(tag);
        }
        // Alle Tags mit Slugs versehen, bestehende werden nicht angefasst
        articleTags.stream().forEach(t -> slugService.slugify(t));        
        
        return articleTags;
        
    }
    
}
