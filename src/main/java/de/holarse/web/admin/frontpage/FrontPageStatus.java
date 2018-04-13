package de.holarse.web.admin.frontpage;

public class FrontPageStatus {

    private Long nodeId;
    private boolean postable;

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public boolean isPostable() {
        return postable;
    }

    public void setPostable(boolean postable) {
        this.postable = postable;
    }
    
}
