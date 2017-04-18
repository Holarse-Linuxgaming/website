package web.services.backend.file;

import web.entities.Entity;

public interface PersistenceBackend<E extends Entity> {
    
    void write(E e, Class<E> clazz) throws Exception;
    <E> E read(Long uid, Class<E> clazz) throws Exception;
      
}
