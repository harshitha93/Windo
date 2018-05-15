package com.kortain.windo.domain.services;

/**
 * Created by satiswardash on 26/04/18.
 */

public interface WeatherService {

    void requestForData(String place);
    void requestForData(double latitude, double longitude);
    void requestForAllData();
}
