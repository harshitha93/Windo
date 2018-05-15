package com.kortain.windo.domain.utility;

import android.content.Context;

import com.kortain.windo.domain.network.NetworkService;
import com.kortain.windo.domain.network.NetworkServiceBuilder;
import com.kortain.windo.domain.storage.DaoServiceBuilder;
import com.kortain.windo.domain.storage.ForecastDaoService;
import com.kortain.windo.domain.storage.WeatherDoaService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by satiswardash on 25/04/18.
 */

@Module
public class ApplicationModule {

    private Context mContext;
    private NetworkService mOWMApiService;
    private WeatherDoaService mWeatherDaoService;
    private ForecastDaoService mForecastDaoService;

    public ApplicationModule(Context context) {
        mContext = context;
        mOWMApiService = NetworkServiceBuilder.build();
        mWeatherDaoService = DaoServiceBuilder.getInstance(mContext).getWeatherDao();
        mForecastDaoService = DaoServiceBuilder.getInstance(mContext).getForecastDao();
    }

    @Provides
    public ForecastDaoService provideForecastDaoService() {
        return mForecastDaoService;
    }

    @Provides
    NetworkUtility provideNetworkUtility(Context context) {
        return new NetworkUtility(context);
    }

    @Provides
    WeatherDoaService provideWeatherDbService() {
        return mWeatherDaoService;
    }

    @Provides
    NetworkService provideOWMApiServices() {
        return mOWMApiService;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
