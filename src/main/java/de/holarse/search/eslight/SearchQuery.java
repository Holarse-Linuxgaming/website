package de.holarse.search.eslight;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import de.holarse.search.Query;

public class SearchQuery implements Query {

    private final String searchTerm;

    public SearchQuery(String searchTerm) {
        this.searchTerm = searchTerm;
    }
    
    @Override
    public String asString() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        
        ObjectNode root = mapper.createObjectNode();
        ObjectNode query = root.putObject("query");
        ObjectNode multiMatch = query.putObject("multi_match");
        
        multiMatch.put("query", searchTerm);
        
        var fields = multiMatch.putArray("fields");
        fields.add("title^10")
              .add("subtitles^7")
              .add("alternativeTitles^7")
              .add("tags")
              .add("content^5");
        
        return mapper.writeValueAsString(root);
    }
    
    
    
}
