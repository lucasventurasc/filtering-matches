package com.sparknetworks.loveos.domain;

import java.net.URI;

public class UserMatchedBuilder {

    private Profile relatedProfile;
    private URI photo;
    private boolean favorited;
    private String displayName;
    private Integer contactsExchanged;
    private Double compatibilityScore;
    private Integer age;
    private Integer heightInCm;
    private City city;
    private String religion;
    private String jobTitle;

    public UserMatched build() {
        UserMatched userMatched = UserMatched.createUserMatchedRelatedToProfile(relatedProfile);
        userMatched.setMainPhoto(photo);

        if (favorited) {
            userMatched.setFavorited();
        }

        userMatched.setDisplayName(displayName);
        userMatched.setJobTitle(jobTitle);
        userMatched.setContactsExchanged(contactsExchanged);
        userMatched.setCompatibilityScore(compatibilityScore);
        userMatched.setAge(age);
        userMatched.setHeightInCm(heightInCm);
        userMatched.setCity(city);
        userMatched.setReligion(religion);
        return userMatched;
    }

    public UserMatchedBuilder withPhoto(URI photoURI) {
        this.photo = photoURI;
        return this;
    }

    public UserMatchedBuilder favorited() {
        this.favorited = true;
        return this;
    }

    public UserMatchedBuilder withName(String name) {
        this.displayName = name;
        return this;
    }

    public UserMatchedBuilder contactsExchanged(Integer contactsExchanged) {
        this.contactsExchanged = contactsExchanged;
        return this;
    }

    public UserMatchedBuilder compatibilityScore(Double score) {
        this.compatibilityScore = score;
        return this;
    }

    public UserMatchedBuilder age(Integer age) {
        this.age = age;
        return this;
    }

    public UserMatchedBuilder heightInCm(Integer heightInCm) {
        this.heightInCm = heightInCm;
        return this;
    }

    public UserMatchedBuilder city(City city) {
        this.city = city;
        return this;
    }

    public UserMatchedBuilder relatedProfile(Profile profile) {
        this.relatedProfile = profile;
        return this;
    }

    public UserMatchedBuilder religion(String religion) {
        this.religion = religion;
        return this;
    }

    public UserMatchedBuilder jobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
        return this;
    }
}
