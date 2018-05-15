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
public class ForecastEntity {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "forecast_id")
    private String id;
    @ColumnInfo(name = "forecast_currentTemperature")
    private float currentTemperature;
    @ColumnInfo(name = "forecast_maxTemperature")
    private float maxTemperature;
    @ColumnInfo(name = "forecast_minTemperature")
    private float minTemperature;
    @ColumnInfo(name = "forecast_description")
    private String description;
    @ColumnInfo(name = "forecast_place")
    private String place;
    @ColumnInfo(name = "forecast_country")
    private String country;
    @ColumnInfo(name = "forecast_latitude")
    private double latitude;
    @ColumnInfo(name = "forecast_longitude")
    private double longitude;
    @ColumnInfo(name = "forecast_icon")
    private String icon;
    @ColumnInfo(name = "forecast_state")
    private String state;
    @ColumnInfo(name = "forecast_timestamp")
    private long timestamp;
    @ColumnInfo(name = "forecast_createdAt_timestamp")
    private long createdAt;

    public ForecastEntity() {
        this.id = UUID.randomUUID().toString();
    }

    @Ignore
    public ForecastEntity(String id, float currentTemperature, float maxTemperature, float minTemperature, String description, String place, String country, double latitude, double longitude, String icon, String state, long timestamp, long createdAt) {
        if (id == null)
            this.id = UUID.randomUUID().toString();
        else
            this.id = id;
        this.currentTemperature = currentTemperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.description = description;
        this.place = place;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.icon = icon;
        this.state = state;
        this.timestamp = timestamp;
        this.createdAt = createdAt;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
