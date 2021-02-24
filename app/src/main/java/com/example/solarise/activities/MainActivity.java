package com.example.solarise.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
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
import com.google.firebase.auth.FirebaseAuth;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FusedLocationProviderClient fusedLocationClient;
    private OpenWeatherClient client;
    private static Daytime currentDaytime;
    private static Location lastLocation;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private RatingBar ratingBar;
    private Button ratingButton;
    private Button logout;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ratingBar = (RatingBar) findViewById(R.id.rbSleepRating);
        ratingButton = (Button) findViewById(R.id.btnRatingSubmit);

        TextView tvSunriseTime = findViewById(R.id.tvSunriseTime);
        TextView tvSunsetTime = findViewById(R.id.tvSunsetTime);

        ratingButton.setOnClickListener(view -> {
            int numStars = ratingBar.getNumStars();
            float rating = ratingBar.getRating();
            Toast.makeText(this, "Rating: " + rating + "/" + numStars, Toast.LENGTH_SHORT).show();
        });

        logout = (Button)findViewById(R.id.btnLogout);

        logout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Logging Out...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        // set up the network client to send API requests
        client = new OpenWeatherClient();
        // initialize the location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

//        requestPermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION);

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
                        getCurrentLocation(fusedLocationClient);
                        fusedLocationClient.removeLocationUpdates(locationCallback);
                    }
                }
            }
        };

        currentDaytime = new Daytime();
        currentDaytime.setListener(json -> {
            currentDaytime.setDaytimeFromJSON(json);
            tvSunriseTime.setText(currentDaytime.getSunrise());
            tvSunsetTime.setText(currentDaytime.getSunset());
        });

        getCurrentLocation(fusedLocationClient);
        tvSunriseTime.setText(currentDaytime.getSunrise());
        tvSunsetTime.setText(currentDaytime.getSunset());
    }

    // returns pair of lat,lon
    @RequiresApi(api = Build.VERSION_CODES.O)
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
                            Log.i(TAG, "error receiving location");
                            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                        }
                    });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setWeather(OpenWeatherClient client, Location location) {
        currentDaytime.loadDaytimeAsync(client, location);
    }

    public void setLocation(Location location) {
        lastLocation = location;
    }

    private final ActivityResultLauncher<String> requestPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            Log.i(TAG, "permission granted");
            getCurrentLocation(fusedLocationClient);
        }
        else {
            Log.i(TAG, "permission denied");
        }
    });
}
