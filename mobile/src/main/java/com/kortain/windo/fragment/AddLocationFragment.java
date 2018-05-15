package com.kortain.windo.fragment;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.kortain.windo.R;
import com.kortain.windo.adapter.LocationsAdapter;
import com.kortain.windo.domain.components.ComponentFactory;
import com.kortain.windo.domain.components.ComponentOwner;
import com.kortain.windo.domain.components.WeatherComponent;
import com.kortain.windo.domain.models.CurrentWeatherData;
import com.kortain.windo.model.LocationModel;
import com.kortain.windo.utils.ConversionUtility;
import com.kortain.windo.utils.PreferenceManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddLocationFragment extends Fragment implements LocationsAdapter.LocationAdapterCallbacks, ComponentOwner {

    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 11;
    private final String DEGREE = "\u00b0";
    @BindView(R.id.fl_locations_recycler_view)
    RecyclerView mLocationsRecyclerView;
    private Place place;
    private WeatherComponent mWeatherComponent;
    private List<LocationModel> mLocationModels;

    public AddLocationFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AddLocationFragment newInstance() {
        return new AddLocationFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mWeatherComponent = ComponentFactory.create(this, WeatherComponent.class);
        mLocationModels = new ArrayList<>();
        mLocationModels.add(new LocationModel(null, null, null, null, false, true));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_locations, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mWeatherComponent.getWeatherDataList().observe(this, new Observer<List<CurrentWeatherData>>() {
            @Override
            public void onChanged(@Nullable List<CurrentWeatherData> currentWeatherDataList) {
                if (currentWeatherDataList != null &&
                        !currentWeatherDataList.isEmpty())
                    bindData(currentWeatherDataList);
            }
        });

        refresh(null);
    }

    @Override
    public void onAddNewItemClicked() {
        requestLocationSuggestions();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                place = PlaceAutocomplete.getPlace(getContext(), data);
                refresh(place.getName().toString());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getContext(), data);
                Toast.makeText(getContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    @Override
    public void onError(String cause) {
        Toast.makeText(getContext(), cause, Toast.LENGTH_SHORT).show();
    }

    /**
     * Show the location suggestions by requesting one intent
     */
    private void requestLocationSuggestions() {

        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                            .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);

        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException ex) {
            Toast.makeText(getContext(), ex.getCause().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Refresh the location list adapter after adding a new location
     *
     * @param s
     */
    private void refresh(String s) {

        mWeatherComponent.getCurrentWeatherData().observe(this, new Observer<CurrentWeatherData>() {
            @Override
            public void onChanged(@Nullable CurrentWeatherData currentWeatherData) {
                if (currentWeatherData != null) {
                    PreferenceManager.addNewLocation(currentWeatherData.getLocation());
                    mWeatherComponent.requestForAll();
                }
            }
        });

        if (s == null)
            mWeatherComponent.requestForAll();
        else
            mWeatherComponent.requestForWeatherData(s);
    }

    /**
     * Bind the location list model with the adapter
     *
     * @param currentWeatherDataList
     */
    private void bindData(@NonNull List<CurrentWeatherData> currentWeatherDataList) {

        mLocationModels = map(currentWeatherDataList);

        LocationsAdapter adapter = new LocationsAdapter(this, getContext(), mLocationModels);
        mLocationsRecyclerView.setAdapter(adapter);
        mLocationsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * Map the current weather list into adapter model
     *
     * @param currentWeatherDataList
     * @return
     */
    private List<LocationModel> map(List<CurrentWeatherData> currentWeatherDataList) {

        for (CurrentWeatherData weatherData :
                currentWeatherDataList) {

            if (!isExists(weatherData.getLocation())) {

                LocationModel model = new LocationModel();
                model.setPlace(weatherData.getLocation());
                model.setTemperature(ConversionUtility.kelvinToCelsius(weatherData.getCurrentTemperature()) + DEGREE);
                model.setLabel(false);
                model.setTime(ConversionUtility.getTime(Calendar.getInstance().getTime()));
                if (PreferenceManager.getLastKnownLocation().equalsIgnoreCase(weatherData.getLocation()))
                    model.setCurrentLocation(true);
                else
                    model.setCurrentLocation(false);

                mLocationModels.add(model);
            }

        }
        return mLocationModels;
    }

    private boolean isExists(@NonNull String location) {
        boolean flag = false;

        if (mLocationModels != null && !mLocationModels.isEmpty()) {
            for (LocationModel model :
                    mLocationModels) {

                if (model.getPlace() != null &&
                        model.getPlace().toLowerCase().contains(location.toLowerCase()))
                    flag = true;

            }
        }
        return flag;
    }
}
