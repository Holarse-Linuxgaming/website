package de.holarse.workers;

import de.holarse.backend.db.Job;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.JobRepository;
import de.holarse.backend.db.repositories.RoleRepository;
import de.holarse.backend.db.repositories.UserRepository;
import de.holarse.backend.types.PasswordType;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.time.OffsetDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UserWorker {

    private final static transient Logger log = LoggerFactory.getLogger(UserWorker.class);

    @Autowired
    JobRepository jobRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    RoleRepository roleRepository;    

    @Scheduled(fixedRate = 10000)
    public void importUsers() {
        final List<Job> jobs = jobRepository.findOpenJobs(JobConfiguration.IMPORT_QUEUE, JobQueueContext.USERS.toString().toLowerCase());
        for (final Job job : jobs) {
            log.debug("Working on Job#{}", job);
            try {
                final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(job.getData()));

                de.holarse.backend.api.User api_u = (de.holarse.backend.api.User) ois.readObject();
                
                // User anlegen
                final User db_u = new User();
                if (api_u.getUid() != null) {
                    db_u.setDrupalId(api_u.getUid().intValue());
                }
                db_u.setLogin(api_u.getLogin());
                db_u.setEmail(api_u.getEmail());
                db_u.setSlug(api_u.getLogin().toLowerCase());
                if (api_u.getPassword().getType().equalsIgnoreCase("md5")) {
                    db_u.setHashType(PasswordType.md5);
                } else {
                    db_u.setHashType(PasswordType.bcrypt);
                }

                db_u.setDigest(api_u.getPassword().getDigest());
                
                // Rollen
                // Standardrolle ergänzen
                db_u.getRoles().add(roleRepository.findByCode("TRUSTED_USER"));
                // Sonstige Rollen ergänzen
                for (final de.holarse.backend.api.Role r : api_u.getRoles()) {
                    db_u.getRoles().add(roleRepository.findByCode(r.getValue()));
                }
                
                userRepository.saveAndFlush(db_u);
                
                job.setCompleted(true);
                jobRepository.save(job);
                
            } catch (final Exception ex) {
                log.error("Error on import job #{}", job.getId(), ex);
                job.setTries(job.getTries() + 1);
                job.setUpdated(OffsetDateTime.now());
                
                jobRepository.save(job);
            }
        }
    }

}
