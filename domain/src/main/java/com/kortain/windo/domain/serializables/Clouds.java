package com.kortain.windo.domain.serializables;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by satiswardash on 25/04/18.
 */

public class Clouds implements Serializable {

    @SerializedName("all")
    private float cloudiness;

    public Clouds() {
    }

    public Clouds(float cloudiness) {
        this.cloudiness = cloudiness;
    }

    public float getCloudiness() {
        return cloudiness;
    }

    public void setCloudiness(float cloudiness) {
        this.cloudiness = cloudiness;
    }

    @Override
    public String toString() {
        return "Clouds{" +
                "cloudiness='" + cloudiness + '\'' +
                '}';
    }
}
