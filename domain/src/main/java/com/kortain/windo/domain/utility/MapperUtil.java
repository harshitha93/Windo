package com.kortain.windo.domain.utility;

import android.support.annotation.NonNull;

import com.kortain.windo.domain.entity.CurrentWeatherEntity;
import com.kortain.windo.domain.entity.ForecastEntity;
import com.kortain.windo.domain.models.CurrentWeatherData;
import com.kortain.windo.domain.serializables.City;
import com.kortain.windo.domain.serializables.Coordinate;
import com.kortain.windo.domain.serializables.CurrentWeatherResponse;
import com.kortain.windo.domain.serializables.Details;
import com.kortain.windo.domain.serializables.ForecastResponse;
import com.kortain.windo.domain.serializables.Main;
import com.kortain.windo.domain.serializables.System;
import com.kortain.windo.domain.serializables.Weather;
import com.kortain.windo.domain.serializables.Wind;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by satiswardash on 25/04/18.
 */

public class MapperUtil {

    /**
     *
     * @param mapObject
     * @return
     */
    public static CurrentWeatherEntity map(@NonNull CurrentWeatherResponse mapObject) {

        CurrentWeatherEntity weatherEntity = new CurrentWeatherEntity();
        if (mapObject.getMain() != null) {
            Main main = mapObject.getMain();

            weatherEntity.setCurrentTemperature(main.getTemperature());
            weatherEntity.setHumidity((int) main.getHumidity());
            weatherEntity.setMaxTemperature(main.getMaximumTemperature());
            weatherEntity.setMinTemperature(main.getMinimumTemperature());
            weatherEntity.setPressure((int) main.getPressure());
        }
        if (mapObject.getWeather() != null &&
                !mapObject.getWeather().isEmpty()) {
            Details weather = mapObject.getWeather().get(0);

            weatherEntity.setDescription(weather.getDescription());
            weatherEntity.setIcon(weather.getIcon());
            weatherEntity.setState(weather.getMain());
        }
        if (mapObject.getSys() != null) {
            System system = mapObject.getSys();

            if (system.getCountry() != null &&
                    !system.getCountry().isEmpty())
                weatherEntity.setCountry(system.getCountry());

            weatherEntity.setSunrise(system.getSunrise());
            weatherEntity.setSunset(system.getSunset());
        }
        if (mapObject.getCoordinate() != null) {
            Coordinate coordinate = mapObject.getCoordinate();

            weatherEntity.setLatitude(coordinate.getLatitude());
            weatherEntity.setLongitude(coordinate.getLongitude());
        }
        if (mapObject.getName() != null &&
                !mapObject.getName().isEmpty())
            weatherEntity.setPlace(mapObject.getName());

        if (mapObject.getWind() != null) {
            Wind wind = mapObject.getWind();

            weatherEntity.setWind((int) wind.getSpeed());
        }
        weatherEntity.setTimestamp(Calendar.getInstance().getTime().getTime());
        return weatherEntity;
    }

    /**
     *
     * @param mapObject
     * @return
     */
    public static List<ForecastEntity> map(ForecastResponse mapObject) {

        List<ForecastEntity> forecastEntities = new ArrayList<>();

        for (Weather weather :
                mapObject.getList()) {

            ForecastEntity forecastEntity = new ForecastEntity();
            if (weather.getMain() != null) {
                Main main = weather.getMain();

                forecastEntity.setCurrentTemperature(main.getTemperature());
                forecastEntity.setMaxTemperature(main.getMaximumTemperature());
                forecastEntity.setMinTemperature(main.getMinimumTemperature());
            }
            if (weather.getWeather() != null &&
                    !weather.getWeather().isEmpty()) {
                Details details = weather.getWeather().get(0);

                forecastEntity.setDescription(details.getDescription());
                forecastEntity.setIcon(details.getIcon());
                forecastEntity.setState(details.getMain());
            }
            if (weather.getSys() != null) {
                System system = weather.getSys();

                if (system.getCountry() != null &&
                        !system.getCountry().isEmpty())
                    forecastEntity.setCountry(system.getCountry());

            }
            if (weather.getCoordinate() != null) {
                Coordinate coordinate = weather.getCoordinate();

                forecastEntity.setLatitude(coordinate.getLatitude());
                forecastEntity.setLongitude(coordinate.getLongitude());
            }
            if (mapObject.getCity() != null) {
                City city = mapObject.getCity();

                if (city.getName() != null &&
                        !city.getName().isEmpty())
                    forecastEntity.setPlace(city.getName());
            }
            if (weather.getDate() != null &&
                    !weather.getDate().isEmpty())
                forecastEntity.setTimestamp(Utilities.getTime(weather.getDate()));

            forecastEntity.setCreatedAt(Calendar.getInstance().getTime().getTime());
            forecastEntities.add(forecastEntity);
        }


        return forecastEntities;
    }
}
