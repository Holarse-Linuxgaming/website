package de.holarse.backend;

import de.holarse.entity.User;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
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

    private final List<User> users = new ArrayList<>(1750);

    public List<User> getUsers() {
        return users;
    }

    @PostConstruct
    public void loadFromFileSystem() {
        final File usersDirectory = new File(directory, "users");
        log.debug("loading users from " + usersDirectory);

        final long start = System.currentTimeMillis();
        users.clear();
        for (File userFile: usersDirectory.listFiles((dir, name) -> name.endsWith(".xml"))) {
            try {
                users.add(load(userFile));
            } catch (JAXBException je) {
                log.error("Could not read backend file " + userFile, je);
            }
        }

        final long duration = System.currentTimeMillis() - start;
        log.info("Loaded " + users.size() + " users after " + duration + " ms.");
    }

    protected User load(final File file) throws JAXBException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(User.class);
        final Unmarshaller um = jaxbContext.createUnmarshaller();

        return (User) um.unmarshal(file);
    }

}
