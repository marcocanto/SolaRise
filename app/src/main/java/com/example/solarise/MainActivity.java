package com.example.solarise;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnRequest = findViewById(R.id.btnRequest);
        Button btnLocate = findViewById(R.id.btnLocate);

        // initialize the location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getLastLocation();
            }
        });

        getLastLocation();

    }

    private String getLastLocation() {
        String msg = null;
        Log.i(TAG, "getting last location");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // check if location was received
                            if (location != null) {
                                Log.i(TAG, "Location: " + location.toString());
                                Log.i("MainActivity", location.toString());
                                Log.i("MainActivity", Double.toString(location.getLatitude()));
                            } else {
                                Log.i(TAG, "error receiving location");
                            }
                        }
                    });
        }
        return msg;

    }

    private ActivityResultLauncher<String> requestPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
           if (isGranted) {
                Log.i("DEBUG", "permission granted");
           }
           else {
               Log.i("DEBUG", "permission denied");
           }
        });
}
