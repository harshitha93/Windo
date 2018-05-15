package com.kortain.windo.domain.serializables;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by satiswardash on 25/04/18.
 */

public class Wind implements Serializable {

    private float speed;
    @SerializedName("deg")
    private float direction;

    public Wind() {
    }

    public Wind(float speed, float direction) {
        this.speed = speed;
        this.direction = direction;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "speed='" + speed + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
