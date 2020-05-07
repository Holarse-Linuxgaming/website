package de.holarse.backend.views.admin;

/**
 * Pagination-View für clientseitig gerenderte Views (Javascript-seitig)
 */
public class ClientPaginationView {

    private int currentPage = 0;
    private int maxPages = 0;
    private int pageSize = 30;

    public ClientPaginationView() {
    }

    public ClientPaginationView(final int currentPage, final int maxPages, final int pageSize) {
        this.currentPage = currentPage;
        this.maxPages = maxPages;
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxPages() {
        return maxPages;
    }

    public void setMaxPages(int maxPages) {
        this.maxPages = maxPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}