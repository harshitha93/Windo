package com.kortain.windo.domain.components;

import android.arch.lifecycle.LifecycleOwner;


public interface ComponentOwner extends LifecycleOwner {

    void onError(String cause);
}
