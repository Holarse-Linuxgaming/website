
package de.holarse.backend.decorator;

/**
 *
 * @author britter
 * @param <E> Entity (XML)
 * @param <V> View (DB)
 */
public interface CacheBuilder<E, V> {
    
    void buildCache();
    V migrate(E e);
    
}
