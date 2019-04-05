package de.holarse.cache;

import java.time.LocalDateTime;

public interface CachingService {

    Object get(String id);
    void put(String id, Object object);
    void put(String id, Object object, LocalDateTime expiration);    
    void invalidate(String id);
    
}
