package com.kortain.windo.domain.repository;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.kortain.windo.domain.entity.CurrentWeatherEntity;
import com.kortain.windo.domain.network.NetworkService;
import com.kortain.windo.domain.serializables.CurrentWeatherResponse;
import com.kortain.windo.domain.storage.WeatherDoaService;
import com.kortain.windo.domain.utility.Application;
import com.kortain.windo.domain.utility.Constants;
import com.kortain.windo.domain.utility.MapperUtil;
import com.kortain.windo.domain.utility.NetworkUtility;
import com.kortain.windo.domain.utility.Utilities;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by satiswardash on 24/04/18.
 */

public final class WeatherRepositoryImpl implements WeatherRepository {

    @Inject
    NetworkUtility networkUtility;
    @Inject
    NetworkService OWMApiService;
    @Inject
    WeatherDoaService weatherDaoService;
    private WeatherRepositoryCallback mCallback;

    WeatherRepositoryImpl(WeatherRepositoryCallback instance) {

        Application.getDataComponent().inject(this);

        if (instance != null)
            mCallback = instance;
        else
            throw new NullPointerException();
    }

    /**
     * Request weather updates for given string location
     * <p>
     * Checks inside the local storage first, if present then checks for the expiry
     * <p>
     * If expired then fetch the latest weather updates from the Network and persist to local storage and gives a callback
     * If not expired then gives the saved weather details instead of fetching it from the network again, once fetched updates the existing entry
     * If weather entry is not present inside local storage then makes a network call which creates a new weather entry
     * <p>
     * Expiry checks for minimum 10 minutes
     *
     * @param place
     */
    @Override
    public void requestCurrentWeather(@NonNull final String place) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<CurrentWeatherEntity> result = executor.submit(new Callable<CurrentWeatherEntity>() {
            @Override
            public CurrentWeatherEntity call() throws Exception {
                return weatherDaoService.findCurrentWeatherEntityByLocation(place.toLowerCase());
            }
        });

        try {
            final CurrentWeatherEntity weatherEntity = result.get();
            if (weatherEntity != null) {

                final Runnable _runnable = new Runnable() {
                    @Override
                    public void run() {
                        long lastUpdated = weatherEntity.getTimestamp();
                        if (Utilities.isOlder(lastUpdated)) {
                            if (networkUtility.hasNetworkAccess())
                                requestCurrentWeatherFromNetwork(true, place);
                            else {
                                mCallback.onWeatherDataReceived(weatherEntity);
                                mCallback.onError("No network, can't update the weather.");
                            }

                        } /*else
                            mCallback.onWeatherDataReceived(weatherEntity);*/
                    }
                };
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new Thread(_runnable).start();
                    }
                }, 2000);

                mCallback.onWeatherDataReceived(weatherEntity);

            } else {
                requestCurrentWeatherFromNetwork(false, place);
            }
        } catch (InterruptedException | ExecutionException e) {
            mCallback.onError(e.getMessage());
        }
    }

    /**
     * Request the accurate weather data using current latitude and longitude
     * <p>
     * Gets the network response first and check for existing records for the location
     * <p>
     * If entity is present then checks for the expiry
     * <p>
     * If expired then update the latest weather updates already fetched from the Network and persist to local storage which'll give a callback
     * If not expired then gives the saved weather details instead of the updated one
     * If weather entry is not present inside local storage then insert the  updated entity fetched from network
     * <p>
     * Expiry checks for minimum 10 minutes
     *
     * @param latitude
     * @param longitude
     */
    @Override
    public void requestCurrentWeather(double latitude, double longitude) {

        if (networkUtility.hasNetworkAccess()) {

            OWMApiService.fetchAccurateWeatherReport(latitude, longitude)
                    .enqueue(new Callback<CurrentWeatherResponse>() {
                        @SuppressWarnings("ConstantConditions")
                        @Override
                        public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {

                            if (response.code() == Constants.HTTP_OK &&
                                    response.body() != null) {

                                final CurrentWeatherEntity updatedWeatherEntity = MapperUtil.map(response.body());
                                ExecutorService executor = Executors.newSingleThreadExecutor();
                                Future<CurrentWeatherEntity> result = executor.submit(new Callable<CurrentWeatherEntity>() {
                                    @Override
                                    public CurrentWeatherEntity call() throws Exception {
                                        return weatherDaoService.findCurrentWeatherEntityByLocation(updatedWeatherEntity.getPlace().toLowerCase());
                                    }
                                });

                                try {
                                    CurrentWeatherEntity existingWeatherEntity = result.get();
                                    if (existingWeatherEntity != null) {

                                        long lastUpdated = existingWeatherEntity.getTimestamp();
                                        if (Utilities.isOlder(lastUpdated)) {
                                            //if weather record is not older than 10 minutes update the exiting record
                                            updateCurrentWeatherRecord(updatedWeatherEntity);
                                        } else
                                            mCallback.onWeatherDataReceived(existingWeatherEntity);
                                    } else {
                                        //if no records found then insert new one
                                        insertCurrentWeatherRecord(updatedWeatherEntity);
                                    }
                                } catch (InterruptedException | ExecutionException e) {
                                    mCallback.onError(e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                            mCallback.onError(t.getMessage());
                        }
                    });
        } else {
            mCallback.onError("No network connection");
        }
    }

    /**
     * Request weather details for all the stored locations
     *
     */
    @Override
    public void requestAll() {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<CurrentWeatherEntity>> result = executor.submit(new Callable<List<CurrentWeatherEntity>>() {
            @Override
            public List<CurrentWeatherEntity> call() {
                return weatherDaoService.findAllCurrentWeatherEntityList();
            }
        });

        try {
            final List<CurrentWeatherEntity> currentWeatherEntities = result.get();
            if (currentWeatherEntities != null &&
                    !currentWeatherEntities.isEmpty())
                mCallback.onWeatherDataReceived(currentWeatherEntities);
            else
                mCallback.onError("No locations found!");

        } catch (InterruptedException | ExecutionException e) {
            mCallback.onError(e.getMessage());
        }
    }

    /**
     * Uses OWM API for weather details
     * Requests a network call for fetching the latest weather details and updates/insert into the local storage
     * depending on the isExist flag value
     *
     * @param isExists
     * @param place
     */
    private void requestCurrentWeatherFromNetwork(final boolean isExists, final String place) {

        if (networkUtility.hasNetworkAccess()) {

            OWMApiService.fetchCurrentWeatherReport(place)
                    .enqueue(new Callback<CurrentWeatherResponse>() {
                        @SuppressWarnings("ConstantConditions")
                        @Override
                        public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {

                            if (response.code() == Constants.HTTP_OK &&
                                    response.body() != null) {

                                final CurrentWeatherEntity weatherEntity = MapperUtil.map(response.body());
                                if (!isExists) {
                                    insertCurrentWeatherRecord(weatherEntity);
                                } else {
                                    updateCurrentWeatherRecord(weatherEntity);
                                }
                            } else if (response.code() == Constants.HTTP_NOT_FOUND) {
                                mCallback.onError(response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                            mCallback.onError(t.getMessage());
                        }
                    });
        } else
            mCallback.onError("No network connection");
    }

    /**
     * @param newWeatherEntity
     */
    private void insertCurrentWeatherRecord(final CurrentWeatherEntity newWeatherEntity) {
        mCallback.onWeatherDataReceived(newWeatherEntity);
        new Thread(new Runnable() {
            @Override
            public void run() {
                weatherDaoService.insertCurrentWeatherEntity(newWeatherEntity);
            }
        }).start();
    }

    /**
     * Update the updated weather details with the existing weather entry
     * Fetches the existing entry first for the primary key
     * Once found replaces the primary key with the updated one and updates the local entry
     *
     * @param updatedWeather
     */
    private void updateCurrentWeatherRecord(final CurrentWeatherEntity updatedWeather) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<CurrentWeatherEntity> result = executor.submit(new Callable<CurrentWeatherEntity>() {
            @Override
            public CurrentWeatherEntity call() throws Exception {
                return weatherDaoService.findCurrentWeatherEntityByLocation(updatedWeather.getPlace().toLowerCase());
            }
        });

        try {
            CurrentWeatherEntity weatherEntity = result.get();
            if (weatherEntity != null) {

                String _pk = weatherEntity.getId();
                updatedWeather.setId(_pk);

                mCallback.onWeatherDataReceived(updatedWeather);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        weatherDaoService.updateCurrentWeatherEntity(updatedWeather);
                    }
                }).start();
            } else {

                mCallback.onWeatherDataReceived(updatedWeather);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        weatherDaoService.insertCurrentWeatherEntity(updatedWeather);
                    }
                }).start();
            }
        } catch (InterruptedException | ExecutionException e) {
            mCallback.onError(e.getMessage());
        }
    }
}
