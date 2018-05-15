package com.kortain.windo.model;

public class LocationModel {

    private String id;
    private String place;
    private String temperature;
    private String time;
    private boolean isCurrentLocation;
    private boolean isLabel;

    public LocationModel() {
    }

    public LocationModel(String id, String place, String temperature, String time, boolean isCurrentLocation, boolean isLabel) {
        this.id = id;
        this.place = place;
        this.temperature = temperature;
        this.time = time;
        this.isCurrentLocation = isCurrentLocation;
        this.isLabel = isLabel;
    }

    public boolean isLabel() {
        return isLabel;
    }

    public void setLabel(boolean label) {
        isLabel = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isCurrentLocation() {
        return isCurrentLocation;
    }

    public void setCurrentLocation(boolean currentLocation) {
        isCurrentLocation = currentLocation;
    }
}
