package com.sparknetworks.loveos.domain;

public class City {

    private String name;
    private Coordinates coordinates;

    public City(String name, Coordinates coordinates) {
        this.name = name;
        this.coordinates = coordinates;
    }

    public double distanceInKmTo(City city) {
        return coordinates.distanceInKmTo(city.coordinates);
    }

    public String getName() {
        return name;
    }

    public Double getLat() {
        return this.coordinates.getLat();
    }

    public Double getLon() {
        return this.coordinates.getLon();
    }
}
