package de.holarse.backend;

import de.holarse.entity.User;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserLoader {

    Logger log = LoggerFactory.getLogger(UserLoader.class);

    @Value("${holarse.document.path}")
    private String directory;

    private final List<User> users = new ArrayList<>(3000);

    public List<User> getUsers() {
        return users;
    }

    @PostConstruct
    public void loadFromFileSystem() {
        log.debug("loading users");
        final File[] userFiles = new File(directory, "users").listFiles((File dir, String name) -> name.endsWith(".xml"));

        for (final File f : userFiles) {
            try {
                users.add(load(f));
            } catch (JAXBException je) {
                log.error("Could not read user file " + f.getAbsolutePath(), je);
            }
        }

    }

    protected User load(final File file) throws JAXBException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
        final Unmarshaller um = jaxbContext.createUnmarshaller();

        return (User) um.unmarshal(file);
    }
    
    protected void save(final User user, final String name) throws JAXBException, IOException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
        final Marshaller m = jaxbContext.createMarshaller();
        
        m.marshal(user, File.createTempFile("user", ".xml"));
    }
    
}
