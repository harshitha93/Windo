package com.kortain.windo.domain.storage;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kortain.windo.domain.entity.ForecastEntity;

import java.util.List;

/**
 * Created by satiswardash on 26/04/18.
 */

@Dao
public interface ForecastDaoService {

    @Query("SELECT * FROM ForecastEntity f ORDER BY f.forecast_timestamp DESC")
    List<ForecastEntity> findAllForecastEntityListDesc();

    @Query("SELECT * FROM ForecastEntity f ORDER BY f.forecast_timestamp DESC")
    LiveData<List<ForecastEntity>> findAllForecastEntityLiveListDesc();

    @Query("SELECT * FROM ForecastEntity f ORDER BY f.forecast_timestamp ASC")
    List<ForecastEntity> findAllForecastEntityListAsc();

    @Query("SELECT * FROM ForecastEntity f ORDER BY f.forecast_timestamp ASC")
    LiveData<List<ForecastEntity>> findAllForecastEntityLiveListAsc();

    @Query("SELECT * FROM ForecastEntity F WHERE LOWER(F.forecast_place)=:place ORDER BY F.forecast_timestamp DESC")
    List<ForecastEntity> findAllForecastEntityListDesc(String place);

    @Query("SELECT * FROM ForecastEntity F WHERE LOWER(F.forecast_place)=:place ORDER BY F.forecast_timestamp DESC")
    LiveData<List<ForecastEntity>> findAllForecastEntityLiveListDesc(String place);

    @Query("SELECT * FROM ForecastEntity F WHERE F.forecast_latitude = :lat AND F.forecast_longitude = :lon ORDER BY F.forecast_timestamp DESC")
    List<ForecastEntity> findAllForecastEntityListDesc(double lat, double lon);

    @Query("SELECT * FROM ForecastEntity F WHERE F.forecast_latitude = :lat AND F.forecast_longitude = :lon ORDER BY F.forecast_timestamp DESC")
    LiveData<List<ForecastEntity>> findAllForecastEntityLiveListAsc(double lat, double lon);

    @Query("SELECT * FROM ForecastEntity F WHERE LOWER(F.forecast_place)=:place ORDER BY F.forecast_timestamp ASC")
    List<ForecastEntity> findAllForecastEntityListAsc(String place);

    @Query("SELECT * FROM ForecastEntity F WHERE LOWER(F.forecast_place)=:place ORDER BY F.forecast_timestamp ASC")
    LiveData<List<ForecastEntity>> findAllForecastEntityLiveListAsc(String place);

    @Query("SELECT * FROM ForecastEntity F WHERE F.forecast_latitude = :lat AND F.forecast_longitude = :lon ORDER BY F.forecast_timestamp ASC")
    List<ForecastEntity> findAllForecastEntityListAsc(double lat, double lon);

    @Query("SELECT * FROM ForecastEntity F WHERE F.forecast_latitude = :lat AND F.forecast_longitude = :lon ORDER BY F.forecast_timestamp ASC")
    LiveData<List<ForecastEntity>> findAllForecastEntityLiveListDesc(double lat, double lon);

    @Update
    void updateForecastEntities(List<ForecastEntity> entityList);

    @Update
    void updateForecastEntity(ForecastEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertForecastEntities(List<ForecastEntity> entityList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertForecastEntity(ForecastEntity entity);

    @Delete
    int deleteForecastEntity(ForecastEntity entity);

    @Delete
    int deleteForecastEntities(List<ForecastEntity> entityList);
}
