package com.sparknetworks.loveos.infrastructure;

import com.sparknetworks.loveos.application.filtermatches.CityDTO;
import com.sparknetworks.loveos.application.filtermatches.UserMatchedDTO;
import com.sparknetworks.loveos.domain.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class InJsonFileMatchesRepository extends AbstractInJsonFileRepository<List<UserMatchedDTO>> implements MatchesRepository {

	public InJsonFileMatchesRepository(File filePath) {
		super(filePath);
	}

	@Override
	public List<UserMatched> findUsersMatchedByProfile(Profile profile) {
		try {
			List<UserMatchedDTO> usersMatched = readJsonData("matches_" + profile.getId() + ".json");
			return usersMatched.stream().map(dto -> toUserMatched(dto, profile)).collect(toList());
		} catch (FileNotFoundException e) {
			return new ArrayList<>();
		}
	}

	private UserMatched toUserMatched(UserMatchedDTO userMatchedDTO, Profile relatedProfile) {
		UserMatched userMatched = UserMatched.createUserMatchedRelatedToProfile(relatedProfile);
		userMatched.setDisplayName(userMatchedDTO.getDisplayName());
		userMatched.setMainPhoto(userMatchedDTO.getMainPhoto() == null ? null : URI.create(userMatchedDTO.getMainPhoto()));
		userMatched.setContactsExchanged(userMatchedDTO.getContactsExchanged());
		userMatched.setJobTitle(userMatchedDTO.getJobTitle());

		if (userMatchedDTO.isFavourite()) userMatched.setFavorited();

		userMatched.setAge(userMatchedDTO.getAge());
		userMatched.setCompatibilityScore(userMatchedDTO.getCompatibilityScore());
		userMatched.setHeightInCm(userMatchedDTO.getHeightInCm());
		userMatched.setCity(toCity(userMatchedDTO.getCity()));
		userMatched.setReligion(userMatchedDTO.getReligion());

		return userMatched;
	}

	private City toCity(CityDTO city) {
		return new City(city.getName(), new Coordinates(city.getLat(), city.getLon()));
	}

}
