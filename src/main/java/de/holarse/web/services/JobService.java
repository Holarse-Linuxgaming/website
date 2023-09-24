package de.holarse.web.services;

import de.holarse.backend.db.Job;
import de.holarse.backend.db.User;
import de.holarse.backend.db.repositories.JobRepository;
import de.holarse.workers.JobConfiguration;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    
    @Autowired
    private JobRepository jobRepository;
    
    public static final Job newJob() {
        final Job job = new Job();
        job.setCreated(OffsetDateTime.now());
        job.setTries(0);
        return job;
    }
    
    @Deprecated
    public Job prepareForJob(final Object payload, final String ctx) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(payload);
        
        final Job job = newJob();
        job.setContext(ctx);
        job.setQueue(JobConfiguration.IMPORT_QUEUE);
        job.setData(out.toByteArray());

        return job;        
    }    
    
    public void createUserVerificationMailJob(final User user) {
        final Job job = newJob();
        job.setContext("user_verification_mail");
        job.setQueue(JobConfiguration.MAIL_QUEUE);
        job.setData(user.getId().toString().getBytes());
        job.setTries(0);
        
        jobRepository.save(job);
    }
    
}
