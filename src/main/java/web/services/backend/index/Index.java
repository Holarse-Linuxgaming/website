package web.services.backend.index;

import java.util.Collection;
import web.entities.Entity;

/**
 *
 * @author britter
 * @param <E>
 */
public interface Index<E extends Entity> {

    void delete(final Long uid);

    boolean exists(final Long uid);

    E get(final Long uid);

    Collection<E> getAll();

    int getSize();

    void purge();

    void put(final E entity);
    
}
