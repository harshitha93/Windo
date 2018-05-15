package com.kortain.windo.domain.components;

import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

public abstract class ComponentFactory {


    @SuppressWarnings("unchecked")
    public static <T extends ViewModel> T create(@NonNull ComponentOwner owner, @NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(WeatherComponent.class)) {
            return (T) WeatherComponent.create(owner);
        }
        else
            throw new ClassCastException("Unable to find ViewModel");
    }
}
