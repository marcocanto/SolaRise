package com.example.solarise.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.solarise.R;
import com.example.solarise.models.User;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserInfoActivity extends AppCompatActivity {
    private EditText personName, personAge, personSleep, personHeight, personWeight;
    private FirebaseDatabase firebaseDatabase; //Root Node
    private DatabaseReference firebaseReference; //Reference to sub root levels

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        personName = findViewById(R.id.etUserName);
        personAge = findViewById(R.id.etUserAge);
        Bundle bundle = getIntent().getExtras();
        String userUid = bundle.getString("uid");

        Button btnEarlyBird = findViewById(R.id.btnEarlyBird);
        Button btnNightOwl = findViewById(R.id.btnNightOwl);
        MaterialButtonToggleGroup toggleGroup = findViewById(R.id.toggleGroup);
        NumberPicker averageCoffee = findViewById(R.id.npAverageCaffeine);
        averageCoffee.setMinValue(0);
        averageCoffee.setMaxValue(10);
        averageCoffee.setWrapSelectorWheel(true);


        Button btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(view -> {
            String userName = personName.getText().toString();
            String userAge = personAge.getText().toString();
            boolean userSleep;
            if (forms_completed(toggleGroup, userName, userAge)) {
                userSleep = toggleGroup.getCheckedButtonId() == R.id.btnEarlyBird;
                User user1 = new User(userName, Integer.parseInt(userAge), userSleep, 1, userUid);
                firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseReference = firebaseDatabase.getReference("Users");
                firebaseReference.child(userUid).setValue(user1);

                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(UserInfoActivity.this, "Please complete all forms", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean forms_completed(MaterialButtonToggleGroup toggleGroup, String username, String age) {
        if (toggleGroup.getCheckedButtonId() == -1) {
            Toast.makeText(UserInfoActivity.this, "Select a sleep preference", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(UserInfoActivity.this, "selected button" + toggleGroup.getCheckedButtonId(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}