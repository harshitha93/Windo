package com.kortain.windo.domain.repository;

import android.support.annotation.NonNull;

/**
 * Created by satiswardash on 25/04/18.
 */

public class RepositoryBuilder {

    private static RepositoryBuilder INSTANCE;
    private static final Object sLock = new Object();

    /**
     * Get instance for {@link RepositoryBuilder}
     * @return
     */
    public static synchronized RepositoryBuilder getInstance() {

        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = new RepositoryBuilder();
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
    public WeatherRepository getWeatherRepositoryInstance(@NonNull WeatherRepositoryCallback instance) {
        return new WeatherRepositoryImpl(instance);
    }

    /**
     * Get {@link ForecastRepositoryImpl} instance by passing the callback reference
     *
     * @param instance
     * @return
     */
    public ForecastRepository getForecastRepositoryInstance(@NonNull ForecastRepositoryCallback instance) {
        return new ForecastRepositoryImpl(instance);
    }
}
