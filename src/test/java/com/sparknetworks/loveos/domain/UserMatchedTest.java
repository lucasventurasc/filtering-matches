package com.sparknetworks.loveos.domain;

import com.sparknetworks.loveos.application.filtermatches.filter.DistanceRadiusFilter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class UserMatchedTest {

    @Test
    void should_return_false_to_has_photo_when_user_matched_has_no_photo() {
        UserMatched userMatched = UserMatched.createUserMatchedRelatedToProfile(new Profile("christian44"));
       userMatched.setMainPhoto(null);
       assertThat(userMatched.hasPhoto()).isFalse();
    }

    @Test
    void should_return_true_to_has_photo_when_user_matched_has_photo() {
        UserMatched userMatched = UserMatched.createUserMatchedRelatedToProfile(new Profile("christian44"));
        userMatched.setMainPhoto(URI.create("file://photoinsomeplace"));
        assertThat(userMatched.hasPhoto()).isTrue();
    }

    @Test
    void should_return_true_when_distance_between_cities_is_exactly_same_of_the_range() {
        City manchester = Mockito.mock(City.class);
        City liverpool = new City("Liverpool", new Coordinates(53.41058, -2.97794));

        Profile profile = new Profile("christian44");
        profile.setCity(manchester);
        UserMatched userMatchedInLiverpool = UserMatched.createUserMatchedRelatedToProfile(profile);
        userMatchedInLiverpool.setCity(liverpool);

        double distanceRadiusInKmForFilter = 49.67502626878007; //distance from manchester to liverpool
        when(manchester.distanceInKmTo(liverpool)).thenReturn(distanceRadiusInKmForFilter);

        assertThat(userMatchedInLiverpool.distanceFromProfileCityIsWithinRadius(distanceRadiusInKmForFilter)).isTrue();
    }
}