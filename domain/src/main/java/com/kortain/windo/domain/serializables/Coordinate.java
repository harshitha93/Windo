package com.kortain.windo.domain.serializables;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by satiswardash on 25/04/18.
 */

public class Coordinate implements Serializable {

    @SerializedName("lon")
    private double longitude;
    @SerializedName("lat")
    private double latitude;

    public Coordinate(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Coordinate() {
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
