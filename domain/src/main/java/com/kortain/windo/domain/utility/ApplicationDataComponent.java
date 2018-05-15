package com.kortain.windo.domain.utility;

import com.kortain.windo.domain.repository.ForecastRepositoryImpl;
import com.kortain.windo.domain.repository.WeatherRepositoryImpl;

import dagger.Component;

/**
 * Created by satiswardash on 25/04/18.
 */

@Component(modules = {ApplicationModule.class})
public interface ApplicationDataComponent {

    void inject(WeatherRepositoryImpl weatherRepository);
    void inject(ForecastRepositoryImpl forecastRepository);
}
