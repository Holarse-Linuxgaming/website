package de.holarse.backend.db;

import de.holarse.backend.db.types.QueueWorkerType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Table(name = "mqueue")
@Entity
public class Job extends Base {

    /** Es kann verschiedene Queues geben **/
    @Column(columnDefinition = "int not null default 0")
    private int qnumber;
    
    @Enumerated(EnumType.STRING)
    private QueueWorkerType worker;
    
    private String details;
    
    @Column(length = 4096)
    private String payload;
    
    // Priorität von 0 bis MAX (je höher umso wichtiger)
    @Column(columnDefinition = "int not null default 0")
    private long priority;
    
    @Column(columnDefinition = "int not null default 0")
    private int fails;
    
    @Column(columnDefinition = "boolean not null default false")
    private boolean done;

    public int getFails() {
        return fails;
    }

    public void setFails(int fails) {
        this.fails = fails;
    }
    
    public int getQnumber() {
        return qnumber;
    }

    public void setQnumber(int qnumber) {
        this.qnumber = qnumber;
    }

    public QueueWorkerType getWorker() {
        return worker;
    }

    public void setWorker(QueueWorkerType worker) {
        this.worker = worker;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    
    public void incrementFail() {
        this.fails++;
    }
    
}
