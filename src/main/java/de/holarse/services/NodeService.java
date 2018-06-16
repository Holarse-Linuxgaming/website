package de.holarse.services;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.BranchableNode;
import de.holarse.backend.db.CommentableNode;
import de.holarse.backend.db.News;
import de.holarse.backend.db.Node;
import de.holarse.backend.db.NodeLock;
import de.holarse.backend.db.NodeType;
import de.holarse.backend.db.Revision;
import de.holarse.backend.db.RevisionableNode;
import de.holarse.backend.db.Slug;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.ArticleRepository;
import de.holarse.backend.db.repositories.NewsRepository;
import de.holarse.backend.db.repositories.NodeLockRepository;
import de.holarse.backend.db.repositories.RevisionRepository;
import de.holarse.backend.db.repositories.SlugRepository;
import de.holarse.exceptions.NodeLockException;
import de.holarse.exceptions.NodeNotFoundException;
import de.holarse.exceptions.RedirectException;

import java.time.OffsetDateTime;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NodeService {

    @Autowired
    NewsRepository newsRepository;
    
    @Autowired
    ArticleRepository articleRepository;
    
    @Autowired
    SlugRepository slugRepository;
    
    @Autowired
    NodeLockRepository lockRepository;
    
    @Autowired
    RevisionRepository revisionRepository;
    
    /**
     * Erzeugt die Grunddaten für eine neue Node
     * @param <N>
     * @param clazz
     * @return
     * @throws Exception
     */
    public <N extends CommentableNode> N initCommentableNode(Class<N> clazz) throws Exception {
        final N node = initNode(clazz);
        node.setCommentable(Boolean.TRUE);
       
        return node;
    }
    
    /**
     * Erzeugt die Grunddaten für eine neue Node
     * @param <N>
     * @param clazz
     * @return
     * @throws Exception
     */
    public <N extends Node> N initNode(Class<N> clazz) throws Exception {
        final N node = clazz.newInstance();
        node.setArchived(Boolean.FALSE);
        node.setDeleted(Boolean.FALSE);
        node.setDraft(Boolean.FALSE);
        node.setLocked(Boolean.FALSE);
        // Wahrscheinlich später auf false, wenn Vorschau Standard ist.
        node.setPublished(Boolean.TRUE);
        
        return node;
    }    
    
    /**
     * TODO Schöner umsetzen
     * @param ident
     * @return 
     * @throws de.holarse.exceptions.RedirectException 
     */
    public Optional<Article> findArticle(final String ident) throws RedirectException {
        // Ist der Ident eine Zahl, dann nach ID suchen
        if (NumberUtils.isDigits(ident)) {
            final Optional<Article> articleById = articleRepository.findById(Long.parseLong(ident));
            if (articleById.isPresent()) {
                throw new RedirectException(articleById.get().getUrl());
            }
        }
        
        // Anhand des Mainslugs (Hauptpfad) finden
        final Optional<Article> nodeByMainSlug = Optional.ofNullable(articleRepository.findBySlugAndBranch(ident, "master"));
        if (nodeByMainSlug.isPresent()) {
            return nodeByMainSlug;
        }
        
        // Anhand eines archivierten Slugs finden
        final Optional<Slug> slug = Optional.ofNullable(slugRepository.findBySlug(ident));
        if (slug.isPresent()) {
            final Optional<Article> articleByArchivedSlug = articleRepository.findById(slug.get().getNodeId());
            if (articleByArchivedSlug.isPresent()) {
                final Article article = articleByArchivedSlug.get();
                 if (StringUtils.isNotBlank(article.getSlug())) {
                    throw new RedirectException(articleByArchivedSlug.get().getUrl());
                 }
            }
        }
        
        throw new NodeNotFoundException(ident);
    }
    
    /**
     * TODO Schöner umsetzen
     * @param ident
     * @return 
     * @throws de.holarse.exceptions.RedirectException 
     */    
    public Optional<News> findNews(final String ident) throws RedirectException {
        // Ist der Ident eine Zahl, dann nach ID suchen
        if (NumberUtils.isDigits(ident)) {
            final Optional<News> newsById = newsRepository.findById(Long.parseLong(ident));
            if (newsById.isPresent()) {
                throw new RedirectException(newsById.get().getUrl());
            }
        }
        
        // Anhand des Mainslugs (Hauptpfad) finden
        final Optional<News> nodeByMainSlug = Optional.ofNullable(newsRepository.findBySlugAndBranch(ident, null));
        if (nodeByMainSlug.isPresent()) {
            return nodeByMainSlug;
        }
        
        // Anhand eines archivierten Slugs finden
        final Optional<Slug> slug = Optional.ofNullable(slugRepository.findBySlug(ident));
        if (slug.isPresent()) {
            final Optional<News> newsByArchivedSlug = newsRepository.findById(slug.get().getNodeId());
            if (newsByArchivedSlug.isPresent()) {
                final News news = newsByArchivedSlug.get();
                 if (StringUtils.isNotBlank(news.getSlug())) {
                    throw new RedirectException(newsByArchivedSlug.get().getUrl());
                 }
            }
        }
        
        throw new NodeNotFoundException(ident);
    }    
       
    public void archivateSlug(final String slugToArchivate, final Node node, final NodeType nodeType) {
        final Slug slug = new Slug();
        slug.setCreated(OffsetDateTime.now());
        slug.setNodeType(nodeType);
        slug.setNodeId(node.getId());
        slug.setSlug(slugToArchivate);
        
        slugRepository.save(slug);
    }
    
    public String findNextSlug(final String title, final NodeType nodeType) {
        final String slug = slugify(title);
        
        // Prüfen, ob der MainSlug frei ist.
        switch (nodeType) {
            case ARTICLE:
                if (articleRepository.countBySlugAndBranch(slug, "master") == 0) {
                    return slug;
                }
                break;
            case NEWS:
                if (newsRepository.countBySlugAndBranch(slug, "master") == 0) {
                    return slug;
                }
                break;
            default:
                throw new IllegalArgumentException("Unhandled nodetype");
        }
        
        // Main ist nicht mehr frei, nächsten freien suchen
        for (int i=2; i < 1000; i++) {
            final StringBuffer buffer = new StringBuffer();            
            buffer.append(slug).append("-").append(i);
            
            final String nextSlug = buffer.toString();
            
            if (slugRepository.countBySlug(nextSlug) == 0) {
                return nextSlug;
            }
        }
        
        throw new IllegalStateException("Kein möglicher freier Slug gefunden");
    }
    
    //private final String[] removeWords = new String[]{"a","an","as","at","before","but","by","for","from","is","in","into","like","of","off","on","onto","per","since","than","the","this","that","to","up","via","with"};
    
    public String slugify(final String title) {
        return title.toLowerCase()
                .replaceAll(" of ", " ")
                .replaceAll("^of ", " ")
                .replaceAll(" the ", " ")
                .replaceAll("^the ", " ")
                .replaceAll(" to ", " ")
                .replaceAll("^to ", " ")
                .replaceAll(" this ", " ")
                .replaceAll("^this ", " ")                
                .replaceAll("_", " ")  
                .trim()
                .replaceAll(" ", "_")
                .replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .replaceAll("\\{", "")
                .replaceAll("\\}", "")                
                .replaceAll("-", " ")
                .replaceAll("\\.", "")
                .replaceAll("&", "")                
                .replaceAll("!", "")                               
                .replaceAll(":", "")
                .replaceAll("'", "")
                .replaceAll("\\+", "")
                .trim()
                .replaceAll(" ", "_")                
                .replaceAll("_+", "_");
    }
    
    /**
     * Prüft, ob ein Lock für diese Node vorliegt
     * @param node
     * @param currentUser 
     */
    public void tryTolock(final Node node, final User currentUser) throws NodeLockException {
        final Optional<NodeLock> lock = lockRepository.findFirstByNodeIdAndLockUntilAfterAndUserNot(
                node.getId(), 
                OffsetDateTime.now(), 
                currentUser);
        
        // Es gibt schon ein Lock von einem anderen Benutzer, Abbruch
        if (lock.isPresent()) {
            throw new NodeLockException(lock.get());
        }
        
        lock(node, currentUser);
    }
    
    /**
     * Prüft, ob ein Lock für diese Node vorliegt
     * @param node
     * @param currentUser 
     */
    protected void lock(final Node node, final User currentUser) throws NodeLockException {
        // Lock setzen
        OffsetDateTime now = OffsetDateTime.now();
        
        final NodeLock newLock = new NodeLock();
        newLock.setNodeId(node.getId());
        newLock.setUser(currentUser);
        newLock.setCreated(now);
        newLock.setLockUntil(now.plusMinutes(120l)); // 2 Stunden Standardsperre
        
        lockRepository.save(newLock);
    }    
    
    public void unlock(final Node node) {
        lockRepository.deleteByNodeId(node.getId());
    }
    
    /**
     * Erzeugt eine Revision aus dem aktuellen Zustand der Node
     * @param node 
     */
    public void createRevisionFromCurrent(final BranchableNode node) {
        final Revision revision = new Revision();
        revision.setCreated(OffsetDateTime.now());
        revision.setNodeId(node.getId());
        // TODO durch die richtige XML-Ausgabe ersetzen
        revision.setContent(node.getContent());
        revision.setAuthor(node.getAuthor());
        revision.setChangelog(node.getChangelog());
        revision.setRevision(node.getRevision());
        revision.setBranch(node.getBranch());
        
        revisionRepository.saveAndFlush(revision);        
    }
   
}
