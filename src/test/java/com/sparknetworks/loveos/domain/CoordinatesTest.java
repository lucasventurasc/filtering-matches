package com.sparknetworks.loveos.domain;

import org.assertj.core.data.Offset;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoordinatesTest {

    @Test
    void should_calculate_haversine_coordinates_between_two_coordinates() {
        Coordinates swindonCoordinates = new Coordinates(51.568535, -1.772232);
        Coordinates oxfordCoordinates = new Coordinates(51.752022, -1.257677);

        double distanceInKm = swindonCoordinates.distanceInKmTo(oxfordCoordinates);
        assertThat(distanceInKm).isCloseTo(40.950177593859756, Offset.offset(0.0001));
    }
}