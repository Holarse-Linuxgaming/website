package de.holarse.backend.views.admin;

import java.util.List;

/**
 * Generische Sammelklasse für alle Admin-Views mit einer Pagination
 * @param <T>
 */
public class PaginatableAdminView<T extends AdminView> {

    private final List<T> items;
    private final ClientPaginationView pagination;

    public PaginatableAdminView(final List<T> items, final ClientPaginationView pagination) {
        this.items = items;
        this.pagination = pagination;
    }

    public List<T> getItems() {
        return items;
    }

    public ClientPaginationView getPagination() {
        return pagination;
    }

}