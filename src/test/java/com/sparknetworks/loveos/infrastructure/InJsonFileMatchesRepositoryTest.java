package com.sparknetworks.loveos.infrastructure;

import com.sparknetworks.loveos.domain.Profile;
import com.sparknetworks.loveos.domain.UserMatched;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class InJsonFileMatchesRepositoryTest {

	@Test
	void should_return_empty_when_matches_file_of_profile_does_not_exist() {
		String path = getClass().getClassLoader().getResource("integration-test/matches/").getPath();

		InJsonFileMatchesRepository inJsonFileMatchesRepository = new InJsonFileMatchesRepository(new File(path));

		assertThat(inJsonFileMatchesRepository.findUsersMatchedByProfile(new Profile("nonExistingProifile"))).isEmpty();
	}

	@Test
	void should_read_matches_file_that_finishes_with_the_profile_id() {
		String path = getClass().getClassLoader().getResource("integration-test/matches/").getPath();

		InJsonFileMatchesRepository inJsonFileMatchesRepository = new InJsonFileMatchesRepository(new File(path));

		assertThat(inJsonFileMatchesRepository.findUsersMatchedByProfile(new Profile("tonyxpto23"))).hasSize(25);
		assertThat(inJsonFileMatchesRepository.findUsersMatchedByProfile(new Profile("chris"))).hasSize(1);
	}

	@Test
	void should_read_all_matches_data_into_correct_user_matched_domain_fields() {
		String path = getClass().getClassLoader().getResource("integration-test/matches/").getPath();

		InJsonFileMatchesRepository inJsonFileMatchesRepository = new InJsonFileMatchesRepository(new File(path));

		List<UserMatched> chrisMatches = inJsonFileMatchesRepository.findUsersMatchedByProfile(new Profile("chris"));

		assertThat(chrisMatches).hasSize(1);
		UserMatched onlyChrisUserMatched = chrisMatches.get(0);
		assertThat(onlyChrisUserMatched.getDisplayName()).isEqualTo("Caroline");
		assertThat(onlyChrisUserMatched.getAge()).isEqualTo(41);
		assertThat(onlyChrisUserMatched.getJobTitle()).isEqualTo("Corporate Lawyer");
		assertThat(onlyChrisUserMatched.getHeightInCm()).isEqualTo(153);
		assertThat(onlyChrisUserMatched.getCityName()).isEqualTo("Leeds");
		assertThat(onlyChrisUserMatched.getCityLatitude()).isEqualTo(53.801277);
		assertThat(onlyChrisUserMatched.getCityLongitude()).isEqualTo(-1.548567);
		assertThat(onlyChrisUserMatched.getMainPhotoUrl().toString()).isEqualTo("http://thecatapi.com/api/images/get?format=src&type=gif");
		assertThat(onlyChrisUserMatched.getCompatibilityScore()).isEqualTo(0.76);
		assertThat(onlyChrisUserMatched.getContactsExchanged()).isEqualTo(2);
		assertThat(onlyChrisUserMatched.isFavorited()).isTrue();
		assertThat(onlyChrisUserMatched.getReligion()).isEqualTo("Atheist");
	}
}