package de.holarse.search.es;

import de.holarse.backend.db.Searchable;
import de.holarse.search.SearchEngine;
import de.holarse.search.SearchResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EsSearchEngine implements SearchEngine {

    Logger logger = LoggerFactory.getLogger(EsSearchEngine.class);

    private final static HttpHost HTTP_HOST = new HttpHost("localhost", 9200, "http");

    protected RestHighLevelClient getNewClient() {
        return new RestHighLevelClient(RestClient.builder(HTTP_HOST));
    }

    @Override
    public List<SearchResult> search(final String query) {
        logger.debug("Searching for {}", query);

        final List<SearchResult> results = new ArrayList<>();

        final SearchRequest searchRequest = new SearchRequest("articles");
        final SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.multiMatchQuery(query, "title", "content", "alternativeTitles", "tags").type(MultiMatchQueryBuilder.Type.BEST_FIELDS));
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        searchRequest.source(sourceBuilder);

        try(final RestHighLevelClient client = getNewClient()) {
            final SearchResponse searchResponse = client.search(searchRequest);
            searchResponse.getHits().forEach(x -> logger.debug("{}", x.getSourceAsString()));
            searchResponse.getHits().forEach(sr -> results.add(EsSearchResultFactory.build(sr)));
            client.close();
        } catch (IOException ioex) {
            logger.debug("Fehler bei Suchanfrage", ioex);
        }

        return results;
    }

    protected UpdateRequest createUpdateRequest(final Searchable searchable) throws IOException {
        final XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject()
                .field("title", searchable.getTitle())
                .field("alternativeTitles", searchable.getAlternativeTitles().toArray(new String[searchable.getAlternativeTitles().size()]))
                .field("tags", searchable.getTags().stream().map(t -> t.getName()).collect(Collectors.toSet()).toArray(new String[searchable.getTags().size()]))
                .field("content", searchable.getContent())
                .field("url", searchable.getUrl())
                .field("type", searchable.getType())
        .endObject();

        return new UpdateRequest("articles", searchable.getType(), searchable.getId().toString()).doc(builder).docAsUpsert(true);
    }

    @Override
    public void update(final Searchable searchable) throws IOException {
        final List<Searchable> list = new ArrayList<>(1);
        list.add(searchable);
        update(list);
    }

    @Override
    public void update(final Collection<Searchable> searchables) throws IOException {
        final BulkRequest bulkRequest = new BulkRequest();
        for (final Searchable s: searchables) {
            try {
                bulkRequest.add(createUpdateRequest(s));
            } catch (IOException ioex) {
                logger.debug("Fehler bei Suchindexaktualisierung", ioex);
            }
        }
        
        try(final RestHighLevelClient client = getNewClient()) {
            client.bulk(bulkRequest);
        }
    }

    @Override
    public void delete(final Searchable searchable) throws IOException {
        try(final RestHighLevelClient client = getNewClient()) {
            client.delete(new DeleteRequest("documents", searchable.getType(), searchable.getId().toString()));
        }        
        
    }

}
