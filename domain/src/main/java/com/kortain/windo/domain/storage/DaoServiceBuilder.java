package com.kortain.windo.domain.storage;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kortain.windo.domain.entity.CurrentWeatherEntity;
import com.kortain.windo.domain.entity.ForecastEntity;
import com.kortain.windo.domain.utility.Constants;

/**
 * Created by satiswardash on 25/04/18.
 */

@Database(entities = {CurrentWeatherEntity.class, ForecastEntity.class}, version = Constants.DB_VERSION)
public abstract class DaoServiceBuilder extends RoomDatabase{

    private static final Object sLock = new Object();
    private static DaoServiceBuilder INSTANCE;

    public abstract WeatherDoaService getWeatherDao();
    public abstract ForecastDaoService getForecastDao();

    /**
     * Creates a new database instance which is thread safe
     *
     * @param context
     * @return
     */
    public static DaoServiceBuilder getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        DaoServiceBuilder.class, Constants.DATABASE_NAME)
                        //TODO Remove the below line if you want to add DB migration between versions and add the Migration references accordingly
                        //TODO While performing DB migration don't forget to change the DB version
                        //.addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_1_4)
                        .build();
            }
            return INSTANCE;
        }
    }
}
