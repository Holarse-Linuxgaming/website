package web.services.backend.index;

import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.entities.Entity;

public class EntityIndex<E extends Entity> implements Index<E> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Map<Long, E> idxMain;

    public EntityIndex() {
        this(3000);
    }

    public EntityIndex(int initalCapacity) {
        idxMain = new WeakHashMap<>(initalCapacity);
        logger.debug("Created index with capacity of " + initalCapacity);
    }

    @Override
    public boolean exists(final Long uid) {
        return idxMain.containsKey(uid);
    }

    @Override
    public E get(final Long uid) {
        return idxMain.get(uid);
    }

    @Override
    public Collection<E> getAll() {
        return idxMain.values();
    }

    @Override
    public void put(final E entity) {
        idxMain.put(entity.getUid(), entity);
    }

    @Override
    public void delete(final Long uid) {
        idxMain.remove(uid);
    }

    @Override
    public void purge() {
        idxMain.clear();
    }

    @Override
    public int getSize() {
        return idxMain.size();
    }

}
