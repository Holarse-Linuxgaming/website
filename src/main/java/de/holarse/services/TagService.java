package de.holarse.services;

import de.holarse.backend.db.Tag;
import de.holarse.backend.db.repositories.TagRepository;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
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
    
}
