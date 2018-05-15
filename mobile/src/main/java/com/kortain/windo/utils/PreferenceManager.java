package com.kortain.windo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class PreferenceManager {

    public static final String EMPTY_STRING_DEFAULT_VALUE = "null";
    private static final String PREFERENCE_NAME = "WINDO_PREFS";
    private static final String LAST_UPDATED_CURRENT_LOCATION = "last_known_current_location";
    private static final String ALL_LOCATIONS = "all_location";

    private static SharedPreferences _sharedPreferences;

    /**
     * Initialize the shared preference manager from the {@link Context}
     *
     * @param context
     */
    public static void init(Context context) {
        if (_sharedPreferences == null) {
            /** Get the shared preferences object for MD. */
            _sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        }
    }

    /**
     * Get the shared preference instance
     *
     * @return
     */
    public static SharedPreferences getSharedPreferenceInstance(Context context) {
        init(context);
        return _sharedPreferences;
    }

    /**
     * @param value
     */
    public static void addNewLocation(String value) {
        Set<String> results = getAllLocations();
        if (results == null) {
            Set<String> locations = new HashSet<>();
            locations.add(value.toLowerCase());
            SharedPreferences.Editor _editor = _sharedPreferences.edit();
            _editor.putStringSet(ALL_LOCATIONS, locations);
            _editor.apply();
            _editor = null;
        } else if (!results.contains(value.toLowerCase())) {
            Set<String> locations = new HashSet<>(results);
            locations.add(value);
            SharedPreferences.Editor _editor = _sharedPreferences.edit();
            _editor.putStringSet(ALL_LOCATIONS, locations);
            _editor.apply();
            _editor = null;
        }
    }

    /**
     * @return
     */
    public static String getLastKnownLocation() {
        return _sharedPreferences.getString(LAST_UPDATED_CURRENT_LOCATION, EMPTY_STRING_DEFAULT_VALUE);
    }

    /**
     * @param value
     */
    public static void setLastKnownLocation(String value) {
        SharedPreferences.Editor _editor = _sharedPreferences.edit();
        _editor.putString(LAST_UPDATED_CURRENT_LOCATION, value.toLowerCase());
        _editor.apply();
        _editor = null;
    }

    /**
     * @return
     */
    public static Set<String> getAllLocations() {
        return _sharedPreferences.getStringSet(ALL_LOCATIONS, null);
    }
}
