package com.kortain.windo.domain.serializables;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by satiswardash on 25/04/18.
 */

public class Rain implements Serializable {

    @SerializedName("3h")
    private float volume;

    public Rain() {
    }

    public Rain(float volume) {
        this.volume = volume;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Rain{" +
                "volume='" + volume + '\'' +
                '}';
    }
}
