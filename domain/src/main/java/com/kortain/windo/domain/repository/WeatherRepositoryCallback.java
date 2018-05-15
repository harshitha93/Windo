package com.kortain.windo.domain.repository;

import com.kortain.windo.domain.entity.CurrentWeatherEntity;

import java.util.List;

/**
 * Created by satiswardash on 25/04/18.
 */

public interface WeatherRepositoryCallback extends RepositoryCallback{

    void onWeatherDataReceived(CurrentWeatherEntity weatherEntity);
    void onWeatherDataReceived(List<CurrentWeatherEntity> currentWeatherEntities);
}
