package com.kortain.windo.domain.components;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.kortain.windo.domain.models.CurrentWeatherData;
import com.kortain.windo.domain.models.ForecastData;
import com.kortain.windo.domain.services.ServiceBuilder;
import com.kortain.windo.domain.services.WeatherService;
import com.kortain.windo.domain.services.WeatherServiceCallback;

import java.util.List;

/**
 * Created by satiswardash on 26/04/18.
 */

public final class WeatherComponent extends ViewModel
        implements WeatherServiceCallback {

    private WeatherService mWeatherService;
    private ComponentOwner mComponentOwner;

    private MutableLiveData<CurrentWeatherData> mCurrentWeatherData;
    private MutableLiveData<List<CurrentWeatherData>> mWeatherDataList;
    private MutableLiveData<ForecastData> mForecastData;

    public WeatherComponent() {
        mWeatherService = ServiceBuilder.getInstance().getWeatherRepositoryInstance(this);
    }

    /**
     * @param owner
     * @return
     */
    static WeatherComponent create(ComponentOwner owner) {

        WeatherComponent instance = null;
        if (owner instanceof FragmentActivity)
            instance = ViewModelProviders.of((FragmentActivity) owner).get(WeatherComponent.class);
        else if (owner instanceof Fragment)
            instance = ViewModelProviders.of((Fragment) owner).get(WeatherComponent.class);

        if (instance != null) {
            instance.mComponentOwner = owner;
            return instance;
        } else
            throw new ClassCastException();
    }

    /**
     * @param weatherComponent
     * @param forecastComponent
     */
    @Override
    public void onDataReceived(final CurrentWeatherData weatherComponent, final ForecastData forecastComponent) {
        if (weatherComponent != null && mCurrentWeatherData != null)
            mCurrentWeatherData.postValue(weatherComponent);
        if (forecastComponent != null && mForecastData != null)
            mForecastData.postValue(forecastComponent);
    }

    @Override
    public void onDataReceived(List<CurrentWeatherData> weatherDataList) {
        if (weatherDataList != null && mWeatherDataList != null)
            mWeatherDataList.postValue(weatherDataList);
    }

    /**
     * @param cause
     */
    @Override
    public void onFailure(String cause) {
        mComponentOwner.onError(cause);
    }

    /**
     * Request for weather details (daily, weekly) forecast
     * by giving the place name as string value
     *
     * @param place
     */
    public void requestForWeatherData(String place) {
        mWeatherService.requestForData(place);
    }

    /**
     * Request weather data for all stored locations
     *
     */
    public void requestForAll() {
        mWeatherService.requestForAllData();
    }

    /**
     * Request for an accurate weather details (daily, weekly) forecast
     * by giving the latitude and longitude
     *
     * @param lat
     * @param lon
     */
    public void requestForWeatherData(double lat, double lon) {
        mWeatherService.requestForData(lat, lon);
    }

    public LiveData<CurrentWeatherData> getCurrentWeatherData() {
        if (mCurrentWeatherData == null)
            mCurrentWeatherData = new MutableLiveData<CurrentWeatherData>();
        return mCurrentWeatherData;
    }

    public LiveData<ForecastData> getForecastData() {
        if (mForecastData == null)
            mForecastData = new MutableLiveData<ForecastData>();
        return mForecastData;
    }

    public LiveData<List<CurrentWeatherData>> getWeatherDataList() {
        if (mWeatherDataList == null)
            mWeatherDataList = new MutableLiveData<>();
        return mWeatherDataList;
    }
}
