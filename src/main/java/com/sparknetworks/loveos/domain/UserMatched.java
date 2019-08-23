package com.sparknetworks.loveos.domain;

import java.net.URI;

public class UserMatched {

    private Profile relatedProfile;
    private URI mainPhotoUrl;
    private boolean favorited;
    private String displayName;
    private Integer contactsExchanged;
    private Double compatibilityScore;
    private Integer age;
    private Integer heightInCm;
    private City city;
    private String religion;
    private String jobTitle;

    private UserMatched(Profile relatedProfile) {
        this.relatedProfile = relatedProfile;
    }

    public static UserMatched createUserMatchedRelatedToProfile(Profile relatedProfile) {
        return new UserMatched(relatedProfile);
    }

    public void setMainPhoto(URI mainPhotoUrl) {
        this.mainPhotoUrl = mainPhotoUrl;
    }

    public boolean hasPhoto() {
        return mainPhotoUrl != null;
    }

    public void setFavorited() {
        this.favorited = true;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public URI getMainPhotoUrl() {
        return mainPhotoUrl;
    }

    public void setContactsExchanged(Integer contactsExchanged) {
        this.contactsExchanged = contactsExchanged;
    }

    public Integer getContactsExchanged() {
        return contactsExchanged;
    }

    public boolean hasCompatibilityScoreWithinRange(Range<Double> range) {
        return range.isWithinRange(this.compatibilityScore);
    }

    public void setCompatibilityScore(Double compatibilityScore) {
        this.compatibilityScore = compatibilityScore;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isAgedBetween(Range<Integer> ageRange) {
        return ageRange.isWithinRange(this.age);
    }

    public void setHeightInCm(Integer heightInCm) {
        this.heightInCm = heightInCm;
    }

    public boolean hasHeightBetween(Range<Integer> heightInCmRange) {
        return heightInCmRange.isWithinRange(this.heightInCm);
    }

    public boolean distanceFromProfileCityIsWithinRadius(Double distanceRadiusInKm) {
        return this.relatedProfile.getCity().distanceInKmTo(this.getCity()) <= distanceRadiusInKm;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getReligion() {
        return religion;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Double getCompatibilityScore() {
        return this.compatibilityScore;
    }

    public Integer getHeightInCm() {
        return this.heightInCm;
    }

    public Integer getAge() {
        return age;
    }

    public String getCityName() {
        return city == null ? null : city.getName();
    }

    public Double getCityLatitude() {
        return city == null ? null : city.getLat();
    }

    public Double getCityLongitude() {
        return city == null ? null : city.getLon();
    }
}
