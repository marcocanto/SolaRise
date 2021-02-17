package com.example.solarise.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.solarise.R;
import com.example.solarise.models.Daytime;
import com.example.solarise.network.OpenWeatherClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FusedLocationProviderClient fusedLocationClient;
    private OpenWeatherClient client;
    private static Daytime currentDaytime;
    private static Location lastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // set up the network client to send API requests
        client = new OpenWeatherClient();
        // initialize the location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        requestPermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION);

        if (lastLocation == null) {
            getCurrentLocation(fusedLocationClient);
        }
        else{
            setWeather(client, lastLocation);
        }
//        if (currentDaytime != null) {
//            .setText(currentDaytime.getSunrise());
//        }
    }

    // returns pair of lat,lon
    public void getCurrentLocation(FusedLocationProviderClient fusedLocationClient) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
        } else {
            Log.i(TAG, "getting last location");
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        // check if location was received
                        if (location != null) {
                            Log.i(TAG, "received location data: " + location.toString());
                            lastLocation = location;
                        } else {
                            Log.i(TAG, "error receiving location");
                        }
                    });
        }
    }

    public void setWeather(OpenWeatherClient client, Location location) {
        client.getCurrentWeather(location.getLatitude(), location.getLongitude(), new JsonHttpResponseHandler() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                if (json != null) {
                    try {
                        Log.i(TAG, "retrieved weather json: " + json.jsonObject.getJSONObject("sys").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    currentDaytime = Daytime.fromJSON(json.jsonObject);
                } else {
                    Log.i(TAG, "received empty weather json");
                }
            }
            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e(TAG, "error getting weather data", throwable);
            }
        });
    }

    private final ActivityResultLauncher<String> requestPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            Log.i(TAG, "permission granted");
        }
        else {
            Log.i(TAG, "permission denied");
        }
    });
}
