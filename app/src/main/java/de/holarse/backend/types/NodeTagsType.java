package de.holarse.backend.types;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for Node-Tags, which are frozen in time for ArticleRevision or NewsRevision
 */
public class NodeTagsType implements java.io.Serializable {

    private List<String> tags = new ArrayList<>();

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
