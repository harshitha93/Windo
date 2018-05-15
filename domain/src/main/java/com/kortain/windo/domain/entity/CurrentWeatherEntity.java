package com.kortain.windo.domain.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

/**
 * Created by satiswardash on 25/04/18.
 */

@Entity
public class CurrentWeatherEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "weather_id")
    private String id;
    @ColumnInfo(name = "weather_currentTemperature")
    private float currentTemperature;
    @ColumnInfo(name = "weather_maxTemperature")
    private float maxTemperature;
    @ColumnInfo(name = "weather_minTemperature")
    private float minTemperature;
    @ColumnInfo(name = "weather_humidity")
    private int humidity;
    @ColumnInfo(name = "weather_wind")
    private int wind;
    @ColumnInfo(name = "weather_pressure")
    private int pressure;
    @ColumnInfo(name = "weather_sunrise")
    private long sunrise;
    @ColumnInfo(name = "weather_sunset")
    private long sunset;
    @ColumnInfo(name = "weather_description")
    private String description;
    @ColumnInfo(name = "weather_place")
    private String place;
    @ColumnInfo(name = "weather_country")
    private String country;
    @ColumnInfo(name = "weather_latitude")
    private double latitude;
    @ColumnInfo(name = "weather_longitude")
    private double longitude;
    @ColumnInfo(name = "weather_icon")
    private String icon;
    @ColumnInfo(name = "weather_state")
    private String state;
    @ColumnInfo(name = "weather_timestamp")
    private long timestamp;

    public CurrentWeatherEntity() {
        id = UUID.randomUUID().toString();
    }

    @Ignore
    public CurrentWeatherEntity(String id, float currentTemperature, float maxTemperature, float minTemperature, int humidity, int wind, int pressure, String description, long sunrise, long sunset, String place, String country, double latitude, double longitude, String icon, String state, long timestamp) {
        if (id == null)
            this.id = UUID.randomUUID().toString();
        else
            this.id = id;
        this.currentTemperature = currentTemperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.humidity = humidity;
        this.wind = wind;
        this.pressure = pressure;
        this.description = description;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.place = place;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.icon = icon;
        this.state = state;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "CurrentWeatherEntity{" +
                "id='" + id + '\'' +
                ", currentTemperature=" + currentTemperature +
                ", maxTemperature=" + maxTemperature +
                ", minTemperature=" + minTemperature +
                ", humidity=" + humidity +
                ", wind=" + wind +
                ", pressure=" + pressure +
                ", description='" + description + '\'' +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                ", place='" + place + '\'' +
                ", country='" + country + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", icon='" + icon + '\'' +
                ", state='" + state + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
