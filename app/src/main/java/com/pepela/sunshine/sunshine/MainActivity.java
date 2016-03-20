package com.pepela.sunshine.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
}
