package de.holarse.rest;

import de.holarse.backend.db.Job;
import de.holarse.workers.JobConfiguration;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.OffsetDateTime;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    public Job prepareForJob(final Object payload, final String ctx) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(payload);
        
        final Job job = new Job();
        job.setCreated(OffsetDateTime.now());
        job.setContext(ctx);
        job.setQueue(JobConfiguration.IMPORT_QUEUE);
        job.setData(out.toByteArray());
        job.setTries(0);

        return job;        
    }
    
}
