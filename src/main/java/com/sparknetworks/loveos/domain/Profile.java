package com.sparknetworks.loveos.domain;

import com.sparknetworks.loveos.application.filtermatches.Filters;

import java.util.ArrayList;
import java.util.List;

public class Profile {

	private String id;
	private List<UserMatched> usersMatched;
	private City city;

	public Profile(String id) {
		this.id = id;
	}

	public void setUsersMatched(List<UserMatched> usersMatched) {
		this.usersMatched = usersMatched;
	}

	public List<UserMatched> getUsersMatched(Filters filters) {
		List<UserMatched> usersMatchedCopy = new ArrayList<>(usersMatched);

		usersMatchedCopy.removeIf(userMatched -> !isEligible(userMatched, filters.getFilters()));

		return usersMatchedCopy;
	}

	private boolean isEligible(UserMatched userMatched, List<UserMatchedFilter> filters) {
		for (UserMatchedFilter userMatchedFilter : filters) {
			if (!userMatchedFilter.isEligible(userMatched)) {
				return false;
			}
		}
		return true;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getId() {
		return id;
	}
}
