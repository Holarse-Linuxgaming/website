package web.services.backend.file;

import java.util.Collection;
import web.entities.Entity;

public interface PersistenceBackend<E extends Entity> {
    
    void write(E e) throws Exception;
    E read(Long uid) throws Exception;
    Collection<E> readAll();
    
    E reload(Long uid);
    Collection<E> reload();
    
}
