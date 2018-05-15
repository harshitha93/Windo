package com.kortain.windo.domain.services;

import android.support.annotation.NonNull;

import com.kortain.windo.domain.repository.RepositoryBuilder;
import com.kortain.windo.domain.repository.WeatherRepositoryImpl;

/**
 * Created by satiswardash on 26/04/18.
 */

public class ServiceBuilder {

    private static final Object sLock = new Object();
    private static ServiceBuilder INSTANCE;

    private ServiceBuilder(){}

    /**
     * Get instance for {@link RepositoryBuilder}
     *
     * @return
     */
    public static synchronized ServiceBuilder getInstance() {

        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = new ServiceBuilder();
            }
            return INSTANCE;
        }
    }

    /**
     * Get {@link WeatherRepositoryImpl} instance by passing the callback reference
     *
     * @param instance
     * @return
     */
    public WeatherService getWeatherRepositoryInstance(@NonNull WeatherServiceCallback instance) {
        return new WeatherServiceImpl(instance);
    }
}
