package de.holarse.search.es;

import de.holarse.search.SearchResult;
import java.util.Map;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.search.SearchHit;

public class EsSearchResultFactory {

    public static SearchResult build(final SearchHit hit) {
        final Map<String, DocumentField> fields = hit.getFields();
        return new SearchResult() {
            @Override
            public Long getId() {
                return Long.parseLong(fields.get("nodeid").getValue());
            }

            @Override
            public String getTitle() {
                return fields.get("title").getValue();
            }

            @Override
            public String getAlternativeTitle() {
                final DocumentField df = fields.get("alternativetitles");
                if (df != null) {
                    return df.getValue();
                }
                
                return "";                 
            }

            @Override
            public String getTags() {
                final DocumentField df = fields.get("tags");
                if (df != null) {
                    return df.getValue();
                }
                
                return "";                
            }

            @Override
            public String getUrl() {
                final DocumentField df = fields.get("url");
                if (df != null) {
                    return df.getValue();
                }
                
                return "";       
            }

            @Override
            public String getNodeType() {
                return fields.get("nodetype").getValue();
            }

            @Override
            public String getContent() {
                return fields.get("content").getValue();
            }
            
        };
    }
    
    private EsSearchResultFactory() {}
    
}
