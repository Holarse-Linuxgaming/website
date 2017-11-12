package de.holarse.backend;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generisches Loader für alle Entites vom Datenträger
 * @author comrad
 * @param <E>
 */
public abstract class GenericLoader<E> {
    
    private final static transient Logger log = LoggerFactory.getLogger(GenericLoader.class);
    
    final protected List<E> entities = new ArrayList<>(1750);
    
    protected abstract File getEntityDirectory();
    protected abstract Class<E> getEntityClass();
    
    public List<E> getAll() {
        return entities;
    }
    
    public void loadFromFileSystem() {
        final File entityDirectory = getEntityDirectory();
        if (entityDirectory == null) {
            throw new IllegalStateException("Entity directory not set!");
        }
        log.debug("loading entities from " + entityDirectory);

        final long start = System.currentTimeMillis();
        loadFiles(entityDirectory, entities);
        final long duration = System.currentTimeMillis() - start;
        log.info("Loaded " + entities.size() + " entities after " + duration + " ms with " + this.getClass().getSimpleName());
    }
    
    /**
     * Lädt alle XML-Dateien aus einem Verzeichnis und legt diese in die
     * Ziel-Collection
     * @param entityDirectory
     * @param target 
     */
    protected void loadFiles(final File entityDirectory, final Collection<E> target) {
        target.clear();
        for (File entityFile: entityDirectory.listFiles((dir, name) -> name.endsWith(".xml"))) {
            try {
                target.add(load(entityFile));
            } catch (JAXBException je) {
                log.error("Could not read backend file " + entityFile, je);
            }
        }
    }
    
    /**
     * Lädt eine Entitydatei von der Festplatte und wandelt 
     * sie in ein Object um
     * @param file
     * @return
     * @throws JAXBException 
     */
    @SuppressWarnings("unchecked")
    protected E load(final File file) throws JAXBException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(getEntityClass());
        final Unmarshaller um = jaxbContext.createUnmarshaller();

        return (E) um.unmarshal(file);
    }    
    
}
