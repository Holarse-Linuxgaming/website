package de.holarse.services;

import de.holarse.backend.db.repositories.TagRepository;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired TagRepository tagRepository;
    
    public String createTagList(Collection<String> tagNames) {
        return tagNames.stream().collect(Collectors.joining(","));     
    }

    /**
     * Das Mergen der Tags muss berücksichtigen, dass
     * a) Tags nicht mehrfach vorkommen dürfen
     * b) Tags nicht als Tag vorliegen könnten
     * c) Tags in ihre Alternative aufgelöst werden müssen
     * 
     * Kommt der Tag bereits in der Liste vor, wird er entfernt.
     * @param tags
     * @param tagsToMerge
     * @return 
     */
    public Set<String> mergeToggle(Collection<String> tags, Collection<String> tagsToMerge) {
        tags.removeIf(tagsToMerge::contains);
        return merge(tags, tagsToMerge);
    }
    
    /**
     * Das Mergen der Tags muss berücksichtigen, dass
     * a) Tags nicht mehrfach vorkommen dürfen
     * b) Tags nicht als Tag vorliegen könnten
     * c) Tags in ihre Alternative aufgelöst werden müssen
     * @param tags
     * @param tagsToMerge
     * @return 
     */
    public Set<String> merge(Collection<String> tags, Collection<String> tagsToMerge) {
        return tagsToMerge.stream()
                // tagsToMerge in Tags umwandeln, dabei fallen schonmal nicht existierende Tags weg.
                .map(t -> tagRepository.findByNameIgnoreCase(t)).filter(Optional::isPresent).map(Optional::get)
                // Tags ggf in ihre Aliase umwandeln
                .map(t -> Optional.ofNullable(t.getAlias()).orElse(t))
                // In ihre Namen umwandeln
                .map(t -> t.getName())
                // Zurück zum Set
                .collect(Collectors.toSet());
    }
    
    public Set<de.holarse.backend.db.Tag> commandToTags(final String tags) {
        if (StringUtils.isBlank(tags)) {
            return new HashSet<>();
        }
        return Arrays.asList(tags.split(",")).stream().map(createOrUpdateTag).collect(Collectors.toSet());
    }
    
    public Set<de.holarse.backend.db.Tag> listToTags(final List<de.holarse.backend.export.Tag> tags) {
        if (tags == null || tags.isEmpty()) {
            return new HashSet<>();
        }
        return tags.stream().map(t -> t.getValue()).map(createOrUpdateTag).collect(Collectors.toSet());
    }

    protected Function<String, de.holarse.backend.db.Tag> createOrUpdateTag = s -> tagRepository.findByNameIgnoreCase(s.trim()).orElse(new de.holarse.backend.db.Tag(s));    
    
}
