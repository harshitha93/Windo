package com.kortain.windo.domain;

import android.util.Log;

import com.kortain.windo.domain.network.NetworkService;
import com.kortain.windo.domain.network.NetworkServiceBuilder;
import com.kortain.windo.domain.serializables.CurrentWeatherResponse;
import com.kortain.windo.domain.serializables.ForecastResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by satiswardash on 25/04/18.
 */


public class NetworkTest {

    private static final String TAG = NetworkTest.class.toString();

    NetworkService networkService;

    //@Test
    public void currentWeatherReportValidator() {

        networkService = NetworkServiceBuilder.build();
        networkService.fetchCurrentWeatherReport("Bangalore, India").enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                Log.i(TAG, "currentWeatherReportValidator: "+response.body().toString());
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    //@Test
    public void forecastReportValidator() {

        networkService = NetworkServiceBuilder.build();
        networkService.fetchWeeklyForecast("Bangalore, India").enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                Log.i(TAG, "forecastReportValidator: "+response.body().toString());
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    //@Test
    public void currentAccurateWeatherReportValidator() {

        networkService = NetworkServiceBuilder.build();
        networkService.fetchAccurateWeatherReport(51.51, -0.13).enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                Log.i(TAG, "currentAccurateWeatherReportValidator: "+response.body().toString());
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    //@Test
    public void forecastAccurateReportValidator() {

        networkService = NetworkServiceBuilder.build();
        networkService.fetchAccurateWeeklyForecast(51.51, -0.13).enqueue(new Callback<ForecastResponse>() {
            @Override
            public void onResponse(Call<ForecastResponse> call, Response<ForecastResponse> response) {
                Log.i(TAG, "forecastAccurateReportValidator: "+response.body().toString());
            }

            @Override
            public void onFailure(Call<ForecastResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
