package com.sparknetworks.loveos.infrastructure;

import com.sparknetworks.loveos.domain.MatchesRepository;
import com.sparknetworks.loveos.domain.Profile;
import com.sparknetworks.loveos.domain.ProfileNotFoundException;
import com.sparknetworks.loveos.domain.ProfileRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InJsonFileProfileRepository extends AbstractInJsonFileRepository<Profile> implements ProfileRepository {

    private MatchesRepository matchesRepository;

    public InJsonFileProfileRepository(File filesPath, MatchesRepository matchesRepository) {
        super(filesPath);
        this.matchesRepository = matchesRepository;
    }

    @Override
    public Profile findProfileWithMatchesById(String profileId) {
        try {
            Profile profile = readJsonData("profile_" + profileId + ".json");
            profile.setUsersMatched(matchesRepository.findUsersMatchedByProfile(profile));
            return profile;
        } catch (FileNotFoundException e) {
            throw new ProfileNotFoundException();
        }
    }
}
