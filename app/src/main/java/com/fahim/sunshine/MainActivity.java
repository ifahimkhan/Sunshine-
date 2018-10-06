/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fahim.sunshine;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.sunshine.yahoomodel.LocationResult;
import com.example.android.sunshine.yahoomodel.YahooObject;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity implements
        ForecastAdapter.ForecastAdapterOnClickHandler,
        GeocodingServiceListener,
        LocationListener {
    public static int GET_WEATHER_FROM_CURRENT_LOCATION = 0x00001;
    String location = null;

    SwipeRefreshLayout swipeRefreshLayout;

    YahooObject yahooObject = new YahooObject();
    private final String TAG = MainActivity.class.getSimpleName();

    private SharedPreferences preferences, Settingpreferences;
    private SharedPreferences.Editor editor;
    private GoogleMapsGeocodingService geocodingService;

    private ForecastAdapter mForecastAdapter;
    private RecyclerView mRecyclerView;
    private int mPosition = RecyclerView.NO_POSITION;

    private ProgressBar mprogressbar, mLoadingIndicator;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);


        geocodingService = new GoogleMapsGeocodingService((GeocodingServiceListener) this);
        String l = loadLocation();

        getSupportActionBar().setElevation(0f);
        mprogressbar = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mprogressbar.setVisibility(View.VISIBLE);
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        preferences = getSharedPreferences("MySunshine", 0);
        Settingpreferences = PreferenceManager.getDefaultSharedPreferences(this);


        //String unit=preferences.getString(getString(R.string.pref_units_cel),"F");

        /*
         * Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
         * do things like set the adapter of the RecyclerView and toggle the visibility.
         */
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_forecast);

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         *
         * Please note: This so called "ProgressBar" isn't a bar by default. It is more of a
         * circle. We didn't make the rules (or the names of Views), we just follow them.
         */
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mLoadingIndicator.setVisibility(View.VISIBLE);
        /*
         * A LinearLayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView into a linear list. This means that it can produce either a horizontal or
         * vertical list depending on which parameter you pass in to the LinearLayoutManager
         * constructor. In our case, we want a vertical list, so we pass in the constant from the
         * LinearLayoutManager class for vertical lists, LinearLayoutManager.VERTICAL.
         *
         * There are other LayoutManagers available to display your data in uniform grids,
         * staggered grids, and more! See the developer documentation for more details.
         *
         * The third parameter (shouldReverseLayout) should be true if you want to reverse your
         * layout. Generally, this is only true with horizontal lists that need to support a
         * right-to-left layout.
         */
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        /* setLayoutManager associates the LayoutManager we created above with our RecyclerView */
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);


        mForecastAdapter = new ForecastAdapter(this, this);


        if (l != null) {
            loadAPI(l);
        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                location = loadLocation();

                if (location != null) {
                    loadAPI(location);
                } else {
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    private String loadLocation() {
        if (Settingpreferences.getBoolean(getString(R.string.pref_geolocation_enabled), false)) {
            String locationCache = Settingpreferences.getString(getString(R.string.pref_cached_location), null);

            if (locationCache == null) {
                getWeatherFromCurrentLocation();
            } else {
                location = locationCache;
            }
        } else {
            location = Settingpreferences.getString(getString(R.string.pref_manual_location), null);
        }

        return location;
    }

    private void loadAPI(String location) {
        String unit = Settingpreferences.getString(getString(R.string.pref_temperature_unit), null);

        unit = unit.equalsIgnoreCase("f") ? "f" : "c";
        if (unit.equals("c")) {

            Toast.makeText(this, "Temperature in Celsius", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Temperature in Fahrenheit", Toast.LENGTH_SHORT).show();
        }
        String YQL = String.format("select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"%s\") and u='" + unit + "'", location);

        String endpoint = String.format("https://query.yahooapis.com/v1/public/yql?q=%s&format=json", Uri.encode(YQL));

        new BackgroundWorker().execute(endpoint);

    }

    @Override
    public void onClick(int adapterPosition) {
        Intent weatherDetailIntent = new Intent(MainActivity.this, DetailActivity.class);
        weatherDetailIntent.putExtra("position", adapterPosition);
        startActivity(weatherDetailIntent);
    }




    /**
     * This is where we inflate and set up the menu for this Activity.
     *
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */

    private void getWeatherFromCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
            }, GET_WEATHER_FROM_CURRENT_LOCATION);
            return;
        }

        // system's LocationManager
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        Criteria locationCriteria = new Criteria();

        if (isNetworkEnabled) {
            locationCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
        } else if (isGPSEnabled) {
            locationCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        }

        locationManager.requestSingleUpdate(locationCriteria, this, null);

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MainActivity.GET_WEATHER_FROM_CURRENT_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getWeatherFromCurrentLocation();
            } else {
                AlertDialog messageDialog = new AlertDialog.Builder(this)
                        .setMessage(getString(R.string.location_permission_needed))
                        .setPositiveButton(getString(R.string.disable_geolocation), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startSettingsActivity();
                            }
                        })
                        .create();

                messageDialog.show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.forecast, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    private void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Callback invoked when a menu item was selected from this Activity's menu.
     *
     * @param item The menu item that was selected by the user
     * @return true if you handle the menu click here, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        if (id == R.id.action_map) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        geocodingService.refreshLocation(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void geocodeSuccess(LocationResult location) {
        // completed geocoding successfully
        loadAPI(location.getAddress());

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(getString(R.string.pref_cached_location), location.getAddress());
        editor.apply();
    }

    @Override
    public void geocodeFailure(Exception exception) {
        // GeoCoding failed, try loading weather data from the cache

    }

    private class BackgroundWorker extends AsyncTask<String, Void, String> {

        AlertDialog alert;


        @Override
        protected String doInBackground(String... params) {
            String stream = null;
            try {
                String url = params[0];

                HttpHandler handler = new HttpHandler();

                 stream= handler.makeServiceCall(url);

            }catch(Exception e){

            }

            return stream;

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {

            progressDialog.setMessage("Loading...!");
            progressDialog.show();

        }

        @Override
        protected void onPostExecute(String s) {
            try {
               /* if (s != null | s!="") {
                    editor = preferences.edit();
                    editor.putString("response", s);
                    editor.commit();
                }*/

                Gson gson = new Gson();
                yahooObject = gson.fromJson(s, YahooObject.class);
                mForecastAdapter.setYahooObject(yahooObject);
                mRecyclerView.setAdapter(mForecastAdapter);

                progressDialog.hide();
            } catch (Exception e) {

            }


        }
    }

    public void cacheLoad() {
        String cache = preferences.getString("response", null);
        if (cache != null) {
            try {
                Gson gson = new Gson();
                yahooObject = gson.fromJson(cache, YahooObject.class);
                mForecastAdapter.setYahooObject(yahooObject);
                mRecyclerView.setAdapter(mForecastAdapter);
                progressDialog.hide();
            } catch (Exception e) {

            }

        } else {
            if (Settingpreferences.getBoolean(getString(R.string.pref_geolocation_enabled), false)) {
                String locationCache = Settingpreferences.getString(getString(R.string.pref_cached_location), null);

                if (locationCache == null) {
                    getWeatherFromCurrentLocation();
                } else {
                    location = locationCache;
                }
            } else {
                location = Settingpreferences.getString(getString(R.string.pref_manual_location), null);
            }

            if (location != null) {
                loadAPI(location);
            }

        }
    }
}
