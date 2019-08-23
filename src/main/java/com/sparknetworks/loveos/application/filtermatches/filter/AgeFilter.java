package com.sparknetworks.loveos.application.filtermatches.filter;

import com.sparknetworks.loveos.domain.Range;
import com.sparknetworks.loveos.domain.UserMatched;
import com.sparknetworks.loveos.domain.UserMatchedFilter;

public class AgeFilter implements UserMatchedFilter {

    private Range<Integer> ageRange;

    public AgeFilter(Range<Integer> ageRange) {
        this.ageRange = ageRange;
    }


    @Override
    public boolean isEligible(UserMatched userMatched) {
        return userMatched.isAgedBetween(ageRange);
    }
}
