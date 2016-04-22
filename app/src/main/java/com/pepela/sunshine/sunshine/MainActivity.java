package com.pepela.sunshine.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements ForecastFragment.Callback {

    private static final String FORECASTFRAGMENT_TAG = "FORECAST_FRAGMENT_TAG";
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private String mLocation;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (findViewById(R.id.detail_fragment_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_fragment_container, new DetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        }


        ForecastFragment forecastFragment = ((ForecastFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_forecast));
        forecastFragment.setUseTodayLayout(!mTwoPane);

        mLocation = Utility.getPreferredLocation(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        String location = Utility.getPreferredLocation(this);

        if (location != null && !location.equals(mLocation)) {
            ForecastFragment ff = (ForecastFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_forecast);

            if (ff != null)
                ff.onLocationChanged();
            DetailFragment df = (DetailFragment) getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
            if (null != df) {
                df.onLocationChanged(location);
            }
            mLocation = Utility.getPreferredLocation(this);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_view_location_on_map:
                openLocationInMap();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void openLocationInMap() {
        Geocoder geoCoder = new Geocoder(this, Locale.getDefault());
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String location = sharedPreferences.getString(
                    getString(R.string.pref_location_key),
                    getString(R.string.pref_location_default));

            List<Address> addresses = geoCoder.getFromLocationName(
                    location, 1);
            if (addresses.size() > 0) {
                String coordinates = "geo:" + String.valueOf(addresses.get(0).getLatitude()) + "," + String.valueOf(addresses.get(0).getLongitude());
                Intent i = new
                        Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(coordinates));

                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                } else {
                    Log.e(MainActivity.class.getSimpleName(), "Error starting map intent.");
                }
            }
        } catch (IOException e) {
            Log.e(MainActivity.class.getSimpleName(), "Error getting location: " + e.getMessage());
        }
    }

    @Override
    public void onItemSelected(Uri dateUri) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(DetailFragment.DETAIL_URI, dateUri);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class)
                    .setData(dateUri);
            startActivity(intent);
        }
    }
}
