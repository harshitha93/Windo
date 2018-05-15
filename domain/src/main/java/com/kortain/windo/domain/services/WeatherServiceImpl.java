package com.kortain.windo.domain.services;

import android.support.annotation.NonNull;

import com.kortain.windo.domain.entity.CurrentWeatherEntity;
import com.kortain.windo.domain.entity.ForecastEntity;
import com.kortain.windo.domain.models.CurrentWeatherData;
import com.kortain.windo.domain.models.ForecastData;
import com.kortain.windo.domain.repository.ForecastRepository;
import com.kortain.windo.domain.repository.ForecastRepositoryCallback;
import com.kortain.windo.domain.repository.RepositoryBuilder;
import com.kortain.windo.domain.repository.WeatherRepository;
import com.kortain.windo.domain.repository.WeatherRepositoryCallback;
import com.kortain.windo.domain.utility.Utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by satiswardash on 26/04/18.
 */

final class WeatherServiceImpl
        implements WeatherService, WeatherRepositoryCallback, ForecastRepositoryCallback {

    private WeatherServiceCallback mCallback;
    private WeatherRepository mWeatherRepository;
    private ForecastRepository mForecastRepository;

    private CurrentWeatherData[] result1 = new CurrentWeatherData[1];
    private ForecastData[] result2 = new ForecastData[1];

    private String message;

    /**
     * @param instance
     */
    WeatherServiceImpl(@NonNull WeatherServiceCallback instance) {
        result1[0] = null;
        result2[0] = null;
        mCallback = instance;
        mWeatherRepository = RepositoryBuilder.getInstance().getWeatherRepositoryInstance(this);
        mForecastRepository = RepositoryBuilder.getInstance().getForecastRepositoryInstance(this);
    }

    /**
     * Request
     *
     * @param place
     */
    @Override
    public void requestForData(String place) {
        mWeatherRepository.requestCurrentWeather(place);
        mForecastRepository.requestForecast(place);
    }

    /**
     * Request
     *
     * @param latitude
     * @param longitude
     */
    @Override
    public void requestForData(double latitude, double longitude) {
        mWeatherRepository.requestCurrentWeather(latitude, longitude);
        mForecastRepository.requestForecast(latitude, longitude);
    }

    /**
     * Request for all data
     */
    @Override
    public void requestForAllData() {
        mWeatherRepository.requestAll();
    }

    /**
     * Callback
     *
     * @param message
     */
    @Override
    public void onError(String message) {
        if (!message.equalsIgnoreCase(this.message)) {
            this.message = message;
            mCallback.onFailure(message);
        } else
            this.message = null;
    }

    /**
     * Callback
     *
     * @param weatherEntity
     */
    @Override
    public void onWeatherDataReceived(CurrentWeatherEntity weatherEntity) {
        prepareWeatherData(weatherEntity);
    }

    @Override
    public void onWeatherDataReceived(List<CurrentWeatherEntity> currentWeatherEntities) {
        mCallback.onDataReceived(prepareWeatherData(currentWeatherEntities));
    }

    /**
     * Callback
     *
     * @param forecastEntities
     */
    @Override
    public void onForecastReceived(List<ForecastEntity> forecastEntities) {
        prepareForecastData(forecastEntities);
    }

    /**
     * @param currentWeatherEntities
     */
    private List<CurrentWeatherData> prepareWeatherData(List<CurrentWeatherEntity> currentWeatherEntities) {

        List<CurrentWeatherData> cwdl = new ArrayList<>();
        for (CurrentWeatherEntity weatherEntity :
                currentWeatherEntities) {

            CurrentWeatherData cwd = new CurrentWeatherData(
                    weatherEntity.getCurrentTemperature(),
                    weatherEntity.getMaxTemperature(),
                    weatherEntity.getMinTemperature(),
                    weatherEntity.getHumidity(),
                    weatherEntity.getWind(),
                    weatherEntity.getPressure(),
                    weatherEntity.getSunrise(),
                    weatherEntity.getSunset(),
                    weatherEntity.getDescription(),
                    weatherEntity.getState(),
                    weatherEntity.getPlace(),
                    weatherEntity.getCountry(),
                    weatherEntity.getTimestamp(),
                    weatherEntity.getIcon());

            cwdl.add(cwd);
        }
        return cwdl;
    }

    /**
     * @param weatherEntity
     */
    private void prepareWeatherData(CurrentWeatherEntity weatherEntity) {

        CurrentWeatherData cwd = new CurrentWeatherData(
                weatherEntity.getCurrentTemperature(),
                weatherEntity.getMaxTemperature(),
                weatherEntity.getMinTemperature(),
                weatherEntity.getHumidity(),
                weatherEntity.getWind(),
                weatherEntity.getPressure(),
                weatherEntity.getSunrise(),
                weatherEntity.getSunset(),
                weatherEntity.getDescription(),
                weatherEntity.getState(),
                weatherEntity.getPlace(),
                weatherEntity.getCountry(),
                weatherEntity.getTimestamp(),
                weatherEntity.getIcon());

        result1[0] = cwd;
        returnCombinedResults();
    }

    /**
     * @param forecastEntities
     */
    private void prepareForecastData(List<ForecastEntity> forecastEntities) {

        List<CurrentWeatherData> dailyForecast = getDailyForecast(forecastEntities);
        List<CurrentWeatherData> weeklyForecast = getWeeklyForecast(forecastEntities);

        ForecastData fd = new ForecastData(dailyForecast, weeklyForecast);
        result2[0] = fd;
        returnCombinedResults();
    }

    /**
     * Gives daily forecast for next 5 days
     *
     * @param forecastEntities
     * @return
     */
    private List<CurrentWeatherData> getWeeklyForecast(List<ForecastEntity> forecastEntities) {

        long currentTime;
        long endTime;
        Calendar current = Calendar.getInstance(); // creates calendar

        current.setTime(new Date()); // sets calendar time/date
        currentTime = Utilities.nextDayStart(current.getTime());

        current.add(Calendar.DAY_OF_MONTH, 5); // adds one hour
        endTime = current.getTimeInMillis(); // returns new date object, one hour in the future

        return getResultWithinTime(currentTime, endTime, forecastEntities);
    }

    /**
     * Gives the hourly forecast for next 24 hours, in 3 hours duration
     *
     * @param forecastEntities
     * @return
     */
    private List<CurrentWeatherData> getDailyForecast(List<ForecastEntity> forecastEntities) {

        long currentTime;
        long endTime;
        Calendar current = Calendar.getInstance(); // creates calendar

        current.setTime(new Date()); // sets calendar time/date
        currentTime = current.getTimeInMillis();

        current.add(Calendar.HOUR_OF_DAY, 24); // adds one hour
        endTime = current.getTimeInMillis();//(current.getTime()); // returns new date object, one hour in the future

        return getResultWithinTime(currentTime, endTime, forecastEntities);
    }

    /**
     * @param startTime
     * @param endTime
     * @param forecastEntities
     * @return
     */
    private List<CurrentWeatherData> getResultWithinTime(long startTime, long endTime, List<ForecastEntity> forecastEntities) {

        List<CurrentWeatherData> results = new ArrayList<>();
        for (ForecastEntity entity :
                forecastEntities) {

            if (entity.getTimestamp() > startTime &&
                    entity.getTimestamp() <= endTime) {

                CurrentWeatherData cwd = new CurrentWeatherData(
                        entity.getCurrentTemperature(),
                        entity.getMaxTemperature(),
                        entity.getMinTemperature(),
                        0, 0, 0, 0, 0,
                        entity.getDescription(),
                        entity.getState(),
                        entity.getPlace(),
                        entity.getCountry(),
                        entity.getTimestamp(),
                        entity.getIcon());

                results.add(cwd);
            }
        }
        return results;
    }

    /**
     *
     */
    private void returnCombinedResults() {

        if (result1[0] != null &&
                result2[0] != null) {
            mCallback.onDataReceived(result1[0], result2[0]);
            result1[0] = null;
            result2[0] = null;
        }
    }
}
