package com.kortain.windo.domain.storage;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kortain.windo.domain.entity.CurrentWeatherEntity;

import java.util.List;

/**
 * Created by satiswardash on 24/04/18.
 */

@Dao
public interface WeatherDoaService {

    @Query("SELECT * FROM CurrentWeatherEntity")
    List<CurrentWeatherEntity> findAllCurrentWeatherEntityList();

    @Query("SELECT * FROM CurrentWeatherEntity")
    LiveData<List<CurrentWeatherEntity>> findAllCurrentWeatherEntityLiveList();

    @Query("SELECT * FROM CurrentWeatherEntity W WHERE LOWER(W.weather_place)=:place LIMIT 1")
    CurrentWeatherEntity findCurrentWeatherEntityByLocation(String place);

    @Query("SELECT * FROM CurrentWeatherEntity W WHERE LOWER(W.weather_place)=:place LIMIT 1")
    LiveData<CurrentWeatherEntity> findCurrentWeatherLiveEntityByLocation(String place);

    @Update
    void updateCurrentWeatherEntity(CurrentWeatherEntity entity);

    @Update
    void updateCurrentWeatherEntities(List<CurrentWeatherEntity> entities);

    @Delete
    void deleteCurrentWeatherEntity(CurrentWeatherEntity entity);

    @Delete
    void deleteAllCurrentWeatherEntity(List<CurrentWeatherEntity> entities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrentWeatherEntity(CurrentWeatherEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCurrentWeatherEntities(List<CurrentWeatherEntity> entities);
}
