package com.example.solarise.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.solarise.R;
import com.example.solarise.models.User;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserInfoActivity extends AppCompatActivity {
    private TextInputEditText personName, personAge, personCaffeine;
    private Button btnEarlyBird, btnNightOwl;
    private FirebaseDatabase firebaseDatabase; //Root Node
    private DatabaseReference firebaseReference; //Reference to sub root levels
    private MaterialButtonToggleGroup toggleGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        personName = findViewById(R.id.etUserName);
        personAge = findViewById(R.id.etUserAge);
        personCaffeine = findViewById(R.id.etCaffeine);
        btnEarlyBird = findViewById(R.id.btnEarlyBird);
        btnNightOwl = findViewById(R.id.btnNightOwl);
        Bundle bundle = getIntent().getExtras();
        String userUid = bundle.getString("uid");

        toggleGroup = findViewById(R.id.toggleGroup);

        Button btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(view -> {
            String userName = personName.getText().toString();
            String userAge = personAge.getText().toString();
            String userCaff = personCaffeine.getText().toString();
            boolean userSleep;
            if (forms_completed(toggleGroup, userName, userAge)) {
                userSleep = toggleGroup.getCheckedButtonId() == R.id.btnEarlyBird;
                User user1 = new User(userName, Integer.parseInt(userAge), userSleep, Integer.parseInt(userCaff), userUid);
                firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseReference = firebaseDatabase.getReference("Users");
                firebaseReference.child(userUid).setValue(user1);

                startActivity(new Intent(this, NewUserActivity.class));
                finish();
            } else {
                Toast.makeText(UserInfoActivity.this, "Please complete all forms", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean forms_completed(MaterialButtonToggleGroup toggleGroup, String username, String age) {
        return toggleGroup.getCheckedButtonId() != -1 && username.length() > 0 && age.length() > 0;
    }
}