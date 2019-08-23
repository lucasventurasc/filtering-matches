package com.sparknetworks.loveos.domain;

import java.util.List;

public interface MatchesRepository {

	List<UserMatched> findUsersMatchedByProfile(Profile profile);
}
