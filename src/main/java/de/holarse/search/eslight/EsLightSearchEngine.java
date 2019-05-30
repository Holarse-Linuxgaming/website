package de.holarse.search.eslight;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.holarse.backend.db.Searchable;
import de.holarse.backend.db.Tag;
import de.holarse.backend.db.TagGroup;
import de.holarse.search.Query;
import de.holarse.search.SearchEngine;
import de.holarse.search.SearchResult;
import de.holarse.search.SettableSearchResult;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Direkte ElasticSearch-Anbindung per Java-Http-Client und Json
 * @author comrad
 */
@Component
@Qualifier("eslight")
public class EsLightSearchEngine implements SearchEngine {

    Logger logger = LoggerFactory.getLogger(EsLightSearchEngine.class);
    
    @Value("${es.host}")
    private String esHost;    
    
    private final static String SEARCH_URL = "/articles,news/_search";
    
    private HttpRequest createRequest(final String url, final Query query) throws Exception {
        return HttpRequest  .newBuilder()
                            .uri(URI.create(url))
                            .header("Content-Type", "application/json; encoding=UTF-8")
                            .POST(HttpRequest.BodyPublishers.ofString(query.asString()))
                            .build();
    }
    
    private HttpClient httpClient() {
        return HttpClient.newBuilder().version(HttpClient.Version.HTTP_1_1).connectTimeout(Duration.ofSeconds(4)).build();
    }
    
    private HttpResponse<String> execute(final HttpRequest request) throws IOException, InterruptedException {
        return httpClient().send(request, BodyHandlers.ofString());
    }
    
    private List<SearchResult> parseSearchResult(HttpResponse<String> response) throws IOException {
        final JsonNode root = new ObjectMapper().readTree(response.body());
        final List<JsonNode> hits = root.path("hits").path("hits").findValues("_source");
        
        var result = new ArrayList<SearchResult>(100);
        for (final JsonNode node : hits) {
            final SettableSearchResult sr = new SimpleSearchResult();
            sr.setTitle(node.get("title").textValue());
            sr.setContent(node.get("content").textValue());
            
            final JsonNode tags = node.findPath("tags");
            if (tags.isArray()) {
                var _tags = new ArrayList<String>(15);
                tags.elements().forEachRemaining(t -> _tags.add(t.textValue()));
                sr.setTags(_tags);                
            } else {
                sr.setTags(List.of(tags.textValue()));
            }
            
            final JsonNode ats = node.findPath("alternativeTitles");
            if (ats.isArray()) {
                var _ats = new ArrayList<String>(15);
                ats.elements().forEachRemaining(a -> _ats.add(a.textValue()));
                sr.setAlternativeTitles(_ats);                
            } else {
                sr.setAlternativeTitles(List.of(ats.textValue()));
            }            
        }
        
        return result;
    }
    
    /**
     * {"query":{"bool":{"must":[{"multi_match":{"query":"tes","fields":["alternativeTitles^7.0","comments^1.0","content^1.0","subtitles^7.0","tags^15.0","title^10.0"],"type":"best_fields","operator":"OR","slop":0,"prefix_length":0,"max_expansions":50,"zero_terms_query":"NONE","auto_generate_synonyms_phrase_query":true,"fuzzy_transpositions":true,"boost":1.0}}],"filter":[{"term":{"searchable":{"value":true,"boost":1.0}}}],"adjust_pure_negative":true,"boost":1.0}},"sort":[{"_score":{"order":"desc"}}]}
     * @param searchTerm
     * @return 
     */
    @Override
    public List<SearchResult> search(final String searchTerm) {
        try {
            return parseSearchResult(
                    execute(
                            createRequest(esHost + "/" + SEARCH_URL, new SearchQuery(searchTerm))
                    ));
        } catch (Exception e) {
            logger.error("Fehler beim Suchaufruf", e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<SearchResult> searchByTags(final Collection<Tag> tags, final String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(final Searchable searchable) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(final Iterable<? extends Searchable> searchables) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateTags(final Iterable<TagGroup> tagGroups) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateEverything(final Iterable<? extends Searchable> searchables, final Iterable<TagGroup> tagGroups) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(final Searchable searchable) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
