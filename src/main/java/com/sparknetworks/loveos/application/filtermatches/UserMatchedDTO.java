package com.sparknetworks.loveos.application.filtermatches;

public class UserMatchedDTO {

    private String displayName;
    private String mainPhoto;
    private Integer age;
    private String jobTitle;
    private Integer heightInCm;
    private CityDTO city;
    private Double compatibilityScore;
    private Integer contactsExchanged;
    private boolean favourite;
    private String religion;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }

    public String getMainPhoto() {
        return mainPhoto;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getHeightInCm() {
        return heightInCm;
    }

    public void setHeightInCm(Integer heightInCm) {
        this.heightInCm = heightInCm;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    public Double getCompatibilityScore() {
        return compatibilityScore;
    }

    public void setCompatibilityScore(Double compatibilityScore) {
        this.compatibilityScore = compatibilityScore;
    }

    public Integer getContactsExchanged() {
        return contactsExchanged;
    }

    public void setContactsExchanged(Integer contactsExchanged) {
        this.contactsExchanged = contactsExchanged;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }
}
