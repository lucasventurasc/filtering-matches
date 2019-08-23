package com.sparknetworks.loveos.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QueryFilterTest {

    @Test
    void should_create_compatibility_score_range_correctly() {
        QueryFilter queryFilter = new QueryFilter();
        queryFilter.setCompatibilityScoreRangeFrom(20d);
        queryFilter.setCompatibilityScoreRangeTo(22d);

        assertThat(queryFilter.getCompatibilityScoreRange().isWithinRange(20d)).isTrue();
        assertThat(queryFilter.getCompatibilityScoreRange().isWithinRange(21d)).isTrue();
        assertThat(queryFilter.getCompatibilityScoreRange().isWithinRange(22d)).isTrue();
    }

    @Test
    void should_create_age_range_correctly() {
        QueryFilter queryFilter = new QueryFilter();
        queryFilter.setAgeRangeFrom(18);
        queryFilter.setAgeRangeTo(20);

        assertThat(queryFilter.getAgeRange().isWithinRange(18)).isTrue();
        assertThat(queryFilter.getAgeRange().isWithinRange(19)).isTrue();
        assertThat(queryFilter.getAgeRange().isWithinRange(20)).isTrue();
    }

    @Test
    void should_create_height_range_correctly() {
        QueryFilter queryFilter = new QueryFilter();
        queryFilter.setHeightRangeFrom(165);
        queryFilter.setHeightRangeTo(167);

        assertThat(queryFilter.getHeightRange().isWithinRange(165)).isTrue();
        assertThat(queryFilter.getHeightRange().isWithinRange(166)).isTrue();
        assertThat(queryFilter.getHeightRange().isWithinRange(167)).isTrue();
    }

    @Test
    void should_return_null_for_compatibility_score_range_when_from_or_to_are_null() {
        QueryFilter queryFilter = new QueryFilter();
        queryFilter.setCompatibilityScoreRangeFrom(null);
        queryFilter.setCompatibilityScoreRangeTo(22d);

        assertThat(queryFilter.getCompatibilityScoreRange()).isNull();

        queryFilter.setCompatibilityScoreRangeFrom(20d);
        queryFilter.setCompatibilityScoreRangeTo(null);

        assertThat(queryFilter.getCompatibilityScoreRange()).isNull();
    }

    @Test
    void should_return_null_for_age_range_when_from_or_to_are_null() {
        QueryFilter queryFilter = new QueryFilter();
        queryFilter.setAgeRangeFrom(null);
        queryFilter.setAgeRangeTo(20);

        assertThat(queryFilter.getCompatibilityScoreRange()).isNull();

        queryFilter.setAgeRangeFrom(18);
        queryFilter.setAgeRangeTo(null);

        assertThat(queryFilter.getAgeRange()).isNull();
    }

    @Test
    void should_return_null_for_height_range_when_from_or_to_are_null() {
        QueryFilter queryFilter = new QueryFilter();
        queryFilter.setHeightRangeFrom(null);
        queryFilter.setHeightRangeTo(167);

        assertThat(queryFilter.getCompatibilityScoreRange()).isNull();

        queryFilter.setHeightRangeFrom(165);
        queryFilter.setHeightRangeTo(null);

        assertThat(queryFilter.getHeightRange()).isNull();
    }

    @Test
    void should_return_null_for_distance_in_km_when_distance_set_is_null() {
        QueryFilter queryFilter = new QueryFilter();
        queryFilter.setDistanceInKm(null);
        assertThat(queryFilter.getDistanceInKm()).isNull();
    }

}