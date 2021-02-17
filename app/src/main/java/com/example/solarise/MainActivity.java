package com.example.solarise;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    private FusedLocationProviderClient fusedLocationClient;

    private EditText personName, personAge, personSleep, personHeight, personWeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        personName = (EditText) findViewById(R.id.person_name);
        personAge = (EditText) findViewById(R.id.person_age);
        personSleep = (EditText) findViewById(R.id.person_sleep);
        personHeight = (EditText) findViewById(R.id.height);
        personWeight = (EditText) findViewById(R.id.weight);

        Button btnSubmit = findViewById(R.id.submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = personName.getText().toString();
                int userAge = Integer.parseInt(personAge.getText().toString());
                boolean userSleep = personSleep.getText().toString().equals("Early Bird");
                int userHeight = Integer.parseInt(personHeight.getText().toString());
                int userWeight = Integer.parseInt(personWeight.getText().toString());
                User user1 = new User(userName, userAge, userSleep, userHeight, userWeight);

//                try {
//                    FileOutputStream fos = openFileOutput("test.dat", Context.MODE_PRIVATE);
//
//                    // Wrapping our file stream
//                    ObjectOutputStream oos = new ObjectOutputStream(fos);
//                    oos.write(user1);
//                    oos.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

                setContentView(R.layout.activity_dashboard);
            }
        });

    }
}
