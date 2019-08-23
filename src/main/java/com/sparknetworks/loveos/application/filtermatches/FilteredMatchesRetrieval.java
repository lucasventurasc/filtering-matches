package com.sparknetworks.loveos.application.filtermatches;

import com.sparknetworks.loveos.domain.Profile;
import com.sparknetworks.loveos.domain.ProfileRepository;
import com.sparknetworks.loveos.domain.UserMatched;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class FilteredMatchesRetrieval {

    private ProfileRepository profileRepository;

    public FilteredMatchesRetrieval(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<UserMatchedDTO> retrieve(String profileId, Filters filters) {
        Profile profile = profileRepository.findProfileWithMatchesById(profileId);

        List<UserMatched> usersMatched = profile.getUsersMatched(filters);

        return usersMatched.stream().map(this::toUserMatchedDTO).collect(toList());
    }

    private UserMatchedDTO toUserMatchedDTO(UserMatched userMatched) {
        UserMatchedDTO userMatchedDTO = new UserMatchedDTO();
        userMatchedDTO.setDisplayName(userMatched.getDisplayName());
        userMatchedDTO.setMainPhoto(userMatched.getMainPhotoUrl() != null ? userMatched.getMainPhotoUrl().toString() : null);
        userMatchedDTO.setAge(userMatched.getAge());
        userMatchedDTO.setContactsExchanged(userMatched.getContactsExchanged());
        userMatchedDTO.setJobTitle(userMatched.getJobTitle());
        userMatchedDTO.setCompatibilityScore(userMatched.getCompatibilityScore());
        userMatchedDTO.setHeightInCm(userMatched.getHeightInCm());
        userMatchedDTO.setCity(new CityDTO(userMatched.getCityName(), userMatched.getCityLatitude(), userMatched.getCityLongitude()));
        userMatchedDTO.setReligion(userMatched.getReligion());
        userMatchedDTO.setFavourite(userMatched.isFavorited());
        return userMatchedDTO;
    }
}
