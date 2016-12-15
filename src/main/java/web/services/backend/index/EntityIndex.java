package web.services.backend.index;

import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import web.entities.Entity;

@Component
@Scope()
public class EntityIndex<E extends Entity> implements Index<E> {
    
    private final Map<Long, E> idxMain;

    public EntityIndex() {
        this(1500);
    }
    
    public EntityIndex(int initalCapacity) {
        idxMain = new WeakHashMap<>(initalCapacity);
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
