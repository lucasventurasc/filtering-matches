package com.sparknetworks.loveos.controller;

import com.sparknetworks.loveos.domain.Range;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Objects;

public class QueryFilter {

    private String hasPhoto;
    private String inContact;
    private String favorited;

    @DecimalMin(value = "0.01", message = "compatibility score range lower bound should be greater than or equal to 0.01")
    private BigDecimal compatibilityScoreRangeFrom;

    @DecimalMax(value = "0.99", message = "compatibility score range upper bound should be less than or equal to 0.99")
    private BigDecimal compatibilityScoreRangeTo;

    @Min(value = 18, message = "age range lower bound should be greater than or equal to 18")
    private Integer ageRangeFrom;

    @Max(value = 95, message = "age range upper bound should be less than or equal to 95")
    private Integer ageRangeTo;

    @Min(value = 135, message = "height range lower bound should be greater than or equal to 135")
    private Integer heightRangeFrom;

    @Max(value = 210, message = "height range upper bound should be less than or equal to 210")
    private Integer heightRangeTo;

    @DecimalMin(value = "30", message = "distance in km should be greater than or equal to 30")
    @DecimalMax(value = "300", message = "distance in km should be less than or equal to 300")
    private BigDecimal distanceInKm;

    public void setHasPhoto(String hasPhoto) {
        this.hasPhoto = hasPhoto;
    }

    public void setInContact(String inContact) {
        this.inContact = inContact;
    }

    public void setFavorited(String favorited) {
        this.favorited = favorited;
    }

    public String getHasPhoto() {
        return hasPhoto == null ? "no-filter" : hasPhoto;
    }

    public String getInContact() {
        return inContact == null ? "no-filter" : inContact;
    }

    public String getFavorited() {
        return favorited == null ? "no-filter" : favorited;
    }

    private boolean areFilled(Object... fields) {
        return Arrays.stream(fields).noneMatch(Objects::isNull);
    }

    public Range<Double> getCompatibilityScoreRange() {
        if (areFilled(compatibilityScoreRangeFrom, compatibilityScoreRangeTo)) {
            return Range.of(compatibilityScoreRangeFrom.doubleValue(), compatibilityScoreRangeTo.doubleValue());
        } else {
            return null;
        }
    }

    public Range<Integer> getAgeRange() {
        return areFilled(ageRangeFrom, ageRangeTo) ? Range.of(ageRangeFrom, ageRangeTo) : null;
    }

    public Range<Integer> getHeightRange() {
        return areFilled(heightRangeFrom, heightRangeTo) ? Range.of(heightRangeFrom, heightRangeTo) : null;
    }

    public Double getDistanceInKm() {
        return distanceInKm != null ? distanceInKm.doubleValue() : null;
    }

    public void setDistanceInKm(Double distanceInKm) {
        this.distanceInKm = distanceInKm == null ? null : BigDecimal.valueOf(distanceInKm);
    }

    public void setCompatibilityScoreRangeFrom(Double compatibilityScoreRangeFrom) {
        this.compatibilityScoreRangeFrom = compatibilityScoreRangeFrom == null ? null : BigDecimal.valueOf(compatibilityScoreRangeFrom);
    }

    public void setCompatibilityScoreRangeTo(Double compatibilityScoreRangeTo) {
        this.compatibilityScoreRangeTo = compatibilityScoreRangeTo == null ? null : BigDecimal.valueOf(compatibilityScoreRangeTo);
    }

    public void setAgeRangeFrom(Integer ageRangeFrom) {
        this.ageRangeFrom = ageRangeFrom;
    }

    public void setAgeRangeTo(Integer ageRangeTo) {
        this.ageRangeTo = ageRangeTo;
    }

    public void setHeightRangeFrom(Integer heightRangeFrom) {
        this.heightRangeFrom = heightRangeFrom;
    }

    public void setHeightRangeTo(Integer heightRangeTo) {
        this.heightRangeTo = heightRangeTo;
    }
}
