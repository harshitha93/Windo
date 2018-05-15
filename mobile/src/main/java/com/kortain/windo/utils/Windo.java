package com.kortain.windo.utils;


import android.util.Log;

import com.kortain.windo.domain.utility.Application;

import java.util.Set;

public class Windo extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        PreferenceManager.init(this);
    }
}
