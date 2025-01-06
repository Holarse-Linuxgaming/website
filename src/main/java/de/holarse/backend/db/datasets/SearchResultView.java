package de.holarse.backend.db.datasets;

import java.time.LocalDateTime;

public interface SearchResultView {
    
    Integer getNodeId();
    String getTitle();
    String getUrl();
    String getDocType();
    String getTeaser();
    LocalDateTime getLastUpdate();
    Float getRank();

}
