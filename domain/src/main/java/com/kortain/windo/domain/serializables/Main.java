package com.kortain.windo.domain.serializables;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by satiswardash on 25/04/18.
 */

public class Main implements Serializable {

    @SerializedName("temp")
    private float temperature;
    private float pressure;
    private float humidity;
    @SerializedName("temp_min")
    private float minimumTemperature;
    @SerializedName("temp_max")
    private float maximumTemperature;
    @SerializedName("sea_level")
    private float seaLevelPressure;
    @SerializedName("grnd_level")
    private float groundLevelPressure;

    public Main() {
    }

    public Main(float temperature, float pressure, float humidity, float minimumTemperature, float maximumTemperature, float seaLevelPressure, float groundLevelPressure) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.minimumTemperature = minimumTemperature;
        this.maximumTemperature = maximumTemperature;
        this.seaLevelPressure = seaLevelPressure;
        this.groundLevelPressure = groundLevelPressure;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getMinimumTemperature() {
        return minimumTemperature;
    }

    public void setMinimumTemperature(float minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public float getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(float maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

    public float getSeaLevelPressure() {
        return seaLevelPressure;
    }

    public void setSeaLevelPressure(float seaLevelPressure) {
        this.seaLevelPressure = seaLevelPressure;
    }

    public float getGroundLevelPressure() {
        return groundLevelPressure;
    }

    public void setGroundLevelPressure(float groundLevelPressure) {
        this.groundLevelPressure = groundLevelPressure;
    }

    @Override
    public String toString() {
        return "Main{" +
                "temperature='" + temperature + '\'' +
                ", pressure='" + pressure + '\'' +
                ", humidity='" + humidity + '\'' +
                ", minimumTemperature='" + minimumTemperature + '\'' +
                ", maximumTemperature='" + maximumTemperature + '\'' +
                ", seaLevelPressure='" + seaLevelPressure + '\'' +
                ", groundLevelPressure='" + groundLevelPressure + '\'' +
                '}';
    }
}
