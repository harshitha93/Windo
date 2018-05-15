package com.kortain.windo.fragment;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.kortain.windo.R;
import com.kortain.windo.adapter.DailyForecastAdapter;
import com.kortain.windo.domain.components.ComponentFactory;
import com.kortain.windo.domain.components.ComponentOwner;
import com.kortain.windo.domain.components.WeatherComponent;
import com.kortain.windo.domain.models.CurrentWeatherData;
import com.kortain.windo.domain.models.ForecastData;
import com.kortain.windo.model.ForecastModel;
import com.kortain.windo.utils.ConversionUtility;
import com.kortain.windo.utils.EqualSpacingItemDecoration;
import com.kortain.windo.utils.IconsUtility;
import com.kortain.windo.utils.PreferenceManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class DashboardFragment extends Fragment implements ComponentOwner {

    private static final String ARG_LOCATION_PLACE = "arg_place";
    private static final String ARG_LOCATION_LATITUDE = "arg_location_lat";
    private static final String ARG_LOCATION_LONGITUDE = "arg_location_lon";
    private final String DEGREE = "\u00b0";
    @BindView(R.id.cw_current_temp)
    TextView mCurrentTemperatureView;
    @BindView(R.id.cw_min_temp)
    TextView mMinTemperatureView;
    @BindView(R.id.cw_max_temp)
    TextView mMaxTemperatureView;
    @BindView(R.id.cw_humidity)
    TextView mHumidityView;
    @BindView(R.id.cw_wind)
    TextView mWindView;
    @BindView(R.id.cw_pressure)
    TextView mPressureView;
    @BindView(R.id.cs_sunrise)
    TextView mSunriseTimeView;
    @BindView(R.id.cw_sunset)
    TextView mSunsetTimeView;
    @BindView(R.id.cw_description)
    TextView mWeatherDescriptionView;
    @BindView(R.id.cw_icon)
    ImageView mWeatherIconView;
    @BindView(R.id.cw_seekBar)
    SeekBar mSeekBar;
    @BindView(R.id.fd_day)
    TextView mDay;
    @BindView(R.id.fd_place)
    TextView mPlace;
    @BindView(R.id.fd_location_icon)
    ImageView mLocationIcon;
    @BindView(R.id.cdf_daily_forecast_recycler_view)
    RecyclerView mDailyForecastRecyclerView;
    @BindView(R.id.cdf_progressBar)
    ProgressBar dailyForecastLoader;

    WeakReference<CurrentWeatherData> mTodayWeatherData;

    private WeatherComponent mWeatherComponent;
    private String place;
    private double latitude;
    private double longitude;

    public DashboardFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DashboardFragment newInstance(String place) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_LOCATION_PLACE, place);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static DashboardFragment newInstance(double latitude, double longitude) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_LOCATION_LATITUDE, latitude);
        args.putDouble(ARG_LOCATION_LONGITUDE, longitude);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Creates WeatherComponent instance using the ComponentFactory
        mWeatherComponent = ComponentFactory.create(this, WeatherComponent.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null) {

            if (bundle.containsKey(ARG_LOCATION_PLACE))
                place = bundle.getString(ARG_LOCATION_PLACE);
            else if (bundle.containsKey(ARG_LOCATION_LONGITUDE) &&
                    bundle.containsKey(ARG_LOCATION_LATITUDE)) {

                latitude = bundle.getDouble(ARG_LOCATION_LATITUDE);
                longitude = bundle.getDouble(ARG_LOCATION_LONGITUDE);
            }
        }

        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        //Bind the view component with field
        ButterKnife.bind(this, view);
        view.setVisibility(View.INVISIBLE);

        mWeatherComponent.getCurrentWeatherData().observe(this, new Observer<CurrentWeatherData>() {
            @Override
            public void onChanged(@Nullable CurrentWeatherData currentWeatherData) {

                if (currentWeatherData != null) {
                    mTodayWeatherData = new WeakReference<>(currentWeatherData);

                    if (latitude != 0 && longitude != 0) {
                        if (!PreferenceManager.getLastKnownLocation().toLowerCase().equalsIgnoreCase(currentWeatherData.getLocation())) {
                            view.setVisibility(View.VISIBLE);
                            bindWeatherDataData(currentWeatherData);
                            PreferenceManager.setLastKnownLocation(currentWeatherData.getLocation());
                        }
                    } else {
                        view.setVisibility(View.VISIBLE);
                        bindWeatherDataData(currentWeatherData);
                    }
                }
            }
        });

        mWeatherComponent.getForecastData().observe(this, new Observer<ForecastData>() {
            @Override
            public void onChanged(@Nullable ForecastData forecastData) {

                view.setVisibility(View.VISIBLE);
                if (forecastData != null)
                    bindForecastData(forecastData);
            }
        });

        if (place != null && !place.isEmpty()) {
            mLocationIcon.setVisibility(View.GONE);
            mWeatherComponent.requestForWeatherData(place);
        } else if (latitude != 0 && longitude != 0) {
            mWeatherComponent.requestForWeatherData(latitude, longitude);
        }

        setLocationIcon();
    }

    /**
     * Bind forecast data with the view component
     * daily forecast data and weekly forecast data
     *
     * @param forecastData
     */
    @SuppressWarnings("unchecked")
    @SuppressLint("StaticFieldLeak")
    private void bindForecastData(final ForecastData forecastData) {

        dailyForecastLoader.setVisibility(View.VISIBLE);

        final AsyncTask<List<CurrentWeatherData>, Void, List<ForecastModel>> dailyForecastTask = new AsyncTask<List<CurrentWeatherData>, Void, List<ForecastModel>>() {
            @Override
            protected List<ForecastModel> doInBackground(List<CurrentWeatherData>... lists) {

                List<ForecastModel> forecastModelList = new ArrayList<>();
                List<CurrentWeatherData> dailyForecast = lists[0];
                if (!dailyForecast.isEmpty() && mTodayWeatherData.get() != null) {


                    for (CurrentWeatherData weatherData :
                            dailyForecast) {

                        ForecastModel forecastModel = new ForecastModel();
                        forecastModel.setTime(ConversionUtility.getHour(weatherData.getTime()));
                        forecastModel.setCurrentTemperature(ConversionUtility.kelvinToCelsius(weatherData.getCurrentTemperature()) + DEGREE);
                        forecastModel.setIcon(IconsUtility.getInstance(getContext()).getWeatherIcon(weatherData.getDescription()));

                /*if (new Date(weatherData.getTime()).getDay() > new Date(Calendar.getInstance().getTimeInMillis()).getDay())
                    forecastModel.setIcon(IconsUtility.getWeatherIcon(getContext(), weatherData.getDescription()));
                else if (weatherData.getTime() > new Date(mTodayWeatherData.get().getSunrise() * 1000L).getTime() &&
                        weatherData.getTime() < new Date(mTodayWeatherData.get().getSunset() * 1000L).getTime())
                    forecastModel.setIcon(IconsUtility.getWeatherIcon(getContext(), weatherData.getDescription()));
                else if (weatherData.getTime() > new Date(mTodayWeatherData.get().getSunset() * 1000L).getTime())
                    forecastModel.setIcon(getContext().getResources().getDrawable(R.drawable.ic_moon));*/

                        forecastModelList.add(forecastModel);
                    }
                }
                return forecastModelList;
            }

            @Override
            protected void onPostExecute(List<ForecastModel> forecastModels) {
                dailyForecastLoader.setVisibility(View.GONE);
                mDailyForecastRecyclerView.setAdapter(new DailyForecastAdapter(getContext(), forecastModels));
                mDailyForecastRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                mDailyForecastRecyclerView.addItemDecoration(new EqualSpacingItemDecoration(16));
            }
        };

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dailyForecastTask.execute(forecastData.getDailyForecast());
            }
        }, 1000);

    }

    /**
     * @param cause
     */
    @Override
    public void onError(final String cause) {
        final View view = getView();
        if (view != null) {
            Snackbar.make(view, cause, Snackbar.LENGTH_SHORT).setActionTextColor(getResources().getColor(R.color.colorPrimary)).show();
        }
    }

    /**
     * @param result
     */
    @SuppressLint("SetTextI18n")
    private void bindWeatherDataData(CurrentWeatherData result) {

        if (result != null) {
            mCurrentTemperatureView.setText(ConversionUtility.kelvinToCelsius(result.getCurrentTemperature()) + DEGREE);
            mWeatherDescriptionView.setText(String.valueOf(result.getDescription().charAt(0)).toUpperCase()+result.getDescription().substring(1, result.getDescription().length()));
            mMinTemperatureView.setText(ConversionUtility.kelvinToCelsius(result.getMinTemperature()) + DEGREE);
            mMaxTemperatureView.setText(ConversionUtility.kelvinToCelsius(result.getMaxTemperature()) + DEGREE);
            mSunriseTimeView.setText(ConversionUtility.getTime(result.getSunrise()));
            mSunsetTimeView.setText(ConversionUtility.getTime(result.getSunset()));
            mWindView.setText(ConversionUtility.mps_to_kmph(result.getWind()) + " km/hr");
            mPressureView.setText(result.getPressure() + " hPa");
            mHumidityView.setText(result.getHumidity() + "%");

            mWeatherIconView.setImageDrawable(IconsUtility.getInstance(getContext()).getWeatherIcon(result.getDescription()));

            mDay.setText("Today");
            mPlace.setText(result.getLocation() + " | " + result.getCountry());

            mSeekBar.setMax(Integer.parseInt(ConversionUtility.getTime(result.getSunset()).replace(":", "")));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mSeekBar.setMin(Integer.parseInt(ConversionUtility.getTime(result.getSunrise()).replace(":", "")));
            }

            mSeekBar.setEnabled(false);
            mSeekBar.setProgress(Integer.parseInt(ConversionUtility.getTime(Calendar.getInstance().getTime()).replace(":", "")));

            /*final ValueAnimator anim = ValueAnimator.ofInt(0, Integer.parseInt(ConversionUtility.getTime(Calendar.getInstance().getTime()).replace(":", "")));
            anim.setDuration(2000);
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    animation.setInterpolator(new AnticipateOvershootInterpolator());
                    int animProgress = (Integer) animation.getAnimatedValue();
                    mSeekBar.setProgress(animProgress);
                }
            });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    anim.start();
                }
            }, 300);*/
        }
    }

    private void setLocationIcon() {
        if (place != null && !place.isEmpty()) {
            if (PreferenceManager.getLastKnownLocation().equalsIgnoreCase(place))
                mLocationIcon.setVisibility(View.VISIBLE);
        } else if (latitude != 0 && longitude != 0)
            mLocationIcon.setVisibility(View.VISIBLE);
    }
}
