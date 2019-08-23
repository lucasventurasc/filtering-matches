package com.sparknetworks.loveos.domain;

public interface ProfileRepository {

    Profile findProfileWithMatchesById(String profileId);
}
