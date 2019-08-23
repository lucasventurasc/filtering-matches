package com.sparknetworks.loveos.application.filtermatches.filter;

import com.sparknetworks.loveos.domain.Range;
import com.sparknetworks.loveos.domain.UserMatched;
import com.sparknetworks.loveos.domain.UserMatchedFilter;

public class CompatibilityScoreFilter implements UserMatchedFilter {

    private Range<Double> range;

    public CompatibilityScoreFilter(Range<Double> range) {
        this.range = range;
    }

    @Override
    public boolean isEligible(UserMatched userMatched) {
        return userMatched.hasCompatibilityScoreWithinRange(range);
    }
}
