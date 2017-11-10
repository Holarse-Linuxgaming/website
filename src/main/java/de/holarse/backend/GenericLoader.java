/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.holarse.backend;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author comrad
 * @param <E>
 */
public abstract class GenericLoader<E> {
    
    Logger log = LoggerFactory.getLogger(GenericLoader.class);
    
    final protected List<E> entities = new ArrayList<>(1750);
    
    protected abstract File getEntityDirectory();
    protected abstract Class<E> getEntityClass();
    
    public List<E> getAll() {
        return entities;
    }
    
    public void loadFromFileSystem() {
        final File entityDirectory = getEntityDirectory();
        log.debug("loading entities from " + entityDirectory);

        final long start = System.currentTimeMillis();
        entities.clear();
        for (File entityFile: entityDirectory.listFiles((dir, name) -> name.endsWith(".xml"))) {
            try {
                entities.add(load(entityFile));
            } catch (JAXBException je) {
                log.error("Could not read backend file " + entityFile, je);
            }
        }

        final long duration = System.currentTimeMillis() - start;
        log.info("Loaded " + entities.size() + " entities after " + duration + " ms with " + this.getClass().getSimpleName());
    }
   
    @SuppressWarnings("unchecked")
    protected E load(final File file) throws JAXBException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(getEntityClass());
        final Unmarshaller um = jaxbContext.createUnmarshaller();

        return (E) um.unmarshal(file);
    }    
    
}
