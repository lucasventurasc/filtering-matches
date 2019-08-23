package com.sparknetworks.loveos.domain;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CityTest {

    @Test
    void should_calculate_distance_between_two_cities() {
        City bournemouth = new City("Bournemouth", new Coordinates(50.720806, -1.904755));
        City plymouth = new City("Plymouth", new Coordinates(50.376289, -4.143841));

        assertThat(bournemouth.distanceInKmTo(plymouth)).isCloseTo(162.8, Offset.offset(0.02));
    }
}