package com.sparknetworks.loveos.application.filtermatches;

import com.sparknetworks.loveos.domain.UserMatchedFilter;

import java.util.List;

public class Filters {

    private final List<UserMatchedFilter> userMatchedFilters;

    public Filters(List<UserMatchedFilter> userMatchedFilters) {
        this.userMatchedFilters = userMatchedFilters;
    }

    public List<UserMatchedFilter> getFilters() {
        return userMatchedFilters;
    }
}