package com.kortain.windo.domain.serializables;

import java.io.Serializable;

/**
 * Created by satiswardash on 25/04/18.
 */

public class System implements Serializable {

    private int type;
    private int id;
    private String message;
    private String country;
    private long sunrise;
    private long sunset;
    private String pod;

    public System() {
    }

    public System(int type, int id, String message, String country, long sunrise, long sunset, String pod) {
        this.type = type;
        this.id = id;
        this.message = message;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.pod = pod;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    @Override
    public String toString() {
        return "System{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", message='" + message + '\'' +
                ", country='" + country + '\'' +
                ", sunrise='" + sunrise + '\'' +
                ", sunset='" + sunset + '\'' +
                ", pod='" + pod + '\'' +
                '}';
    }
}
