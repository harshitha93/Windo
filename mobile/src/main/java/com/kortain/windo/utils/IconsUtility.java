package com.kortain.windo.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.kortain.windo.R;

public class IconsUtility {

    Context context;

    public IconsUtility(Context context) {

        this.context = context;
    }

    public static IconsUtility getInstance(Context context) {
        return new IconsUtility(context);
    }

    public Drawable getWeatherIcon(String description) {

        if (description.toLowerCase().contains("cloudy"))
            return context.getResources().getDrawable(R.drawable.ic_cloudy).mutate();
        else if (description.toLowerCase().contains("cloud"))
            return context.getResources().getDrawable(R.drawable.ic_cloud).mutate();
        else if (description.toLowerCase().contains("rain"))
            return context.getResources().getDrawable(R.drawable.ic_rain).mutate();
        else if (description.toLowerCase().contains("thunderstorm"))
            return context.getResources().getDrawable(R.drawable.ic_thunderstorm).mutate();
        else if (description.toLowerCase().contains("thunder"))
            return context.getResources().getDrawable(R.drawable.ic_thunder).mutate();
        else if (description.toLowerCase().contains("clear"))
            return context.getResources().getDrawable(R.drawable.ic_sunny).mutate();
        else if (description.toLowerCase().contains("sun"))
            return context.getResources().getDrawable(R.drawable.ic_sunny).mutate();
        else if (description.toLowerCase().contains("snow"))
            return context.getResources().getDrawable(R.drawable.ic_snowfall).mutate();
        else if (description.toLowerCase().contains("night"))
            return context.getResources().getDrawable(R.drawable.ic_moon).mutate();
        else
            return null;
    }

    public Drawable getIcon(Context context, String type) {

        switch (type) {

            case "01d": {
                //return context.getResources().getDrawable(R.drawable.ic_w_day);
            }
            case "01n": {
                //return context.getResources().getDrawable(R.drawable.ic_w_night);
            }
            case "02d": {
                break;
            }
            case "02n": {
                break;
            }
            case "03d": {
                break;
            }
            case "03n": {
                break;
            }
            case "04d": {
                break;
            }
            case "04n": {
                break;
            }
            case "09d": {
                break;
            }
            case "09n": {
                break;
            }
            case "10d": {
                break;
            }
            case "10n": {
                break;
            }
            case "11d": {
                break;
            }case "11n": {
                break;
            }case "13d": {
                break;
            }
            case "13n": {
                break;
            }
            case "50d": {
                break;
            }
            case "50n": {
                break;
            }


        }

        return null;
    }
}
