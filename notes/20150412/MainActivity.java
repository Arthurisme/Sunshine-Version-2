/*
 * Copyright (C) 2014 The Android Open Source Project
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
package com.example.android.sunshine.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URISyntaxException;


//import com.android.debug.hv.ViewServer;
import com.example.android.sunshine.app.data.WeatherContract;

import java.net.URI;

public class MainActivity extends ActionBarActivity implements ForecastFragment.Callback {

    String mLocation;
    //private final String FORECASTFRAGMENT_TAG = "FFTAG";
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mLocation = Utility.getPreferredLocation(this);
        super.onCreate(savedInstanceState);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        setContentView(R.layout.activity_main);

        if (findViewById(R.id.weather_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
//            if (savedInstanceState == null) {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.weather_detail_container, new DetailFragment(), DETAILFRAGMENT_TAG)
//                        .commit();
//            }


            //show default detail information:

            //get uri of taday:
            long dateTime;
            Time dayTime = new Time();
            Log.d("2403", "2403 : Main-dayTime1: " + dayTime);
            dayTime.setToNow();
            Log.d("2403", "2403 : Main-dayTime2: " + dayTime);
            int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);
            Log.d("2403", "2403 : Main-julianStartDay: " + julianStartDay);
            //dayTime = new Time();
            Log.d("2403", "2403 : Main-dayTime3: " + dayTime);
            dateTime = dayTime.setJulianDay(julianStartDay);
            Log.d("2403", "2403 : Main-dateTime: " + dateTime);

            String locationSetting = Utility.getPreferredLocation(this.getApplication());
            Uri uriDefault= WeatherContract.WeatherEntry.buildWeatherLocationWithDate(locationSetting, dateTime);
            Log.d("2403", "2403 : Main-uriDefault: " + uriDefault);


                //Uri uriDefault =  Uri.parse("content://com.example.android.sunshine.app/weather/94043/1427601600000");
                //System.out.println("URI created: " + uri);
                Bundle argsDefault = new Bundle();
                argsDefault.putParcelable(DetailFragment.DETAIL_URI, uriDefault);

                DetailFragment fragment = new DetailFragment();//line F2703
                fragment.setArguments(argsDefault);



                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.weather_detail_container, fragment, DETAILFRAGMENT_TAG) //fragment that have a tag name as mentioned, from DetailFragment F2703 but not a new one.
                                //try :
                                //.replace(R.id.weather_detail_container, new DetailFragment(), "tt")

                        .commit();










        } else {
            mTwoPane = false;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        if (id == R.id.action_map) {
            openPreferredLocationInMap();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationInMap() {

        String location = Utility.getPreferredLocation(this);

        // Using the URI scheme for showing a location found on a map.  This super-handy
        // intent can is detailed in the "Common Intents" page of Android's developer site:
        // http://developer.android.com/guide/components/intents-common.html#Maps
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
                .appendQueryParameter("q", location)
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);

        //what is this?
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "Couldn't call " + location + ", no receiving apps installed!");
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        String location = Utility.getPreferredLocation( this );
//                 // update the location in our second pane using the fragment manager
//                         if (location != null && !location.equals(mLocation)) {
//                         ForecastFragment ff = (ForecastFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_forecast);
//                         if ( null != ff ) {
//                                 ff.onLocationChanged();
//                             }
//                         mLocation = location;
//                     }
//        ViewServer.get(this).setFocusedWindow(this);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        String location = Utility.getPreferredLocation(this);
        // update the location in our second pane using the fragment manager
        if (location != null && !location.equals(mLocation)) {
            ForecastFragment ff = (ForecastFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_forecast);
            if (null != ff) {
                ff.onLocationChanged();
            }
            DetailFragment df = (DetailFragment) getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
            if (null != df) {
                df.onLocationChanged(location);
            }
            mLocation = location;
        }
    }

    @Override
    public void onItemSelected(Uri contentUri) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putParcelable(DetailFragment.DETAIL_URI, contentUri);

            DetailFragment fragment = new DetailFragment();//line F2703
            fragment.setArguments(args);
            Log.d("2403", "2403 :onItemSelected(Uri contentUri)" + contentUri);


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.weather_detail_container, fragment, DETAILFRAGMENT_TAG) //fragment that have a tag name as mentioned, from DetailFragment F2703 but not a new one.
                            //try :
                            //.replace(R.id.weather_detail_container, new DetailFragment(), "tt")

                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class)
                    .setData(contentUri);
            startActivity(intent);
        }
    }


    public void onDestroy() {
        super.onDestroy();
        //ViewServer.get(this).removeWindow(this);
    }
}
