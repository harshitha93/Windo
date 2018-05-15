package com.kortain.windo.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ConversionUtility {

    /**
     * kelvinToCelsius
     *
     * @param temp
     * @return
     */
    public static int kelvinToCelsius(double temp) {

        double celsius;
        celsius = temp - 273.0;
        return (int) celsius;
    }

    /**
     * kelvinToFahrenheit
     *
     * @param temp
     * @return
     */
    public static int kelvinToFahrenheit(double temp) {

        double celsius, fahrenheit;
        celsius = temp - 273.0;
        fahrenheit = (celsius * 9.0 / 5.0) + 32.0;
        return (int) fahrenheit;
    }

    /**
     * function to convert speed
     * in km/hr to m/sec
     *
     * @param kmph
     * @return
     */
    public static int kmph_to_mps(double kmph) {
        return (int) (0.277778 * kmph);
    }

    /**
     * function to convert speed
     * in m/sec to km/hr
     *
     * @param mps
     * @return
     */
    public static int mps_to_kmph(double mps) {
        return (int) (3.6 * mps);
    }

    /**
     * Get the time in 24hours format
     * from a date that is is long format
     *
     * @param time
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTime(long time) {

        long unixSeconds = time;
        // convert seconds to milliseconds
        Date date = new Date(unixSeconds * 1000L);
        // the format of your date
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        // give a timezone reference for formatting (see comment at the bottom)
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);

        return formattedDate;
    }

    public static String getTime(Date date) {

        // the format of your date
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        // give a timezone reference for formatting (see comment at the bottom)
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);

        return formattedDate;
    }

    /**
     *
     * @param time
     * @return
     */
    public static String getHour(long time) {

        Date date = new Date(time);
        // the format of your date
        SimpleDateFormat sdf = new SimpleDateFormat("HH", Locale.ENGLISH);
        // give a timezone reference for formatting (see comment at the bottom)
        //sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);

        return formattedDate;
    }
}
