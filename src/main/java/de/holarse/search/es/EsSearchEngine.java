//package de.holarse.search.es;
//
//import de.holarse.backend.db.Searchable;
//import de.holarse.search.SearchEngine;
//import de.holarse.search.SearchResult;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import org.apache.http.HttpHost;
//import org.elasticsearch.action.bulk.BackoffPolicy;
//import org.elasticsearch.action.bulk.BulkProcessor;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.action.update.UpdateResponse;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.unit.ByteSizeUnit;
//import org.elasticsearch.common.unit.ByteSizeValue;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.sort.ScoreSortBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//@Component
//@Qualifier("elasticsearch")
//public class EsSearchEngine implements SearchEngine {
//
//    Logger logger = LoggerFactory.getLogger(EsSearchEngine.class);
//
//    private final static HttpHost HTTPHOST = new HttpHost("localhost", 9200, "http");
//
//    @Override
//    public List<SearchResult> search(final String query) {
//        logger.debug("Searching for {}", query);
//
//        final List<SearchResult> results = new ArrayList<>();
//        try (final RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(HTTPHOST))) {
//
//            final SearchRequest searchRequest = new SearchRequest("articles");
//            final SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//            sourceBuilder.query(QueryBuilders.boolQuery().must(QueryBuilders.multiMatchQuery(query, "title", "content")));
//            sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
//            searchRequest.source(sourceBuilder);
//
//            final SearchResponse searchResponse = client.search(searchRequest);
//            searchResponse.getHits().forEach(x -> logger.debug("{}", x.getSourceAsString()));
//            searchResponse.getHits().forEach(sr -> results.add(EsSearchResultFactory.build(sr)));
//        } catch (IOException ioex) {
//            logger.debug("Fehler bei Suchanfrage", ioex);
//        }
//
//        return results;
//    }
//
//    protected UpdateRequest createUpdateRequest(final Searchable searchable) {
//        final Map<String, Object> documentMap = new HashMap<>();
//        documentMap.put("nodeid", searchable.getId());
//        documentMap.put("title", searchable.getTitle());
//        documentMap.put("content", searchable.getContent());
//        documentMap.put("nodetype", searchable.getIndex());
//        documentMap.put("url", searchable.getUrl());
//
//        return new UpdateRequest("articles", "doc", searchable.getId().toString()).doc(documentMap).docAsUpsert(true);        
//    }
//    
//    @Override
//    public void update(final Searchable searchable) {
//        try (final RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(HTTPHOST))) {
//            final UpdateResponse resp = client.update(createUpdateRequest(searchable));
//        } catch (IOException ioex) {
//            logger.debug("Fehler bei Suchindexaktualisierung", ioex);
//        }
//    }
//
//    @Override
//    public void update(final Collection<Searchable> searchables) {
//        try (final RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(HTTPHOST))) {
//
////            BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
////                @Override
////                public void beforeBulk(long executionId, BulkRequest request) {}
////
////                @Override
////                public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {}
////
////                @Override
////                public void afterBulk(long executionId, BulkRequest request, Throwable failure) {}
////            })
////                    .setBulkActions(10000)
////                    .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
////                    .setFlushInterval(TimeValue.timeValueSeconds(5))
////                    .setConcurrentRequests(1)
////                    .setBackoffPolicy(
////                            BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
////                    .build();
////
////                searchables.stream().map(s -> createUpdateRequest(searchable)).forEach(bulkProcessor.);
////                    
////            
////                bulkProcessor.flush();
////                bulkProcessor.close();
//            
//        } catch (IOException ioex) {
//            logger.debug("Fehler bei Suchindexaktualisierung", ioex);
//        }
//
//    }
//
//    @Override
//    public void delete(Searchable searchable) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//}
