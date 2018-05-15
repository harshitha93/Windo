package com.kortain.windo.domain.repository;

import com.kortain.windo.domain.entity.ForecastEntity;

import java.util.List;

/**
 * Created by satiswardash on 26/04/18.
 */

public interface ForecastRepositoryCallback extends RepositoryCallback {

    void onForecastReceived(List<ForecastEntity> forecastEntities);
}
