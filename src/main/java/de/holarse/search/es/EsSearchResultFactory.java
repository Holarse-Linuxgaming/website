package de.holarse.search.es;

import de.holarse.search.SearchResult;
import java.util.List;
import java.util.Map;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EsSearchResultFactory {

    static Logger logger = LoggerFactory.getLogger(EsSearchResultFactory.class);
    
    public static SearchResult build(final SearchHit hit) {
        final Map<String, Object> fields = hit.getSourceAsMap();
//        logger.debug("fields: {}", fields);
        return new SearchResult() {
            @Override
            public String getTitle() {
                return (String) fields.get("title");
            }

            @Override
            public List<String> getAlternativeTitles() {
                return (List<String>) fields.get("alternativeTitles");               
            }

            @Override
            public List<String> getTags() {
                return (List<String>) fields.get("tags");                    
            }

            @Override
            public String getUrl() {
                return (String) fields.get("url");        
            }

            @Override
            public String getNodeType() {
                return (String) fields.get("type");  
            }

            @Override
            public String getContent() {
                return (String) fields.get("content");
            }

            @Override
            public Long getId() {
                return Long.parseLong(hit.getId());
            }
            
        };
    }
    
    private EsSearchResultFactory() {}
    
}
