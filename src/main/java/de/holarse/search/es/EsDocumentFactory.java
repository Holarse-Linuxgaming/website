package de.holarse.search.es;

import de.holarse.backend.db.Article;
import de.holarse.backend.db.News;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.IOException;
import java.util.stream.Collectors;

public class EsDocumentFactory {

    public static XContentBuilder toJson(final Article article) throws IOException {
        final XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                .field("title", article.getTitle())
                .field("alternativeTitles", article.getAlternativeTitles().toArray(new String[article.getAlternativeTitles().size()]))
                .field("tags", article.getTags().stream().map(t -> t.getName()).collect(Collectors.toSet()).toArray(new String[article.getTags().size()]))
                .field("content", article.getContent())
                .field("url", article.getUrl())
                .field("comments", article.getComments().stream().map(c -> c.getContent()).collect(Collectors.toList()).toArray())
                .field("searchable", !article.getDeleted() && !article.getDraft() && article.getPublished() )
                .field("views", 0)
                .endObject();

        return builder;
    }

    public static XContentBuilder toJson(News news) throws IOException {
        final XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                .field("title", news.getTitle())
                .field("subtitle", news.getSubtitle())
                .field("content", news.getContent())
                .field("url", news.getUrl())
                .field("category", news.getCategory() != null ? news.getCategory().toString() : "")
                .field("comments", news.getComments().stream().map(c -> c.getContent()).collect(Collectors.toList()).toArray())
                .field("searchable", !news.getDeleted() && !news.getDraft() && news.getPublished() )
                .endObject();

        return builder;
    }

    private EsDocumentFactory() {};

}
