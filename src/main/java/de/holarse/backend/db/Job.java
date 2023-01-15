package de.holarse.backend.db;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "jobs")
@Entity
public class Job extends TimestampedBase implements Serializable {
    
    private Integer queue;
    private String context;
    
    private byte[] data;
    
    private boolean completed;
    private boolean ignore;
    
    private Integer tries;

    public Integer getTries() {
        return tries;
    }

    public void setTries(Integer tries) {
        this.tries = tries;
    }

    public Integer getQueue() {
        return queue;
    }

    public void setQueue(Integer queue) {
        this.queue = queue;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }
    
}
