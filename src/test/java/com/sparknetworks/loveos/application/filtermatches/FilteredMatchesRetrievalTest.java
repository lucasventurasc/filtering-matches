package com.sparknetworks.loveos.application.filtermatches;

import com.sparknetworks.loveos.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilteredMatchesRetrievalTest {

    private static final String USER_FILTERING_MATCHES = "tony249";
    private static final URI RANDOM_URI_PHOTO = URI.create("http://thecatapi.com/api/images/get?format=src&type=gif");

    private static final City LEEDS_CITY = new City("Leeds", new Coordinates(53.801277, -1.548567));
    private static final City LONDON_CITY = new City("London", new Coordinates(51.509865, -0.118092));
    private static final City OXFORD_CITY = new City("Oxford", new Coordinates(51.75202, -1.257677));

    private List<UserMatched> usersMatchedOfProfile;

    @Mock
    private ProfileRepository profileRepository;

    private FilteredMatchesRetrieval filteredMatchesRetrieval;

    @BeforeEach
    void initialise() {
        Profile profile = new Profile("tony339");
        this.usersMatchedOfProfile = new ArrayList<>();
        profile.setUsersMatched(usersMatchedOfProfile);
        when(profileRepository.findProfileWithMatchesById(USER_FILTERING_MATCHES)).thenReturn(profile);
        this.filteredMatchesRetrieval = new FilteredMatchesRetrieval(profileRepository);
    }

    @Test
    void should_retrieve_user_matched_fields_empty_when_data_not_exists() {
        this.usersMatchedOfProfile.addAll(singletonList(
                userMatchedBuilder()
                        .withName("Carol Danvers")
                        .withPhoto(null)
                        .age(null)
                        .compatibilityScore(null)
                        .city(null)
                        .heightInCm(null)
                        .contactsExchanged(null)
                        .build())
        );

        List<UserMatchedDTO> filteredMatches = filteredMatchesRetrieval.retrieve(USER_FILTERING_MATCHES, filterBuilder()
                .shouldHavePhoto(false)
                .shouldBeFavorited(false)
                .build());

        UserMatchedDTO carol = getUserMatchedWithName("Carol Danvers", filteredMatches);
        assertThat(carol.getDisplayName()).isEqualTo("Carol Danvers");
        assertThat(carol.getMainPhoto()).isNull();
        assertThat(carol.getContactsExchanged()).isNull();
        assertThat(carol.getJobTitle()).isNull();
        assertThat(carol.getAge()).isNull();
        assertThat(carol.getCompatibilityScore()).isNull();
        assertThat(carol.getHeightInCm()).isNull();
        assertThat(carol.getCity().getName()).isNull();
        assertThat(carol.getCity().getLat()).isNull();
        assertThat(carol.getCity().getLon()).isNull();
        assertThat(carol.getReligion()).isNull();
    }

    @Test
    void should_retrieve_user_matched_fields_filled() {
        this.usersMatchedOfProfile.addAll(singletonList(
                userMatchedBuilder()
                        .withName("Natasha Romanoff")
                        .withPhoto(URI.create("http://randomphoto.com"))
                        .age(34)
                        .jobTitle("Spy")
                        .contactsExchanged(7)
                        .favorited()
                        .heightInCm(165)
                        .compatibilityScore(0.77)
                        .city(LONDON_CITY)
                        .religion("Atheist")
                        .build())
        );

        List<UserMatchedDTO> filteredMatches = filteredMatchesRetrieval.retrieve(USER_FILTERING_MATCHES, filterBuilder()
                .shouldHavePhoto(false)
                .shouldBeFavorited(false)
                .build());

        UserMatchedDTO natasha = getUserMatchedWithName("Natasha Romanoff", filteredMatches);
        assertThat(natasha.getDisplayName()).isEqualTo("Natasha Romanoff");
        assertThat(natasha.getMainPhoto()).isEqualTo("http://randomphoto.com");
        assertThat(natasha.getContactsExchanged()).isEqualTo(7);
        assertThat(natasha.getJobTitle()).isEqualTo("Spy");
        assertThat(natasha.isFavourite()).isEqualTo(true);
        assertThat(natasha.getAge()).isEqualTo(34);
        assertThat(natasha.getCompatibilityScore()).isEqualTo(0.77);
        assertThat(natasha.getHeightInCm()).isEqualTo(165);
        assertThat(natasha.getCity().getName()).isEqualTo("London");
        assertThat(natasha.getCity().getLat()).isEqualTo(51.509865);
        assertThat(natasha.getCity().getLon()).isEqualTo(-0.118092);
        assertThat(natasha.getReligion()).isEqualTo("Atheist");
    }

    @Test
    void should_filter_matches_that_have_photo() {
        this.usersMatchedOfProfile.addAll(asList(
                userMatchedBuilder().withName("Natasha Romanoff").withPhoto(RANDOM_URI_PHOTO).build(),
                userMatchedBuilder().withName("Carol Danvers").withPhoto(RANDOM_URI_PHOTO).build(),
                userMatchedBuilder().withName("Wanda Maximoff").build())
        );

        List<UserMatchedDTO> filteredMatches = filteredMatchesRetrieval.retrieve(USER_FILTERING_MATCHES, filterBuilder()
                .shouldHavePhoto(true)
                .build());

        assertFilteredMatchesContains(filteredMatches, "Natasha Romanoff", "Carol Danvers");
    }

    @Test
    void should_filter_matches_that_has_photo_and_that_has_no_photo_when_should_have_photo_option_is_false() {
        this.usersMatchedOfProfile.addAll(asList(
                userMatchedBuilder().withName("Natasha Romanoff").withPhoto(RANDOM_URI_PHOTO).build(),
                userMatchedBuilder().withName("Carol Danvers").withPhoto(RANDOM_URI_PHOTO).build(),
                userMatchedBuilder().withName("Wanda Maximoff").build())
        );

        List<UserMatchedDTO> filteredMatches = filteredMatchesRetrieval.retrieve(USER_FILTERING_MATCHES, filterBuilder()
                .shouldHavePhoto(false)
                .build());

        assertFilteredMatchesContains(filteredMatches, "Natasha Romanoff", "Wanda Maximoff", "Carol Danvers");
    }

    @Test
    void should_filter_favorite_matches() {
        this.usersMatchedOfProfile.addAll(asList(
                userMatchedBuilder().withName("Natasha Romanoff").favorited().build(),
                userMatchedBuilder().withName("Carol Danvers").build(),
                userMatchedBuilder().withName("Wanda Maximoff").favorited().build())
        );

        List<UserMatchedDTO> filteredMatches = filteredMatchesRetrieval.retrieve(USER_FILTERING_MATCHES, filterBuilder()
                .shouldBeFavorited(true)
                .build());

        assertFilteredMatchesContains(filteredMatches, "Natasha Romanoff", "Wanda Maximoff");
    }

    @Test
    void should_filter_matches_that_are_favorite_and_that_are_no_favorite_when_should_be_favorite_is_option_is_false() {
        this.usersMatchedOfProfile.addAll(asList(
                userMatchedBuilder().withName("Natasha Romanoff").favorited().build(),
                userMatchedBuilder().withName("Carol Danvers").build(),
                userMatchedBuilder().withName("Wanda Maximoff").favorited().build())
        );

        List<UserMatchedDTO> filteredMatches = filteredMatchesRetrieval.retrieve(USER_FILTERING_MATCHES, filterBuilder()
                .shouldBeFavorited(false)
                .build());

        assertFilteredMatchesContains(filteredMatches, "Natasha Romanoff", "Carol Danvers", "Wanda Maximoff");
    }

    @Test
    void should_filter_matches_that_have_one_or_more_contacts_exchanged() {
        this.usersMatchedOfProfile.addAll(asList(
                userMatchedBuilder().withName("Natasha Romanoff").contactsExchanged(1).build(),
                userMatchedBuilder().withName("Carol Danvers").contactsExchanged(0).build(),
                userMatchedBuilder().withName("Wanda Maximoff").contactsExchanged(2).build())
        );

        List<UserMatchedDTO> filteredMatches = filteredMatchesRetrieval.retrieve(USER_FILTERING_MATCHES, filterBuilder()
                .shouldHaveExchangedContacts(true)
                .build());

        assertFilteredMatchesContains(filteredMatches, "Natasha Romanoff", "Wanda Maximoff");

    }

    @Test
    void should_filter_matches_that_has_exchanged_contacts_and_has_not_when_contacts_exchange_option_is_false() {
        this.usersMatchedOfProfile.addAll(asList(
                userMatchedBuilder().withName("Natasha Romanoff").contactsExchanged(1).build(),
                userMatchedBuilder().withName("Carol Danvers").contactsExchanged(0).build(),
                userMatchedBuilder().withName("Wanda Maximoff").contactsExchanged(2).build())
        );

        List<UserMatchedDTO> filteredMatches = filteredMatchesRetrieval.retrieve(USER_FILTERING_MATCHES, filterBuilder()
                .shouldHaveExchangedContacts(false)
                .build());

        assertFilteredMatchesContains(filteredMatches, "Natasha Romanoff", "Carol Danvers", "Wanda Maximoff");
    }

    @Test
    void should_filter_matches_that_are_within_range_of_compatibility_score() {
        this.usersMatchedOfProfile.addAll(asList(
                userMatchedBuilder().withName("Natasha Romanoff").compatibilityScore(0.20).build(),
                userMatchedBuilder().withName("Carol Danvers").compatibilityScore(0.99).build(),
                userMatchedBuilder().withName("Wanda Maximoff").compatibilityScore(0.75).build())
        );

        List<UserMatchedDTO> filteredMatches = filteredMatchesRetrieval.retrieve(USER_FILTERING_MATCHES, filterBuilder()
                .shouldHaveCompatibilityScoreRange(Range.of(0.21, 0.75))
                .build());

        assertFilteredMatchesContains(filteredMatches, "Wanda Maximoff");
    }

    @Test
    void should_filter_matches_that_are_within_range_of_compatibility_score_edge_values() {
        this.usersMatchedOfProfile.addAll(asList(
                userMatchedBuilder().withName("Natasha Romanoff").compatibilityScore(0.20).build(),
                userMatchedBuilder().withName("Carol Danvers").compatibilityScore(0.99).build(),
                userMatchedBuilder().withName("Wanda Maximoff").compatibilityScore(0.75).build())
        );

        List<UserMatchedDTO> filteredMatches = filteredMatchesRetrieval.retrieve(USER_FILTERING_MATCHES, filterBuilder()
                .shouldHaveCompatibilityScoreRange(Range.of(0.21, 0.74))
                .build());

        assertThat(filteredMatches).isEmpty();
    }

    @Test
    void should_filter_matches_by_its_age() {
        this.usersMatchedOfProfile.addAll(asList(
                userMatchedBuilder().withName("Natasha Romanoff").age(34).build(),
                userMatchedBuilder().withName("Carol Danvers").age(29).build(),
                userMatchedBuilder().withName("Wanda Maximoff").age(28).build())
        );

        List<UserMatchedDTO> filteredMatches = filteredMatchesRetrieval.retrieve(USER_FILTERING_MATCHES, filterBuilder()
                .shouldBeAgedBetween(Range.of(27, 33))
                .build());

        assertFilteredMatchesContains(filteredMatches, "Carol Danvers", "Wanda Maximoff");
    }

    @Test
    void should_filter_matches_by_its_age_edge_range_included() {
        this.usersMatchedOfProfile.addAll(asList(
                userMatchedBuilder().withName("Natasha Romanoff").age(34).build(),
                userMatchedBuilder().withName("Carol Danvers").age(29).build(),
                userMatchedBuilder().withName("Wanda Maximoff").age(28).build())
        );

        List<UserMatchedDTO> filteredMatches = filteredMatchesRetrieval.retrieve(USER_FILTERING_MATCHES, filterBuilder()
                .shouldBeAgedBetween(Range.of(34, 34))
                .build());

        assertFilteredMatchesContains(filteredMatches, "Natasha Romanoff");
    }

    @Test
    void should_filter_matches_by_its_height() {
        this.usersMatchedOfProfile.addAll(asList(
                userMatchedBuilder().withName("Natasha Romanoff").heightInCm(160).build(),
                userMatchedBuilder().withName("Carol Danvers").heightInCm(170).build(),
                userMatchedBuilder().withName("Wanda Maximoff").heightInCm(168).build())
        );

        List<UserMatchedDTO> filteredMatches = filteredMatchesRetrieval.retrieve(USER_FILTERING_MATCHES, filterBuilder()
                .shouldHasAHeightBetween(Range.of(160, 169))
                .build());

        assertFilteredMatchesContains(filteredMatches, "Wanda Maximoff", "Natasha Romanoff");
    }

    @Test
    void should_filter_matches_by_its_height_range_included() {
        this.usersMatchedOfProfile.addAll(asList(
                userMatchedBuilder().withName("Natasha Romanoff").heightInCm(160).build(),
                userMatchedBuilder().withName("Carol Danvers").heightInCm(170).build(),
                userMatchedBuilder().withName("Wanda Maximoff").heightInCm(168).build())
        );

        List<UserMatchedDTO> filteredMatches = filteredMatchesRetrieval.retrieve(USER_FILTERING_MATCHES, filterBuilder()
                .shouldHasAHeightBetween(Range.of(168, 170))
                .build());

        assertFilteredMatchesContains(filteredMatches, "Carol Danvers", "Wanda Maximoff");
    }

    @Test
    void should_filter_matches_by_max_distance_from_the_address_of_the_profile() {
        Profile profile = new Profile("tony339");
        profile.setCity(LONDON_CITY);

        UserMatchedBuilder userMatchedBuilder = userMatchedBuilder();
        userMatchedBuilder.relatedProfile(profile);


        this.usersMatchedOfProfile.addAll(asList(
                userMatchedBuilder.withName("Natasha Romanoff").city(LONDON_CITY).build(),
                userMatchedBuilder.withName("Carol Danvers").city(OXFORD_CITY).build(),
                userMatchedBuilder.withName("Wanda Maximoff").city(LEEDS_CITY).build())
        );


        profile.setUsersMatched(usersMatchedOfProfile);
        when(profileRepository.findProfileWithMatchesById(USER_FILTERING_MATCHES)).thenReturn(profile);

        List<UserMatchedDTO> filteredMatches = filteredMatchesRetrieval.retrieve(USER_FILTERING_MATCHES, filterBuilder()
                .distanceInKmFromRelatedProfileCityShouldBeLessThan(272d)
                .build());

        assertFilteredMatchesContains(filteredMatches, "Carol Danvers", "Natasha Romanoff");
    }

    private void assertFilteredMatchesContains(List<UserMatchedDTO> filteredMatches, String... names) {
        assertThat(filteredMatches).extracting(UserMatchedDTO::getDisplayName).containsExactlyInAnyOrder(names);
    }

    private UserMatchedFilterBuilder filterBuilder() {
        return new UserMatchedFilterBuilder();
    }

    private UserMatchedBuilder userMatchedBuilder() {
        return new UserMatchedBuilder();
    }

    private UserMatchedDTO getUserMatchedWithName(String name, List<UserMatchedDTO> filteredMatches) {
        return filteredMatches.stream().filter(userMatchedDTO -> userMatchedDTO.getDisplayName().equals(name)).findFirst().orElseThrow(AssertionError::new);
    }

}
