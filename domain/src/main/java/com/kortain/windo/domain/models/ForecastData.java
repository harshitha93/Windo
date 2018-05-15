package com.kortain.windo.domain.models;

import java.util.List;

/**
 * Created by satiswardash on 26/04/18.
 */

public class ForecastData {

    private List<CurrentWeatherData> dailyForecast;
    private List<CurrentWeatherData> weeklyForecast;

    public ForecastData(List<CurrentWeatherData> dailyForecast, List<CurrentWeatherData> weeklyForecast) {
        this.dailyForecast = dailyForecast;
        this.weeklyForecast = weeklyForecast;
    }

    public ForecastData() {
    }

    public List<CurrentWeatherData> getDailyForecast() {
        return dailyForecast;
    }

    public void setDailyForecast(List<CurrentWeatherData> dailyForecast) {
        this.dailyForecast = dailyForecast;
    }

    public List<CurrentWeatherData> getWeeklyForecast() {
        return weeklyForecast;
    }

    public void setWeeklyForecast(List<CurrentWeatherData> weeklyForecast) {
        this.weeklyForecast = weeklyForecast;
    }
}
