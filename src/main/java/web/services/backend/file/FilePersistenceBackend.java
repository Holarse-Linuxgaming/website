package web.services.backend.file;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.entities.Entity;

public class FilePersistenceBackend<E extends Entity> implements PersistenceBackend<E> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void write(final E e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public E read(final Long uid) throws Exception {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<E> readAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public E reload(final Long uid) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<E> reload() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
