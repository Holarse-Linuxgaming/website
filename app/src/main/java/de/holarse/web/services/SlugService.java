package de.holarse.web.services;

import de.holarse.backend.db.*;
import de.holarse.backend.db.repositories.NodeSlugRepository;
import de.holarse.backend.db.repositories.TagRepository;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.db.repositories.UserSlugRepository;
import de.holarse.backend.types.NodeType;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SlugService {
    
    private final static transient Logger log = LoggerFactory.getLogger(SlugService.class);
    
    private final static String SLUGWORD_DELIMITER = "_";    
    private final static int SLUGWORD_MAXSIZE = 95;
    //private final String[] removeWords = new String[]{"a","an","as","at","before","but","by","for","from","is","in","into","like","of","off","on","onto","per","since","than","the","this","that","to","up","via","with"};    
   
    
    @Autowired
    NodeSlugRepository nodeSlugRepository;
    
    @Autowired
    UserSlugRepository userSlugRepository;
    
    @Autowired
    TagRepository tagRepository;

    public NodeSlug slugify(final ArticleRevision articleRevision) {
        final String title = articleRevision.getTitle1();
        final String slugifiedTitle = slugify(title);
        final List<String> possibleSlugs = new ArrayList<>(101);
        possibleSlugs.add(slugifiedTitle);
        possibleSlugs.addAll(IntStream.rangeClosed(1, 100).boxed().map(n -> String.format("%s-%d", slugifiedTitle, n)).toList());
        
        for (final String possibleSlug : possibleSlugs) {
            final boolean result = nodeSlugRepository.existsByNameAndSlugContext(possibleSlug, NodeType.article);
            log.debug("User {} testing slug {} exists: {}", title, possibleSlug, result);
            if (!result) {
                final NodeSlug nodeSlug = new NodeSlug();
                nodeSlug.setName(possibleSlug);
                nodeSlug.setNodeId(articleRevision.getNodeId());
                nodeSlug.setSlugContext(NodeType.article);
                
                return nodeSlug;
            }
            log.debug("slug {} exists", title, possibleSlug);            
        }

        throw new IllegalStateException("ran out of article revision slug titles");
    }

    public NodeSlug slugify(final NewsRevision revision) {
        final String title = revision.getTitle();
        final String slugifiedTitle = slugify(title);
        final List<String> possibleSlugs = new ArrayList<>(101);
        possibleSlugs.add(slugifiedTitle);
        possibleSlugs.addAll(IntStream.rangeClosed(1, 100).boxed().map(n -> String.format("%s-%d", slugifiedTitle, n)).toList());

        for (final String possibleSlug : possibleSlugs) {
            final boolean result = nodeSlugRepository.existsByNameAndSlugContext(possibleSlug, NodeType.news);
            log.debug("User {} testing slug {} exists: {}", title, possibleSlug, result);
            if (!result) {
                final NodeSlug nodeSlug = new NodeSlug();
                nodeSlug.setName(possibleSlug);
                nodeSlug.setNodeId(revision.getNodeId());
                nodeSlug.setSlugContext(NodeType.news);

                return nodeSlug;
            }
            log.debug("slug {} exists", title, possibleSlug);
        }

        throw new IllegalStateException("ran out of news revision slug titles");
    }
   
    /**
     * Hinterlegt ein Slug für diesen Benutzer
     * @param user
     * @return 
     */
    public UserSlug slugify(final User user) {
        // Mögliche Liste aller Slugs herstellen
        final String slugifiedUsername = slugify(user.getLogin());
        final List<String> possibleSlugs = new ArrayList<>(101);
        possibleSlugs.add(slugifiedUsername);
        possibleSlugs.addAll(IntStream.rangeClosed(1, 100).boxed().map(n -> String.format("%s-%d", slugifiedUsername, n)).toList());
        
        log.debug("Possible slugs: {}", possibleSlugs);
        
        for (final String possibleSlug : possibleSlugs) {
            log.debug("User {} testing slug {}", user.getLogin(), possibleSlug);
            if (userSlugRepository.isSlugUsed(possibleSlug) == 0) {
                final UserSlug us = new UserSlug();
                us.setCreated(OffsetDateTime.now());
                us.setName(possibleSlug);
                
                return us;
            }
        }

        throw new IllegalStateException("ran out of user slug titles");
    }    
    
    /**
     * Hinterlegt ein Slug für den Tag
     * @param tag
     * @return 
     */
    public Tag slugify(final Tag tag) {
        if (StringUtils.isAllBlank(tag.getSlug())) {
            final String sluggedTag = slugify(tag.getName());

            final List<String> possibleSlugs = new ArrayList<>(101);
            possibleSlugs.add(sluggedTag);
            possibleSlugs.addAll(IntStream.rangeClosed(1, 100).boxed().map(n -> String.format("%s-%d", sluggedTag, n)).toList());
            
            for (final String possibleSlug : possibleSlugs) {
                log.debug("User {} testing slug {}", tag.getName(), possibleSlug);
                if (!tagRepository.existsBySlug(possibleSlug)) {
                    tag.setSlug(possibleSlug);
                    return tag;
                }
            } 
            throw new IllegalStateException("ran out of tag slugs");
        } else {
            return tag;
        }
    }

   
    public String transliterate(final String title) {
        return title.toLowerCase()
        .replaceAll(" of ", " ")
        .replaceAll("^of ", " ")
        .replaceAll(" the ", " ")
        .replaceAll("^the ", " ")
        .replaceAll(" to ", " ")
        .replaceAll("^to ", " ")
        .replaceAll(" in ", " ")
        .replaceAll("^in ", " ")        
        .replaceAll(" this ", " ")
        .replaceAll("^this ", " ")
        .replaceAll("€", "E");
    }

    public String slugify(final String title) {
        if (title == null) {
            return "";
        }
        
        // Ungewollte Wörter raus
        // Ungewollte Zeichen raus
        final String r2 = transliterate(title)
                            .replaceAll("_", " ")  
                            .replaceAll(",", " ")                                
                            .replaceAll("\\[", "")
                            .replaceAll("\\]", "")
                            .replaceAll("\\{", "")
                            .replaceAll("\\}", "")   
                            .replaceAll("/", "")             
                            .replaceAll("-", " ")
                            .replaceAll("\\.", "")
                            .replaceAll("&", "")                
                            .replaceAll("!", "")                               
                            .replaceAll(":", " ")
                            .replaceAll("'", "")
                            .replaceAll("\\+", "")
                            .replaceAll("#", "");

        // In Wörter aufsplitten
        final String[] words = r2.split(" ");

        // Wörter zusammenfügen, solange keine 95 Zeichen überschritten sind
        final StringBuffer buffer = new StringBuffer(SLUGWORD_MAXSIZE);
        for(final String word : words) {
            final String w = word.trim();
            //System.out.println("WORD='" + w + "', buffer: '" + buffer.toString() + "', len: " + buffer.length());
            if (StringUtils.isBlank(w)) {
                continue;
            }

            // bisheriger slug + "_" + neues wort                
            if ((buffer.length() + w.length() + 1) > 95) { 
                break;
            }

            // Nur am Anfang kein _ hinzufügen
            if (buffer.length() > 0) {
                buffer.append(SLUGWORD_DELIMITER);
            }

            buffer.append(word);
        }

        return buffer.toString();
    }      
    
}
