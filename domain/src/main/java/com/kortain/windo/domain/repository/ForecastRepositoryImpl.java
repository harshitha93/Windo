package com.kortain.windo.domain.repository;

import android.os.Handler;

import com.kortain.windo.domain.entity.ForecastEntity;
import com.kortain.windo.domain.network.NetworkService;
import com.kortain.windo.domain.serializables.ForecastResponse;
import com.kortain.windo.domain.storage.ForecastDaoService;
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
 * Created by satiswardash on 26/04/18.
 */

public final class ForecastRepositoryImpl implements ForecastRepository {

    @Inject
    NetworkUtility networkUtility;
    @Inject
    NetworkService OWMApiService;
    @Inject
    ForecastDaoService forecastDaoService;
    private ForecastRepositoryCallback mCallback;

    public ForecastRepositoryImpl(ForecastRepositoryCallback instance) {

        Application.getDataComponent().inject(this);

        if (instance != null)
            mCallback = instance;
        else
            throw new NullPointerException();
    }

    /**
     * Request forecast updates for given string location
     * <p>
     * Checks inside the local storage first, if present then checks for the expiry
     * <p>
     * If expired then fetch the latest forecast updates from the Network and persist to local storage and gives a callback
     * If not expired then gives the saved weather details instead of fetching it from the network again, once fetched updates the existing entry
     * If forecast entry is not present inside local storage then makes a network call which creates a new forecast entry
     * <p>
     * Expiry checks for minimum 10 minutes
     *
     * @param place
     */
    @Override
    public void requestForecast(final String place) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<ForecastEntity>> results = executor.submit(new Callable<List<ForecastEntity>>() {
            @Override
            public List<ForecastEntity> call() throws Exception {
                return forecastDaoService.findAllForecastEntityListAsc(place.toLowerCase());
            }
        });

        try {
            final List<ForecastEntity> forecastEntities = results.get();
            if (forecastEntities != null &&
                    !forecastEntities.isEmpty()) {

                final Runnable _runnable = new Runnable() {
                    @Override
                    public void run() {
                        long lastUpdated = forecastEntities.get(0).getCreatedAt();
                        if (Utilities.isOlder(lastUpdated)) {
                            if (networkUtility.hasNetworkAccess())
                                requestForecastFromNetwork(true, place);
                            else {
                                mCallback.onForecastReceived(forecastEntities);
                                mCallback.onError("No network, can't update the forecast.");
                            }
                        } else {
                            mCallback.onForecastReceived(forecastEntities);
                        }
                    }
                };
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        new Thread(_runnable).start();
                    }
                }, 2000);

                mCallback.onForecastReceived(forecastEntities);

            } else {
                requestForecastFromNetwork(false, place);
            }

        } catch (InterruptedException | ExecutionException e) {
            mCallback.onError(e.getMessage());
        }
    }

    /**
     * Request the accurate forecast data using current latitude and longitude
     * <p>
     * Gets the network response first and check for existing records for the location
     * <p>
     * If entity is present then checks for the expiry
     * <p>
     * If expired then update the latest forecast updates already fetched from the Network and persist to local storage which'll give a callback
     * If not expired then gives the saved weather details instead of the updated one
     * If forecast entry is not present inside local storage then insert the  updated forecast entity fetched from network
     * <p>
     * Expiry checks for minimum 10 minutes
     *
     * @param latitude
     * @param longitude
     */
    @Override
    public void requestForecast(double latitude, double longitude) {

        if (networkUtility.hasNetworkAccess()) {

            OWMApiService.fetchAccurateWeeklyForecast(latitude, longitude)
                    .enqueue(new Callback<ForecastResponse>() {
                        @SuppressWarnings("ConstantConditions")
                        @Override
                        public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {

                            if (response.code() == Constants.HTTP_OK &&
                                    response.body() != null) {

                                if (response.body().getCity() != null &&
                                        response.body().getCity().getName() != null &&
                                        !response.body().getCity().getName().isEmpty()) {

                                    final String place = response.body().getCity().getName();
                                    final List<ForecastEntity> updatedForecastEntities = MapperUtil.map(response.body());

                                    ExecutorService executor = Executors.newSingleThreadExecutor();
                                    Future<List<ForecastEntity>> results = executor.submit(new Callable<List<ForecastEntity>>() {
                                        @Override
                                        public List<ForecastEntity> call() throws Exception {
                                            return forecastDaoService.findAllForecastEntityListAsc(place.toLowerCase());
                                        }
                                    });

                                    try {
                                        List<ForecastEntity> existingForecastEntities = results.get();
                                        if (existingForecastEntities != null &&
                                                !existingForecastEntities.isEmpty()) {

                                            long lastUpdated = existingForecastEntities.get(0).getCreatedAt();
                                            if (Utilities.isOlder(lastUpdated)) {
                                                //if FORECAST record is not older than 10 minutes update the exiting record
                                                updateForecastRecords(updatedForecastEntities, place);
                                            } else {
                                                mCallback.onForecastReceived(existingForecastEntities);
                                            }
                                        } else {
                                            insertForecastRecords(updatedForecastEntities);
                                        }

                                    } catch (InterruptedException | ExecutionException e) {
                                        mCallback.onError(e.getMessage());
                                    }
                                }

                            }
                        }

                        @Override
                        public void onFailure(Call<ForecastResponse> call, Throwable t) {
                            mCallback.onError(t.getMessage());
                        }
                    });
        } else
            mCallback.onError("No network connection");
    }

    /**
     * @param isExists
     * @param place
     */
    private void requestForecastFromNetwork(final boolean isExists, String place) {

        if (networkUtility.hasNetworkAccess()) {

            OWMApiService.fetchWeeklyForecast(place)
                    .enqueue(new Callback<ForecastResponse>() {
                        @SuppressWarnings("ConstantConditions")
                        @Override
                        public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {

                            if (response.code() == Constants.HTTP_OK &&
                                    response.body() != null) {

                                final List<ForecastEntity> forecastEntities = MapperUtil.map(response.body());
                                if (!isExists)
                                    insertForecastRecords(forecastEntities);
                                else {

                                    if (response.body().getCity() != null)
                                        if (response.body().getCity().getName() != null &&
                                                !response.body().getCity().getName().isEmpty()) {
                                            updateForecastRecords(forecastEntities, response.body().getCity().getName());
                                        } else
                                            mCallback.onError("Some error has occurred.");
                                    else
                                        mCallback.onError("Some error has occurred.");
                                }
                            } else if (response.code() == Constants.HTTP_NOT_FOUND) {
                                mCallback.onError(response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<ForecastResponse> call, Throwable t) {
                            mCallback.onError(t.getMessage());
                        }
                    });

        } else
            mCallback.onError("No network connection");
    }

    /**
     * @param updatedForecastEntities
     * @param place
     */
    private void updateForecastRecords(final List<ForecastEntity> updatedForecastEntities, final String place) {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<ForecastEntity>> results = executor.submit(new Callable<List<ForecastEntity>>() {
            @Override
            public List<ForecastEntity> call() throws Exception {
                return forecastDaoService.findAllForecastEntityListAsc(place.toLowerCase());
            }
        });

        try {

            final List<ForecastEntity> existingForecastEntities = results.get();
            if (existingForecastEntities != null &&
                    !existingForecastEntities.isEmpty()) {

                ExecutorService deleteService = Executors.newSingleThreadExecutor();
                Future<Integer> delete = deleteService.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        return forecastDaoService.deleteForecastEntities(existingForecastEntities);
                    }
                });

                int deletedRowCount = delete.get();
                if (deletedRowCount > 0) {

                    ExecutorService insertService = Executors.newSingleThreadExecutor();
                    Future<List<Long>> insert = insertService.submit(new Callable<List<Long>>() {
                        @Override
                        public List<Long> call() throws Exception {
                            return forecastDaoService.insertForecastEntities(updatedForecastEntities);
                        }
                    });

                    List<Long> insertedRowCount = insert.get();
                    if (insertedRowCount.size() == updatedForecastEntities.size()) {
                        mCallback.onForecastReceived(updatedForecastEntities);
                    }
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            mCallback.onError(e.getMessage());
        }
    }

    /**
     * @param newForecastEntities
     */
    private void insertForecastRecords(final List<ForecastEntity> newForecastEntities) {

        ExecutorService insertService = Executors.newSingleThreadExecutor();
        Future<List<Long>> insert = insertService.submit(new Callable<List<Long>>() {
            @Override
            public List<Long> call() throws Exception {
                return forecastDaoService.insertForecastEntities(newForecastEntities);
            }
        });

        List<Long> insertedRowCount;
        try {
            insertedRowCount = insert.get();
            if (insertedRowCount.size() == newForecastEntities.size()) {
                mCallback.onForecastReceived(newForecastEntities);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
