package com.example.solarise.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.solarise.R;
import com.example.solarise.models.User;
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
        personName = findViewById(R.id.person_name);
        personAge = findViewById(R.id.person_age);
        personSleep = findViewById(R.id.person_sleep);
        personHeight = findViewById(R.id.height);
        personWeight = findViewById(R.id.weight);

        Button btnSubmit = findViewById(R.id.submit);

        btnSubmit.setOnClickListener(view -> {
            String userName = personName.getText().toString();
            int userAge = Integer.parseInt(personAge.getText().toString());
            boolean userSleep = personSleep.getText().toString().equals("Early Bird");
            int userHeight = Integer.parseInt(personHeight.getText().toString());
            int userWeight = Integer.parseInt(personWeight.getText().toString());
            User user1 = new User(userName, userAge, userSleep, userHeight, userWeight);
            if (forms_completed(userName, userAge, userSleep, userHeight, userWeight)){
                firebaseDatabase  = FirebaseDatabase.getInstance();
                firebaseReference = firebaseDatabase.getReference("Users");
                firebaseReference.child(userName).setValue(user1);

                setContentView(R.layout.activity_dashboard);
            }
            else{
                Toast.makeText(UserInfoActivity.this,"Please complete all forms", Toast.LENGTH_SHORT).show();
            }
//                firebaseDatabase  = FirebaseDatabase.getInstance();
//                firebaseReference = firebaseDatabase.getReference("Users");
//                firebaseReference.child(userName).setValue(user1);
//                setContentView(R.layout.activity_dashboard);
        });
    }

    private boolean forms_completed(String name, int age, boolean userSleep, int Height, int Weight){
        if(name.isEmpty()){
            return false;
        }
        if (age == 0 || Height == 0 || Weight == 0){
            return false;
        }
        return true;
    }


}