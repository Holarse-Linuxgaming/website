package de.holarse.web.controller.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.holarse.backend.types.SearchScopeType;
import de.holarse.backend.types.SearchSortType;

public class SearchForm {

    /** Query */
    private String q = "";

    /** Search Scope (articles, news and/or threads) */
    private List<SearchScopeType> s = new ArrayList<>();

    /** Selected Tags */
    private List<String> t = new ArrayList<>();

    /** Tag to be toggled */
    private String a = "";

    /** Sort */
    private SearchSortType sort = SearchSortType.rank;

    public String getQ() {
        return q;
    }

    public void setQ(final String q) {
        this.q = q;
    }

    public List<String> getT() {
        return t;
    }

    public void setT(final List<String> t) {
        this.t = t;
    }

    public SearchSortType getSort() {
        return sort;
    }

    public void setSort(final SearchSortType sort) {
        this.sort = sort;
    }


    public List<SearchScopeType> getS() {
        return s;
    }

    public void setS(final List<SearchScopeType> s) {
        this.s = s;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public static SearchForm create() {
        final SearchForm searchForm = new SearchForm();
        searchForm.setS(Arrays.asList(SearchScopeType.values())); // Scopes
        searchForm.setSort(SearchSortType.rank);
        return searchForm;
    }

    @Override
    public String toString() {
        return "SearchForm [q=" + q + ", s=" + s + ", t=" + t + ", a=" + a + ", sort=" + sort + "]";
    }

    
}
