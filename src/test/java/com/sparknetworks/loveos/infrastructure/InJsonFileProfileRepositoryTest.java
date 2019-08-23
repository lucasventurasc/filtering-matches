package com.sparknetworks.loveos.infrastructure;

import com.sparknetworks.loveos.application.filtermatches.Filters;
import com.sparknetworks.loveos.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InJsonFileProfileRepositoryTest {

	@Mock
	private MatchesRepository matchesRepository;

	@Test
	void should_throw_profile_not_found_when_user_id_it_not_exists() {
		String path = getClass().getClassLoader().getResource("integration-test/profiles/").getPath();

		InJsonFileProfileRepository inJsonFileProfileRepository = new InJsonFileProfileRepository(new File(path), matchesRepository);

		assertThrows(ProfileNotFoundException.class, () -> {
			inJsonFileProfileRepository.findProfileWithMatchesById("NonExistingUSer");
		});
	}

	@Test
	void should_retrieve_correct_profile_info_from_repository() {
		String path = getClass().getClassLoader().getResource("integration-test/profiles/").getPath();

		InJsonFileProfileRepository inJsonFileProfileRepository = new InJsonFileProfileRepository(new File(path), matchesRepository);

		Profile profile = inJsonFileProfileRepository.findProfileWithMatchesById("Caroline");

		assertThat(profile.getId()).isEqualTo("Caroline");
		assertThat(profile.getCity().getName()).isEqualTo("Leeds");
		assertThat(profile.getCity().getLat()).isEqualTo(53.801277);
		assertThat(profile.getCity().getLon()).isEqualTo(-1.548567);
	}

	@Test
	void should_load_users_matched_from_matches_repository() {
		String path = getClass().getClassLoader().getResource("integration-test/profiles/").getPath();
		InJsonFileProfileRepository inJsonFileProfileRepository = new InJsonFileProfileRepository(new File(path), matchesRepository);

		Profile profile = new Profile("Caroline");
		List<UserMatched> usersMatched = new ArrayList<>();
		usersMatched.add(new UserMatchedBuilder().withName("Katherine").build());

		when(matchesRepository.findUsersMatchedByProfile(argThat(arg -> profile.getId().equals(arg.getId())))).thenReturn(usersMatched);

		Profile returnedProfile = inJsonFileProfileRepository.findProfileWithMatchesById("Caroline");
		List<UserMatched> returnedUsersMatched = getUsersMatched(returnedProfile);

		verify(matchesRepository).findUsersMatchedByProfile(returnedProfile);
		assertThat(returnedUsersMatched).hasSize(1);
		assertThat(returnedUsersMatched.get(0).getDisplayName()).isEqualTo("Katherine");
	}

	private List<UserMatched> getUsersMatched(Profile returnedProfile) {
		return returnedProfile.getUsersMatched(new Filters(new ArrayList<>()));
	}

}