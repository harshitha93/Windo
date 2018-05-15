package com.kortain.windo.domain.network;

import com.kortain.windo.domain.serializables.CurrentWeatherResponse;
import com.kortain.windo.domain.serializables.ForecastResponse;
import com.kortain.windo.domain.utility.OWMEndpoints;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by satiswardash on 25/04/18.
 */

public interface NetworkService {

    @GET(OWMEndpoints.CURRENT_WEATHER)
    Call<CurrentWeatherResponse> fetchCurrentWeatherReport(@Query("q") String location);

    @GET(OWMEndpoints.WEEKLY_WEATHER_FORECAST)
    Call<ForecastResponse> fetchWeeklyForecast(@Query("q") String location);

    @GET(OWMEndpoints.CURRENT_WEATHER)
    Call<CurrentWeatherResponse> fetchAccurateWeatherReport(@Query("lat") double latitude, @Query("lon") double longitude);

    @GET(OWMEndpoints.WEEKLY_WEATHER_FORECAST)
    Call<ForecastResponse> fetchAccurateWeeklyForecast(@Query("lat") double latitude, @Query("lon") double longitude);
}
