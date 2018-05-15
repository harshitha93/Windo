package com.kortain.windo.domain.services;

import com.kortain.windo.domain.models.CurrentWeatherData;
import com.kortain.windo.domain.models.ForecastData;

import java.util.List;

/**
 * Created by satiswardash on 26/04/18.
 */

public interface WeatherServiceCallback {

    void onDataReceived(CurrentWeatherData weatherComponent, ForecastData forecastComponent);
    void onDataReceived(List<CurrentWeatherData> weatherDataList);
    void onFailure(String cause);
}
