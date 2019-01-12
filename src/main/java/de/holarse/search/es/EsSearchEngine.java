package de.holarse.search.es;

import de.holarse.backend.db.Searchable;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.TagGroup;
import de.holarse.search.SearchEngine;
import de.holarse.search.SearchResult;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
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
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EsSearchEngine implements SearchEngine {

    Logger logger = LoggerFactory.getLogger(EsSearchEngine.class);
    
    @Value("${es.host}")
    private String esHost;

    private static HttpHost HTTP_HOST;
    
    @PostConstruct
    public void initEsHost() {
        HTTP_HOST = new HttpHost(esHost, 9200, "http");        
    }
    
    protected RestHighLevelClient getNewClient() {        
        logger.info("Getting new ES connection to " + HTTP_HOST.toURI());
        return new RestHighLevelClient(RestClient.builder(HTTP_HOST));
    }

    @Override
    public List<SearchResult> searchByTags(final Collection<Tag> tags, final String query) {
        logger.debug("Searching for Tags {}", tags);

        final List<SearchResult> results = new ArrayList<>();

        final SearchRequest searchRequest = new SearchRequest("articles");

        final BoolQueryBuilder qbq = QueryBuilders.boolQuery();
        tags.forEach(t -> qbq.must(QueryBuilders.termQuery("tags", t.getName())));
        
        if (StringUtils.isNotBlank(query)) {
            qbq.must(fieldMatches(query));
        }
        
        qbq.filter(QueryBuilders.termQuery("searchable", true));
        
        final SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(qbq);
        
        sourceBuilder.sort("views", SortOrder.DESC);
        searchRequest.source(sourceBuilder);

        logger.debug("Query: {}", searchRequest.source());
        
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
    
    protected QueryBuilder fieldMatches(final String query) {
        return QueryBuilders.multiMatchQuery(query)
                        .field("title", 10.0f)
                        .field("subtitles", 7.0f)
                        .field("alternativeTitles", 7.0f)
                        .field("tags", 15.0f)
                        .field("content")
                        .field("comments", 1.0f)
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS);        
    }
    
    @Override
    public List<SearchResult> search(final String query) {
        logger.debug("Searching for {}", query);

        final List<SearchResult> results = new ArrayList<>();

        final SearchRequest searchRequest = new SearchRequest("articles", "news");
        final SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(
                QueryBuilders.boolQuery()
                .must(fieldMatches(query))
                .filter(QueryBuilders.termQuery("searchable", true))
        );
        sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        searchRequest.source(sourceBuilder);

        logger.debug("Query: {}", searchRequest.source());        
        
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
        final XContentBuilder builder = searchable.toJson();

        logger.debug("UPDATE: {}", builder.toString());
        
        final UpdateRequest updateReq = new UpdateRequest(searchable.getType(), "docs", searchable.getId().toString())
                                            .doc(builder)
                                            .docAsUpsert(true);
        
        logger.debug("UpdateRequest: {} ", updateReq);
        return updateReq;
    }

    @Override
    public void update(final Searchable searchable) throws IOException {
        final List<Searchable> list = new ArrayList<>(1);
        list.add(searchable);
        update(list);
    }

    @Override
    public void updateEverything(final Iterable<? extends Searchable> searchables, final Iterable<TagGroup> tagGroups) throws IOException {
        update(searchables);
        updateTags(tagGroups);
    }
    
    @Override
    public void update(final Iterable<? extends Searchable> searchables) throws IOException {
        //final List<UpdateRequest> requests = new ArrayList<>(25);
        
        final BulkRequest bulkRequest = new BulkRequest();
        
        for (final Searchable s: searchables) {
            logger.info("Adding {} to bluk updates for documents", s.getTitle());
            bulkRequest.add(createUpdateRequest(s));
            //requests.add(createUpdateRequest(s));
        }
        
        logger.info("Updating {} search items in document bulk", bulkRequest.numberOfActions());

        if (bulkRequest.numberOfActions() <= 0) {
            return;
        }        
        
        try(final RestHighLevelClient client = getNewClient()) {
            logger.info("Executing bulk request for documents");
            client.bulk(bulkRequest);
        } catch (IOException ioex) {
            logger.error("Error while executing bulk requests for documents", ioex);
        } finally {
            logger.info("Bulk for documents done");
        }
    }

    @Override
    public void updateTags(final Iterable<TagGroup> tagGroups) throws IOException {
        final BulkRequest bulkRequest = new BulkRequest();
        for (final TagGroup tagGroup : tagGroups) {
            logger.info("Adding {} to bluk updates for documents", tagGroup.getName());            
            final XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject()
                    .field("tagGroup", tagGroup.getName())
                    .field("tags", tagGroup.getTags().stream().map(t -> t.getName()).collect(Collectors.toSet()).toArray())
            .endObject();

            logger.debug("UPDATE: {}", builder.toString());
        
            final UpdateRequest updateReq = new UpdateRequest("tags", "docs", tagGroup.getId().toString())
                                                .doc(builder)
                                                .docAsUpsert(true);          
                                                
            bulkRequest.add(updateReq);
        }
        
        logger.info("Updating {} search items in tag bulk", bulkRequest.numberOfActions());        
        if (bulkRequest.numberOfActions() <= 0) {
            return;
        }
        
        try(final RestHighLevelClient client = getNewClient()) {
            logger.info("Executing bulk request for tags");
            client.bulk(bulkRequest);
        } catch (IOException ioex) {
            logger.error("Error while executing bulk requests for tags", ioex);
        } finally {
            logger.info("Bulk for tags done");
        }        
    }

    @Override
    public void delete(final Searchable searchable) throws IOException {
        try(final RestHighLevelClient client = getNewClient()) {
            client.delete(new DeleteRequest("documents", searchable.getType(), searchable.getId().toString()));
        }        
        
    }

}
