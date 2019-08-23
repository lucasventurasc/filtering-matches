package com.sparknetworks.loveos.application.filtermatches.filter;

import com.sparknetworks.loveos.domain.UserMatched;
import com.sparknetworks.loveos.domain.UserMatchedFilter;

public class HasPhotoFilter implements UserMatchedFilter {

    @Override
    public boolean isEligible(UserMatched userMatched) {
        return userMatched.hasPhoto();
    }
}
