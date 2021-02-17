package com.example.solarise.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.solarise.R;
import com.example.solarise.models.User;
import com.google.android.gms.location.FusedLocationProviderClient;

public class LoginActivity extends AppCompatActivity {

    private String TAG = "LoginActivity";
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

        btnSubmit.setOnClickListener(view -> {
            String userName = personName.getText().toString();
            int userAge = Integer.parseInt(personAge.getText().toString());
            boolean userSleep = personSleep.getText().toString().equals("Early Bird");
            int userHeight = Integer.parseInt(personHeight.getText().toString());
            int userWeight = Integer.parseInt(personWeight.getText().toString());
            User user1 = new User(userName, userAge, userSleep, userHeight, userWeight);

//            try {
//                FileOutputStream fos = openFileOutput("test.dat", Context.MODE_PRIVATE);
//
//                // Wrapping our file stream
//                ObjectOutputStream oos = new ObjectOutputStream(fos);
//                oos.write(user1);
//                oos.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        });

    }
}
