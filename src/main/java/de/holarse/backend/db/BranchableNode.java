package de.holarse.backend.db;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BranchableNode extends SluggableNode {
    
    @Column(columnDefinition = "varchar(255) default 'master'")
    private String branch;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
   
}