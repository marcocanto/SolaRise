package com.example.solarise.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.solarise.R;
import com.example.solarise.models.Daytime;
import com.example.solarise.network.OpenWeatherClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FusedLocationProviderClient fusedLocationClient;
    private OpenWeatherClient client;
    private static Daytime currentDaytime;
    private static Location lastLocation;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // set up the network client to send API requests
        client = new OpenWeatherClient();
        // initialize the location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20 * 1000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.i(TAG, "on location result");
                if (locationResult == null) {
                    return;
                }
                for (Location location: locationResult.getLocations()) {
                    if (location != null) {
                        setLocation(location);
                        fusedLocationClient.removeLocationUpdates(locationCallback);
                    }
                }
            }
        };

        TextView tvSunrise = findViewById(R.id.tvSunrise);
        TextView tvSunset = findViewById(R.id.tvSunset);

        currentDaytime = new Daytime();
        currentDaytime.setListener(json -> {
            currentDaytime.setDaytimeFromJSON(json);
            tvSunrise.setText(currentDaytime.getSunrise());
            tvSunset.setText(currentDaytime.getSunset());
        });

        requestPermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION);

        getCurrentLocation(fusedLocationClient);
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
                            setLocation(location);
                            setWeather(client, lastLocation);
                        } else {
                            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                            Log.i(TAG, "error receiving location");
                        }
                    });
        }
    }

    public void setWeather(OpenWeatherClient client, Location location) {
        currentDaytime.loadDaytimeAsync(client, location);
    }

    public void setLocation(Location location) {
        lastLocation = location;
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
