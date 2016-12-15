package web.services;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import web.entities.Entity;
import web.exceptions.EntityNotFoundException;
import web.services.backend.index.Index;

public abstract class AbstractService<E extends Entity> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Index<E> index;

    abstract protected String getDirectory();

    /**
     * Initiales laden vom Persistence-Backend anfordern
     *
     * @throws IOException
     */
    @PostConstruct
    public void initFromDisk() throws IOException {
        logger.debug("--------------------------------------------------------------------------------------------------");
        logger.debug("Loading " + getEntityClass().getCanonicalName() + " from " + getDirectory());
        index.purge();
        Files.list(Paths.get(getDirectory())).filter(Files::isRegularFile).forEach(p -> {
            try {
                loadFromDisk(p.toFile());
            } catch (EntityNotFoundException ex) {
                logger.warn("File {} not found", p.toString(), ex);
            }
        });
        logger.debug("--------------------------------------------------------------------------------------------------");
        logger.debug("Loaded: {} " + getEntityClass().getCanonicalName() + " " + index.getSize());
        logger.debug("--------------------------------------------------------------------------------------------------");
    }

    /**
     * Reload vom Persistence-Backend anstossen
     *
     * @param uid
     * @throws EntityNotFoundException
     */
    public void reloadFromDisk(final Long uid) throws EntityNotFoundException {
        loadFromDisk(getFilename(uid));
    }

    /**
     * Vom Persistence-Backend lesen
     *
     * @param file
     * @throws EntityNotFoundException
     */
    protected void loadFromDisk(final File file) throws EntityNotFoundException {
        logger.debug("Trying to load file " + file.getAbsolutePath());

        if (!file.exists()) {
            logger.warn("File {} was not found.", file.getAbsolutePath());
            throw new EntityNotFoundException();
        }

        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(getEntityClass());
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            final E entity = (E) jaxbUnmarshaller.unmarshal(file);

            logger.debug("File loaded {}", entity);

            index.put(entity);

        } catch (JAXBException e) {
            logger.error("Error while loading xml file {}", file.getAbsoluteFile(), e);
        }
    }

    /**
     * Auf das Persistence-Backend zurückschreiben
     *
     * @param entity
     */
    public void writeToDisk(final E entity) {
        final File targetFile = new File(getDirectory(), entity.getUid() + ".xml");

        logger.debug("Trying to write article {} ({}).", entity.getUid(), targetFile.getAbsolutePath());
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(getEntityClass());
            final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(entity, targetFile);

            logger.debug("Writing entity {} ({}) complete", entity.getUid(), targetFile.getAbsolutePath());
            index.delete(entity.getUid());
        } catch (JAXBException e) {
            logger.error("Error while writing xml file {}", targetFile.getAbsolutePath(), e);
        }
    }

    /**
     * Dateinamen der Entity zusammenstellen
     *
     * @param uid
     * @return
     */
    protected File getFilename(final Long uid) {
        return new File(getDirectory(), uid + ".xml");
    }

    /**
     * Generic-Klasse der Entity finden
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private Class<E> getEntityClass() {
        final Type type = getClass().getGenericSuperclass();
        final ParameterizedType paramType = (ParameterizedType) type;
        return (Class<E>) paramType.getActualTypeArguments()[0];
    }

    /**
     * Anhand der UID finden
     *
     * @param uid
     * @return
     * @throws EntityNotFoundException
     */
    public E findById(final Long uid) throws EntityNotFoundException {
        if (index.exists(uid)) {
            logger.info("Found uid " + uid + " in index");
            return index.get(uid);
        }

        // Versuchen das nochmal von der Platte zu laden
        logger.debug("Trying to load uid " + uid + " from disk");
        loadFromDisk(getFilename(uid)); // Exception falls nicht gefunden

        // Jetzt ists im Index
        return index.get(uid);
    }

    /**
     * Alle zurückgeben
     *
     * @return
     */
    public Collection<E> findAll() {
        return index.getAll();
    }

}
