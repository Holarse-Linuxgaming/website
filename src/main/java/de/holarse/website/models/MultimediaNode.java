package de.holarse.website.models;

import java.util.Set;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class MultimediaNode extends Node {

    @ManyToMany
    @JoinTable(name = "nodes_videos", joinColumns = @JoinColumn(name = "node_id"), inverseJoinColumns = @JoinColumn(name = "video_id"))
    private Set<Video> videos;

    public Set<Video> getVideos() {
        return videos;
    }

    public void setVideos(Set<Video> videos) {
        this.videos = videos;
    }
    
}
