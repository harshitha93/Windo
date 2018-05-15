package com.kortain.windo.domain.repository;

/**
 * Created by satiswardash on 26/04/18.
 */

public interface ForecastRepository {

    void requestForecast(String place);
    void requestForecast(double lat, double lon);
}
