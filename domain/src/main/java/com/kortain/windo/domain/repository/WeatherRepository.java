package com.kortain.windo.domain.repository;

import com.kortain.windo.domain.entity.CurrentWeatherEntity;

/**
 * Created by satiswardash on 25/04/18.
 */

public interface WeatherRepository {

    void requestCurrentWeather(String place);
    void requestCurrentWeather(double lat, double lon);
    void requestAll();
}
