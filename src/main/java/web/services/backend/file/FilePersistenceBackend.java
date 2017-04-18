package web.services.backend.file;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CacheResult;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import web.entities.Entity;
import web.exceptions.EntityNotFoundException;

@CacheDefaults(cacheName = "file-backend")
@Service
public class FilePersistenceBackend<E extends Entity> implements PersistenceBackend<E> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Value("${holarse.directories.base}")
    private String baseDirectory;
    
    @CachePut(cacheNames = "file-backend", key="{#clazz.simpleName, #entity.uid}")
    @Override
    public void write(final E entity, final Class<E> clazz) throws Exception {
        final File targetFile = new File(getDirectory(clazz).toFile(), entity.getUid() + ".xml");

        logger.debug("Trying to write article {} ({}).", entity.getUid(), targetFile.getAbsolutePath());
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            final Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.marshal(entity, targetFile);

            logger.debug("Writing entity {} ({}) complete", entity.getUid(), targetFile.getAbsolutePath());
        } catch (JAXBException e) {
            logger.error("Error while writing xml file {}", targetFile.getAbsolutePath(), e);
        }
    }

    @Cacheable(cacheNames="file-backend", key="{#clazz.simpleName, #uid}")
    @Override
    public <E> E read(final Long uid, final Class<E> clazz) throws Exception {
        logger.info("Loading {} {} from file backend", clazz.getSimpleName(), uid);
        final File file = new File(getDirectory(clazz).toFile(), uid + ".xml");
        if (!file.exists()) {
            logger.warn("File {} was not found.", file.getAbsolutePath());
            throw new EntityNotFoundException();
        }

        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            final Object entity = jaxbUnmarshaller.unmarshal(file);
            
            return clazz.cast(entity);

        } catch (JAXBException e) {
            logger.error("Error while loading xml file {}", file.getAbsoluteFile(), e);
        }        
        
        return null;
    }
    
    protected Path getDirectory(Class<?> clazz) {
        return FileSystems.getDefault().getPath(baseDirectory, pluralize(clazz.getSimpleName().toLowerCase()));
    }
    
    protected String pluralize(final String name) {
        if (name.endsWith("s")) {
            return name;
        }
        
        return name + "s";
    }
//    
//    protected Path getDirectory(E entity) {
//        return FileSystems.getDefault().getPath(baseDirectory, entity.getClass().getName());
//    }
    
//   
//    @SuppressWarnings("unchecked")
//    private Class<E> getEntityClass() {
//        final Type type = getClass().getGenericSuperclass();
//        final ParameterizedType paramType = (ParameterizedType) type;
//        return (Class<E>) paramType.getActualTypeArguments()[0];
//    }

}
