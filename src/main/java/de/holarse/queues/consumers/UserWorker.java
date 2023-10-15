package de.holarse.queues.consumers;

import de.holarse.backend.db.Job;
import de.holarse.backend.db.User;
import de.holarse.backend.db.UserData;
import de.holarse.backend.db.UserSlug;
import de.holarse.backend.db.repositories.JobRepository;
import de.holarse.backend.db.repositories.RoleRepository;
import de.holarse.backend.types.PasswordType;
import de.holarse.web.services.SlugService;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.time.OffsetDateTime;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserWorker {

    private final static transient Logger log = LoggerFactory.getLogger(UserWorker.class);

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    SlugService slugService;

    @Scheduled(fixedRate = 10000)
    public void importUsers() {
        EntityManager em = entityManagerFactory.createEntityManager();
        final List<Job> jobs = jobRepository.findOpenJobs(JobConfiguration.IMPORT_QUEUE, JobQueueContext.USERS.toString().toLowerCase());
        for (final Job job : jobs) {
            log.debug("Working on Job#{}", job);
            EntityTransaction tx = em.getTransaction();  
            tx.begin();
            try {
                final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(job.getData()));

                de.holarse.backend.api.User api_u = (de.holarse.backend.api.User) ois.readObject();

                // User anlegen
                final User user = new User();
                if (api_u.getUid() != null) {
                    user.setDrupalId(api_u.getUid().intValue());
                }
                user.setLogin(api_u.getLogin());
                user.setEmail(api_u.getEmail());
                if (api_u.getPassword().getType().equalsIgnoreCase("md5")) {
                    user.setHashType(PasswordType.md5);
                } else {
                    user.setHashType(PasswordType.bcrypt);
                }

                user.setDigest(api_u.getPassword().getDigest());

                // Rollen
                // Standardrolle ergänzen
                user.getRoles().add(roleRepository.findByCode("TRUSTED_USER"));
                // Sonstige Rollen ergänzen
                for (final de.holarse.backend.api.Role r : api_u.getRoles()) {
                    user.getRoles().add(roleRepository.findByCode(r.getValue()));
                }

                // UserData
                final UserData userData = new UserData();
                user.setUserData(userData);

               
                // Benutzer anlegen
                em.persist(user);
                
                // Slug erzeugen und merken
                UserSlug userSlug = slugService.slugify(user);
                user.getUserSlugs().add(userSlug);
                userSlug.setUser(user);                
                em.persist(userSlug);                
                log.info("User creation complete");

                job.setCompleted(true);
                tx.commit();
            } catch (final Exception ex) {
                log.error("Error on import job #{}", job.getId(), ex);
                job.setTries(job.getTries() + 1);
                job.setUpdated(OffsetDateTime.now());
               
            } finally {
                em.persist(job);
            }
        }
    }

}
