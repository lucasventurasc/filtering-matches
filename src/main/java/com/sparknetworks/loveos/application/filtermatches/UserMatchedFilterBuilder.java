package com.sparknetworks.loveos.application.filtermatches;

import com.sparknetworks.loveos.application.filtermatches.filter.*;
import com.sparknetworks.loveos.domain.Range;
import com.sparknetworks.loveos.domain.UserMatchedFilter;

import java.util.ArrayList;
import java.util.List;

public class UserMatchedFilterBuilder {

    private List<UserMatchedFilter> userMatchedFilters;

    public UserMatchedFilterBuilder() {
        this.userMatchedFilters = new ArrayList<>();
    }

    public Filters build() {
        return new Filters(userMatchedFilters);
    }

    public UserMatchedFilterBuilder shouldHavePhoto(boolean value) {
        if (value) this.userMatchedFilters.add(new HasPhotoFilter());
        return this;
    }

    public UserMatchedFilterBuilder shouldBeFavorited(boolean value) {
        if (value) this.userMatchedFilters.add(new FavoritedFilter());
        return this;
    }

    public UserMatchedFilterBuilder shouldHaveExchangedContacts(boolean value) {
        if (value) this.userMatchedFilters.add(new ContactsExchangedFilter());
        return this;
    }

    public UserMatchedFilterBuilder shouldHaveCompatibilityScoreRange(Range<Double> compatibilityScoreRange) {
        if (compatibilityScoreRange != null) this.userMatchedFilters.add(new CompatibilityScoreFilter(compatibilityScoreRange));
        return this;
    }

    public UserMatchedFilterBuilder shouldBeAgedBetween(Range<Integer> ageRange) {
        if (ageRange != null) this.userMatchedFilters.add(new AgeFilter(ageRange));
        return this;
    }

    public UserMatchedFilterBuilder shouldHasAHeightBetween(Range<Integer> heightInCmRange) {
        if (heightInCmRange != null) this.userMatchedFilters.add(new HeightFilter(heightInCmRange));
        return this;
    }

    public UserMatchedFilterBuilder distanceInKmFromRelatedProfileCityShouldBeLessThan(Double distanceRadiusInKm) {
        if (distanceRadiusInKm != null) this.userMatchedFilters.add(new DistanceRadiusFilter(distanceRadiusInKm));
        return this;
    }
}
