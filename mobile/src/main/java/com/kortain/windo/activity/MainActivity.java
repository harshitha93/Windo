package com.kortain.windo.activity;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.kortain.windo.R;
import com.kortain.windo.domain.components.ComponentFactory;
import com.kortain.windo.domain.components.ComponentOwner;
import com.kortain.windo.domain.components.WeatherComponent;
import com.kortain.windo.domain.models.CurrentWeatherData;
import com.kortain.windo.fragment.DashboardFragment;
import com.kortain.windo.utils.NetworkUtility;
import com.kortain.windo.utils.PreferenceManager;

import java.lang.ref.WeakReference;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements
        GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,
        LocationListener, ComponentOwner {

    private final int REQUEST_LOCATION_PERMISSION = 11;
    boolean isGPSEnabled = false;
    @BindView(R.id.loader_view)
    LinearLayout mLoaderView;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @BindView(R.id.activity_main_root_container)
    ViewPager mViewPager;
    @BindView(R.id.activity_main_tab_layout)
    TabLayout mTabLayout;
    private int PAGE_SIZE = -1;
    private int mFineLocationPermission;
    private int mCoarseLocationPermission;
    private Location location;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // getting GPS status
        if (locationManager != null) {
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
        }

        if (!isGPSEnabled) {
            showSettingsAlert();
        } else {

            mFineLocationPermission =
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            mCoarseLocationPermission =
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        askPermission(REQUEST_LOCATION_PERMISSION);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!PreferenceManager.getLastKnownLocation().equalsIgnoreCase(PreferenceManager.EMPTY_STRING_DEFAULT_VALUE))
            initFragment();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient != null &&
                mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case REQUEST_LOCATION_PERMISSION: {
                requestLocation();
                break;
            }
        }
    }

    /**
     * Ask user's approval for Dangerous permissions
     */
    private void askPermission(int requestCode) {

        if (mFineLocationPermission == PackageManager.PERMISSION_GRANTED
                && mCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {


            if (requestCode == REQUEST_LOCATION_PERMISSION) {
                requestLocation();
            }
        } else {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    requestCode);
        }
    }

    private void requestLocation() {

        if (NetworkUtility.hasNetworkAccess(this)) {
            if (location == null) {
                if (mGoogleApiClient == null) {
                    buildGoogleApiClient();
                }
                createLocationRequest();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        if (location != null) {
            initFragment();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        try {
            if (mGoogleApiClient != null)
                mGoogleApiClient.connect();
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /**
     * Creates {@link GoogleApiClient} object using builder and LocationService.API
     */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    /**
     * Creates a {@link LocationRequest} object and initializing the interval frequency and accuracy
     */
    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        long mInterval = 60 * 1000;
        mLocationRequest.setInterval(mInterval);//30 seconds
        long mFastestInterval = 30 * 1000;
        mLocationRequest.setFastestInterval(mFastestInterval);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        float mDisplacement = 300;
        mLocationRequest.setSmallestDisplacement(mDisplacement);
    }

    public void showSettingsAlert() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is not enabled!");

        // Setting Dialog Message
        alertDialog.setMessage("Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                dialog.cancel();
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void addNewLocation(View view) {
        Intent intent = new Intent(this, AddLocationActivity.class);
        startActivity(intent);
    }

    public void refresh(View view) {

        mLoaderView.setVisibility(View.VISIBLE);

        if (NetworkUtility.hasNetworkAccess(this))
            initFragment();
        else
            Snackbar.make(Objects.requireNonNull(getCurrentFocus()), "No network, can't update!", Snackbar.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoaderView.setVisibility(View.GONE);
            }
        }, 500);
    }

    private void initFragment() {
        mLoaderView.setVisibility(View.VISIBLE);

        refreshPageSize();

        if (mSectionsPagerAdapter == null) {
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
            mTabLayout.setupWithViewPager(mViewPager, true);

            // Set up the ViewPager with the sections adapter.
            mViewPager.setAdapter(mSectionsPagerAdapter);
            mViewPager.setOffscreenPageLimit(PAGE_SIZE);
        } else {
            mSectionsPagerAdapter.notifyDataSetChanged();
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoaderView.setVisibility(View.GONE);
            }
        }, 500);
    }

    private void refreshPageSize() {
        if (PreferenceManager.getAllLocations() == null)
            PAGE_SIZE = 0;
        else
            PAGE_SIZE = PreferenceManager.getAllLocations().size();
    }

    @Override
    public void onError(String cause) {
        Toast.makeText(this, cause, Toast.LENGTH_SHORT).show();
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
                if (location != null)
                    return DashboardFragment.newInstance(location.getLatitude(), location.getLongitude());
                else
                    return DashboardFragment.newInstance(PreferenceManager.getLastKnownLocation());
            } else {
                String place = (String) PreferenceManager.getAllLocations().toArray()[position - 1];
                return DashboardFragment.newInstance(place);
            }
        }


        @Override
        public int getCount() {
            return PAGE_SIZE + 1;
        }


    }
}
