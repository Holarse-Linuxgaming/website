package de.holarse.factories;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import de.holarse.backend.db.News;
import de.holarse.backend.db.Article;
import de.holarse.backend.db.Attachment;
import de.holarse.backend.db.types.AttachmentGroup;
import de.holarse.backend.views.ArticleView;
import de.holarse.backend.views.NewsView;
import de.holarse.renderer.Renderer;

@Component
public class ViewFactory
{
    Logger logger = LoggerFactory.getLogger(ViewFactory.class);

    @Qualifier("htmlRenderer")
    @org.springframework.beans.factory.annotation.Autowired
    Renderer renderer;      

    /**
     * Erzeugt eine View-Ansicht eines Artikel
     * @param article
     * @return
     */
    public ArticleView fromArticle(final Article article) {
        final ArticleView view = new ArticleView();
                
        final Map<AttachmentGroup, List<Attachment>> attachmentGroups = article.getAttachments()
                .stream()
                .filter(a -> StringUtils.isNotBlank(a.getAttachmentData()))
                .collect(Collectors.groupingBy(a -> a.getAttachmentGroup()));
        
        // TODO In AttachmentViews umwandeln
        view.getAttachments().putAll(attachmentGroups);

        // TODO In Tag-Views umwandeln
        view.getTags().addAll(article.getTags().stream().map(t -> t.getName()).collect(Collectors.toList()));        
        
        logger.debug("Content: {}", article.getContent());

        view.setNodeId(article.getNodeId());
        view.setMainTitle(article.getTitle());
        view.setAlternativeTitle1(article.getAlternativeTitle1());
        view.setAlternativeTitle2(article.getAlternativeTitle2());
        view.setAlternativeTitle3(article.getAlternativeTitle3());
        view.setSlug(article.getSlug());
        
        view.setContent(article.getContent());

        try {
            view.setFormattedContent( renderer.render(article.getContent() ));
        } catch (Exception e) {
            logger.warn("Fehler beim Rendern in Article Id=" + article.getId(), e);
            view.setContent("");
        }
        
        return view;
    }    

    /**
     * Erzeugt eine cachbare View-Ansicht einer News
     * @param news
     * @return
     */
    public NewsView fromNews(final News news) {
        NewsView view = new NewsView();
        
        final Map<AttachmentGroup, List<Attachment>> attachmentGroups = news.getAttachments()
                .stream()
                .filter(a -> StringUtils.isNotBlank(a.getAttachmentData()))
                .collect(Collectors.groupingBy(a -> a.getAttachmentGroup()));

        // TODO In Attachment-Views umwandeln
        view.getAttachments().putAll(attachmentGroups);
        
        logger.debug("Content: {}", news.getContent());

        view.setNodeId(news.getNodeId());
        view.setTitle(news.getTitle());
        view.setSubtitle(news.getSubtitle());
        view.setSlug(news.getSlug());
        view.setCreated(news.getCreated());
        view.setUpdated(news.getUpdated());
        
        view.setContent(news.getContent());

        try {
            view.setFormattedContent( renderer.render(news.getContent() ));
        } catch (Exception e) {
            logger.warn("Fehler beim Rendern in News Id=" + news.getId(), e);
            view.setContent("");
        }        
        
        return view;
    }      


}