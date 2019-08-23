package com.sparknetworks.loveos.application.filtermatches.filter;

import com.sparknetworks.loveos.domain.Range;
import com.sparknetworks.loveos.domain.UserMatched;
import com.sparknetworks.loveos.domain.UserMatchedFilter;

public class HeightFilter implements UserMatchedFilter {

    private Range<Integer> heightRange;

    public HeightFilter(Range<Integer> heightInCmRange) {
        this.heightRange = heightInCmRange;
    }

    @Override
    public boolean isEligible(UserMatched userMatched) {
        return userMatched.hasHeightBetween(heightRange);
    }
}
