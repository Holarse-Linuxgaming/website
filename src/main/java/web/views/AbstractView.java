package web.views;

import web.entities.Entity;

public class AbstractView<T> implements View<T> {

    private final T entity;
    
    public AbstractView(final T entity) {
        this.entity = entity;
    }
    
    public T getEntity() {
        return entity;
    }
    
}
