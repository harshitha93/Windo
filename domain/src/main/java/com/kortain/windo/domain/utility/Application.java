package com.kortain.windo.domain.utility;

import android.content.Context;

/**
 * Created by satiswardash on 25/04/18.
 */

public class Application extends android.app.Application {

    private static ApplicationDataComponent mDataComponent;
    private static android.app.Application instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        mDataComponent = DaggerApplicationDataComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public static ApplicationDataComponent getDataComponent() {
        return mDataComponent;
    }

    public static android.app.Application getApplicationInstance() {
        return instance;
    }

    public Context getContext() {
        return this;
    }
}
