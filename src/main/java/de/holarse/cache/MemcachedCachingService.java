package de.holarse.cache;

import com.whalin.MemCached.MemCachedClient;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Qualifier("memcached")
@Scope("singleton")
public class MemcachedCachingService implements CachingService {

    private static MemCachedClient mc = new MemCachedClient();
    
    @Override
    public Object get(final String id) {
        return mc.get(id);
    }
    
    /**
     * Fügt einen Cacheeintrag mit einer Standardablaufzeit von 1h ein.
     * @param id
     * @param object 
     */
    @Override
    public void put(final String id, final Object object) {
        put(id, object, LocalDateTime.now().plus(1, ChronoUnit.HOURS));
    }    

    @Override
    public void put(final String id, final Object object, final LocalDateTime expiration) {
        Date expDate = null;
        if (expiration != null) {
            expDate = Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant());
        }
        
        mc.add(id, object, expDate);
    }

    @Override
    public void invalidate(final String id) {
        mc.delete(id);
    }
    
}
