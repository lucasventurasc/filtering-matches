package com.sparknetworks.loveos.domain;

public class Coordinates {

    private static final double EARTH_RADIUS_IN_KM = 6372.8; // In kilometers

    private double lat;
    private double lon;

    public Coordinates(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    /**
     * Haversine formula
     * @param otherCoordinate coordinate to compare
     * @return distance in KM
     */
    public double distanceInKmTo(Coordinates otherCoordinate) {
        double lat1 = this.lat;
        double lon1 = this.lon;
        double lat2 = otherCoordinate.lat;
        double lon2 = otherCoordinate.lon;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return EARTH_RADIUS_IN_KM * c;
    }

	Double getLat() {
        return lat;
	}

	Double getLon() {
        return lon;
    }
}
