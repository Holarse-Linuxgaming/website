package de.holarse.backend.db;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BranchableNode extends SluggableNode {
    
    private String branch;

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
   
}