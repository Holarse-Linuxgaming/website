package de.holarse.backend.views;

import java.util.ArrayList;
import java.util.List;

/**
 * View für die Zugriffszahlen
 */
public class PageVisitReport implements View {
    
    private final String dateFrom;
    private final String dateUntil;

    private final Long nodeId;

    private final List<PageVisitResult> items = new ArrayList<>();

    public PageVisitReport(final String dateFrom, final String dateUntil, final Long nodeId, final List<PageVisitResult> items) {
        this.dateFrom = dateFrom;
        this.dateUntil = dateUntil;
        this.nodeId = nodeId;
        this.items.addAll(items);
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public String getDateUntil() {
        return dateUntil;
    }

    public List<PageVisitResult> getItems() {
        return items;
    }

    public Long getNodeId() {
        return nodeId;
    }
}