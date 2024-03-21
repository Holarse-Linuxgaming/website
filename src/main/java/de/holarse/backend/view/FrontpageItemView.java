package de.holarse.backend.view;

import de.holarse.backend.types.NodeType;

public interface FrontpageItemView {

    Integer getNodeId();
    String getSlug();
    NodeType getNodeType();

    String getTitle();

    String getTeaser();

}
