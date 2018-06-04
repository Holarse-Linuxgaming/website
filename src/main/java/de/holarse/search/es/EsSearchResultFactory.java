package de.holarse.search.es;

import de.holarse.search.SearchResult;
import java.util.Map;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EsSearchResultFactory {

    static Logger logger = LoggerFactory.getLogger(EsSearchResultFactory.class);
    
    public static SearchResult build(final SearchHit hit) {
        final Map<String, Object> fields = hit.getSourceAsMap();
        logger.debug("fields: {}", fields);
        return new SearchResult() {
            @Override
            public Long getId() {
                return (Long) fields.get("nodeid");
            }

            @Override
            public String getTitle() {
                return (String) fields.get("title");
            }

            @Override
            public String getAlternativeTitle() {
                return (String) fields.get("alternativetitle");               
            }

            @Override
            public String getTags() {
                return (String) fields.get("tags");                    
            }

            @Override
            public String getUrl() {
                return (String) fields.get("url");        
            }

            @Override
            public String getNodeType() {
                return (String) fields.get("nodetype");  
            }

            @Override
            public String getContent() {
                return (String) fields.get("content");
            }
            
        };
    }
    
    private EsSearchResultFactory() {}
    
}
