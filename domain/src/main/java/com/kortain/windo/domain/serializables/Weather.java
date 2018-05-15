package com.kortain.windo.domain.serializables;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by satiswardash on 25/04/18.
 */

public class Weather implements Serializable {

    @SerializedName("dt")
    private long timeOfDataCalculation;
    private System sys;
    @SerializedName("coord")
    private Coordinate coordinate;
    private List<Details> weather;
    private String base;
    private Main main;
    private String visibility;
    private Wind wind;
    private Clouds clouds;
    private Rain rain;
    private Snow snow;
    @SerializedName("dt_txt")
    private String date;

    public Weather() {
    }

    public Weather(long timeOfDataCalculation, System sys, Coordinate coordinate, List<Details> weather, String base, Main main, String visibility, Wind wind, Clouds clouds, Rain rain, Snow snow, String date) {
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

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
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

    public long getTimeOfDataCalculation() {
        return timeOfDataCalculation;
    }

    public void setTimeOfDataCalculation(long timeOfDataCalculation) {
        this.timeOfDataCalculation = timeOfDataCalculation;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "timeOfDataCalculation='" + timeOfDataCalculation + '\'' +
                ", sys=" + sys +
                ", coordinate=" + coordinate +
                ", weather=" + getWeatherAsString(weather)+
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
