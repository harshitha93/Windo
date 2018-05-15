package com.kortain.windo.domain.utility;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by satiswardash on 26/04/18.
 */

public class Utilities {

    private static final String TAG = Utilities.class.toString();

    /**
     * Check whether the last updated timestamp is older than 10  mins or not
     *
     * @param lastUpdated
     * @return
     */
    public static boolean isOlder(long lastUpdated) {

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long different = 0;

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-M-dd HH:mm:ss");

        long current = Calendar.getInstance().getTime().getTime();

        try {
            String d1 = simpleDateFormat.format(new Date(current));
            String d2 = simpleDateFormat.format(new Date(lastUpdated));

            //milliseconds
            different = simpleDateFormat.parse(d1).getTime() - simpleDateFormat.parse(d2).getTime();

            different = different / minutesInMilli;

        } catch (ParseException e) {
            //e.printStackTrace();
        }

        return different >= 10;
    }

    /**
     * @param date
     * @return
     */
    public static long getTime(String date) {

        long time = 0;
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-M-dd HH:mm:ss");

        try {
            Date d = simpleDateFormat.parse(date);
            time = d.getTime();

            //Log.i(TAG, "Utilities_getTime: "+d.toString());
            //Log.i(TAG, "Utilities_getTime: " + new Date(time).toString());

        } catch (ParseException e) {
            //e.printStackTrace();
        }
        return time;
    }

    /**
     *
     * @param date
     * @return
     */
    public static long nextDayStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.add(Calendar.DATE, 1);

        return cal.getTimeInMillis();
    }
}
