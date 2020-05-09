package de.holarse.factories;

import java.util.stream.Collectors;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import de.holarse.backend.db.News;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.Article;
import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.Comment;
import de.holarse.backend.db.Node;
import de.holarse.backend.db.User;
import de.holarse.backend.db.types.AttachmentGroup;
import de.holarse.backend.views.ArticleView;
import de.holarse.backend.views.AttachmentView;
import de.holarse.backend.views.CommentView;
import de.holarse.backend.views.ContentView;
import de.holarse.backend.views.NewsView;
import de.holarse.backend.views.SearchAutocompleteView;
import de.holarse.backend.views.TagView;
import de.holarse.backend.views.UserView;
import de.holarse.renderer.Renderer;
import de.holarse.search.SearchResult;
import de.holarse.services.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ViewFactory
{
    Logger logger = LoggerFactory.getLogger(ViewFactory.class);

    @Qualifier("htmlRenderer")
    @Autowired
    Renderer formattedRenderer;      
    
    /**
     * Erzeugt eine View-Ansicht eines Artikel
     * @param article
     * @return
     */
    public ArticleView fromArticle(final Article article) {
        final ArticleView view = new ArticleView();
                
        // Attachments umwandeln
        final Map<AttachmentGroup, List<AttachmentView>> attachmentGroups = article.getAttachments()
                .stream()
                .filter(a -> StringUtils.isNotBlank(a.getAttachmentData()))
                .map(this::fromAttachment)
                .collect(Collectors.groupingBy(a -> a.getGroup()));
        
        view.getAttachments().putAll(attachmentGroups);

        // Tags umwandeln
        view.getTags().addAll(article.getTags().stream().map(this::fromTag).collect(Collectors.toList()));        
        
        logger.debug("Content: {}", article.getContent());

        view.setNodeId(article.getNodeId());
        view.setMainTitle(article.getTitle());
        view.setAlternativeTitle1(article.getAlternativeTitle1());
        view.setAlternativeTitle2(article.getAlternativeTitle2());
        view.setAlternativeTitle3(article.getAlternativeTitle3());
        view.setSlug(article.getSlug());
        
        view.setCreated(DateUtils.format(article.getCreated()));
        view.setUpdated(DateUtils.format(article.getUpdated()));        
        
        // URL definieren
        view.setUrl(String.format("/wiki/%s", article.getSlug()));
        view.setEditUrl(String.format("/wiki/%s/edit", article.getNodeId()));
        
        renderNodeInto(article, view);  
        
        return view;
    }    

    /**
     * Erzeugt eine cachbare View-Ansicht einer News
     * @param news
     * @return
     */
    public NewsView fromNews(final News news) {
        NewsView view = new NewsView();
        
        final Map<AttachmentGroup, List<AttachmentView>> attachmentGroups = news.getAttachments()
                .stream()
                .filter(a -> StringUtils.isNotBlank(a.getAttachmentData()))
                .map(this::fromAttachment)
                .collect(Collectors.groupingBy(a -> a.getGroup()));

        view.getAttachments().putAll(attachmentGroups);
        
        logger.debug("Content: {}", news.getContent());

        view.setNodeId(news.getNodeId());
        view.setTitle(news.getTitle());
        view.setSubtitle(news.getSubtitle());
        view.setSlug(news.getSlug());
        view.setCreated(DateUtils.format(news.getCreated()));
        view.setUpdated(DateUtils.format(news.getUpdated()));
        
        // URL definieren
        view.setUrl(String.format("/news/%s", news.getSlug()));
        view.setEditUrl(String.format("/news/%s/edit", news.getNodeId()));        
        
        renderNodeInto(news, view);      
        
        return view;
    } 
    
    public AttachmentView fromAttachment(final Attachment attachment) {
        final AttachmentView view = new AttachmentView();
        view.setNodeId(attachment.getNodeId());
        view.setData(attachment.getAttachmentData());
        view.setDescription(attachment.getDescription());

        view.setOrdering(attachment.getOrdering());

        view.setType(attachment.getAttachmentType());
        view.setGroup(attachment.getAttachmentGroup());        
        view.setDatatype(attachment.getAttachmentDataType());
        
        return view;
    }

    public TagView fromTag(final Tag tag) {
        final TagView view = new TagView(tag.getName(), tag.getName(), tag.getUseCount());
        
        // URL definieren
        view.setUrl(String.format("/finder?tags=%s&i=1", tag.getName()));
        
        return view;
    }
    
    public UserView fromUser(final User user) {
        final UserView view = new UserView();
        view.setId(user.getId());
        view.setEmail(user.getEmail());
        view.setLogin(user.getLogin());
        view.setSlug(user.getSlug());
        view.setSignature(user.getSignature());
        
        view.getRoles().addAll( user.getRoles().stream().map(r -> r.getCode()).collect(Collectors.toList()) );
        
        // URL definieren
        view.setUrl(String.format("/users/%s", user.getSlug()));
        view.setEditUrl(String.format("/users/%s/edit", user.getSlug()));         
        
        return view;
    }
    
    public CommentView fromComment(final Comment comment) {
        final CommentView view = new CommentView();
        view.setId(comment.getId());
        
        view.setAuthor(fromUser(comment.getAuthor()));
        renderNodeInto(comment, view);
        
        view.setCreated(DateUtils.format(comment.getCreated()));
        view.setUpdated(DateUtils.format(comment.getUpdated()));        
        
        return view;
    }
    
    public ContentView renderNodeInto(final Node node, final ContentView view) {
        // Rohen Content setzen
        view.setContent(node.getContent());

        // Formatierten HTML-Content setzen
        try {
            view.setFormattedContent( formattedRenderer.render(node.getContent() ));
        } catch (Exception e) {
            logger.warn("Fehler beim Formatted Rendering in Node Id=" + node.getId(), e);
            view.setFormattedContent("");
        } 
        
        // Plain-Content setzen
        // TODO
        
        // Teaser
        view.setTeaser(StringUtils.abbreviate(view.getContent(), 200));
        
        return view;
    }

    public SearchAutocompleteView fromSearchResult(final SearchResult node) {
        SearchAutocompleteView view = new SearchAutocompleteView();

        view.setTeaser(node.getContent());
        view.setTitle(node.getTitle());
        view.setUrl(node.getUrl());
        view.setCategory("Artikel");
        
        return view;
    }    
}