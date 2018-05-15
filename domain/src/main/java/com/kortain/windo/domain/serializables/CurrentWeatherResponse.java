package com.kortain.windo.domain.serializables;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by satiswardash on 25/04/18.
 */

public class CurrentWeatherResponse implements Serializable {

    private int id;
    private String name;
    private int cod;
    @SerializedName("dt")
    private long timeOfDataCalculation;
    private System sys;
    @SerializedName("coord")
    private Coordinate coordinate;
    private List<Details> weather;
    private String base;
    private Main main;
    private long visibility;
    private Wind wind;
    private Clouds clouds;
    private Rain rain;
    private Snow snow;
    @SerializedName("dt_txt")
    private long date;

    public CurrentWeatherResponse() {
    }

    public CurrentWeatherResponse(int id, String name, int cod, long timeOfDataCalculation, System sys, Coordinate coordinate, List<Details> weather, String base, Main main, long visibility, Wind wind, Clouds clouds, Rain rain, Snow snow, long date) {
        this.id = id;
        this.name = name;
        this.cod = cod;
        this.timeOfDataCalculation = timeOfDataCalculation;
        this.sys = sys;
        this.coordinate = coordinate;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.clouds = clouds;
        this.rain = rain;
        this.snow = snow;
        this.date = date;
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

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public long getTimeOfDataCalculation() {
        return timeOfDataCalculation;
    }

    public void setTimeOfDataCalculation(long timeOfDataCalculation) {
        this.timeOfDataCalculation = timeOfDataCalculation;
    }

    public System getSys() {
        return sys;
    }

    public void setSys(System sys) {
        this.sys = sys;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public List<Details> getWeather() {
        return weather;
    }

    public void setWeather(List<Details> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public long getVisibility() {
        return visibility;
    }

    public void setVisibility(long visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Snow getSnow() {
        return snow;
    }

    public void setSnow(Snow snow) {
        this.snow = snow;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CurrentWeatherResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cod='" + cod + '\'' +
                ", timeOfDataCalculation='" + timeOfDataCalculation + '\'' +
                ", sys=" + sys +
                ", coordinate=" + coordinate +
                ", weather=" + getWeatherAsString(weather) +
                ", base='" + base + '\'' +
                ", main=" + main +
                ", visibility='" + visibility + '\'' +
                ", wind=" + wind +
                ", clouds=" + clouds +
                ", rain=" + rain +
                ", snow=" + snow +
                ", date='" + date + '\'' +
                '}';
    }

    private String getWeatherAsString(List<Details> details) {
        StringBuilder builder = new StringBuilder();
        for (Details d :
                details) {

            builder.append(d.toString());
        }
        return builder.toString();
    }
}
