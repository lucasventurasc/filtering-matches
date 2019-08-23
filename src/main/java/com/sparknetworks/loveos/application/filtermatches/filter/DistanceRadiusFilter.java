package com.sparknetworks.loveos.application.filtermatches.filter;

import com.sparknetworks.loveos.domain.UserMatched;
import com.sparknetworks.loveos.domain.UserMatchedFilter;

public class DistanceRadiusFilter implements UserMatchedFilter {

    private double distanceRadiusToFilterInKm;

    public DistanceRadiusFilter(double distanceRadiusToFilterInKm) {
        this.distanceRadiusToFilterInKm = distanceRadiusToFilterInKm;
    }

    @Override
    public boolean isEligible(UserMatched userMatched) {
        return userMatched.distanceFromProfileCityIsWithinRadius(distanceRadiusToFilterInKm);
    }

}
