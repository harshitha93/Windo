package com.kortain.windo.domain.serializables;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by satiswardash on 25/04/18.
 */

public class City implements Serializable {

    private int id;
    private String name;
    @SerializedName("coord")
    private Coordinate coordinate;
    private String country;
    private double population;

    public City() {
    }

    public City(int id, String name, Coordinate coordinate, String country, double population) {
        this.id = id;
        this.name = name;
        this.coordinate = coordinate;
        this.country = country;
        this.population = population;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getPopulation() {
        return population;
    }

    public void setPopulation(double population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", coordinate=" + coordinate +
                ", country='" + country + '\'' +
                ", population='" + population + '\'' +
                '}';
    }
}
