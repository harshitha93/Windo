package com.kortain.windo.domain.models;

/**
 * Created by satiswardash on 26/04/18.
 */

public class CurrentWeatherData {

    private float currentTemperature;
    private float maxTemperature;
    private float minTemperature;
    private int humidity;
    private int wind;
    private int pressure;
    private long sunrise;
    private long sunset;
    private String description;
    private String state;
    private String location;
    private String country;
    private long time;
    private String icon;

    public CurrentWeatherData() {
    }

    public CurrentWeatherData(float currentTemperature, float maxTemperature, float minTemperature, int humidity, int wind, int pressure, long sunrise, long sunset, String description, String state, String location, String country, long time, String icon) {
        this.currentTemperature = currentTemperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.humidity = humidity;
        this.wind = wind;
        this.pressure = pressure;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.description = description;
        this.state = state;
        this.location = location;
        this.country = country;
        this.time = time;
        this.icon = icon;
    }

    public float getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(float currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(float minTemperature) {
        this.minTemperature = minTemperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getWind() {
        return wind;
    }

    public void setWind(int wind) {
        this.wind = wind;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
