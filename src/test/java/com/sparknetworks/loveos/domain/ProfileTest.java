package com.sparknetworks.loveos.domain;

import com.sparknetworks.loveos.application.filtermatches.Filters;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

class ProfileTest {

    @Test
    void should_filter_user_matched_with_given_filters() {
        Profile profile = new Profile("will66");

        List<UserMatched> allUsersMatched = Arrays.asList(
                new UserMatchedBuilder().withName("Luiza").build(),
                new UserMatchedBuilder().withName("Wannessa").build(),
                new UserMatchedBuilder().withName("Leticia").build(),
                new UserMatchedBuilder().withName("Maria").build()
        );
        profile.setUsersMatched(allUsersMatched);

        UserMatchedFilter usersThatStartsWithL = userMatched -> userMatched.getDisplayName().startsWith("L");

        List<UserMatched> usersMatched = profile.getUsersMatched(new Filters(singletonList(usersThatStartsWithL)));

        UserMatched luiza = allUsersMatched.get(0);
        UserMatched leticia = allUsersMatched.get(2);
        assertThat(usersMatched).containsExactlyInAnyOrder(luiza, leticia);
    }
}