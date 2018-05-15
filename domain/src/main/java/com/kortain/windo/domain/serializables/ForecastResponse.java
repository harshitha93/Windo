package com.kortain.windo.domain.serializables;

import java.io.Serializable;
import java.util.List;

/**
 * Created by satiswardash on 25/04/18.
 */

public class ForecastResponse implements Serializable {

    private int cod;
    private String message;
    private int cnt;
    private City city;
    private List<Weather> list;

    public ForecastResponse() {
    }

    public ForecastResponse(int cod, String message, int cnt, City city, List<Weather> list) {
        this.cod = cod;
        this.message = message;
        this.cnt = cnt;
        this.city = city;
        this.list = list;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Weather> getList() {
        return list;
    }

    public void setList(List<Weather> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ForecastResponse{" +
                "cod=" + cod +
                ", message='" + message + '\'' +
                ", cnt=" + cnt +
                ", city=" + city +
                ", list=" + list +
                '}';
    }
}
