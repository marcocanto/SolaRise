package com.example.solarise.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.solarise.R;
import com.example.solarise.models.RetrieveDB;
import com.example.solarise.models.User;
import com.example.solarise.models.readFireBase;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class EditProfile extends AppCompatActivity {
    HashMap<String, User> allUsers = new HashMap();
    ArrayList<String> userInfo = new ArrayList();

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference firebaseRootRef = firebaseDatabase.getReference();
    DatabaseReference itemsRef = firebaseRootRef.child("Users");

    private MaterialToolbar appToolbar;

    private String oName, oAge, oCaffeine;
    private int oSleep;
    private TextView tvEmail;
    private TextInputEditText etUserName, etUserAge, etCaffeine;
    private MaterialButtonToggleGroup toggleGroup;
    private Button btnEditProfile, btnNightOwl, btnEarlyBird;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        RetrieveDB retrieve = new RetrieveDB(uid);

        initializeViews();

        appToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.logout:
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                case R.id.dashboard:
                    finish();
            }
            return true;
        });


        retrieve.getCurrentUser(new readFireBase() {
            @Override
            public void onCallBack(HashMap<String, User> users) {
                Log.d("Retrieved:", users.toString());
                User user = users.get(uid);
                tvEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                etUserName.setText(user.getUserName());
                etUserAge.setText(String.valueOf(user.getAge()));

                if (user.getSleepPreference()) {
                    toggleGroup.check(R.id.btnEarlyBird);
                }
                else{
                    toggleGroup.check(R.id.btnNightOwl);
                }
                etCaffeine.setText(String.valueOf(user.getAverageCaffeine()));

                oName = user.getUserName();
                oAge = String.valueOf(user.getAge());
                oSleep = toggleGroup.getCheckedButtonId();
                oCaffeine = String.valueOf(user.getAverageCaffeine());
            }
        });

        btnEditProfile.setOnClickListener(v -> {
            if (!oName.equals(etUserName.getText().toString()) && !etUserName.getText().toString().isEmpty()){
                Log.d("TESTER:", "Success");
                itemsRef.child(uid).child("userName").setValue(etUserName.getText().toString());
            }
            if (!oAge.equals(etUserAge.getText().toString()) && !etUserAge.getText().toString().isEmpty()){
                itemsRef.child(uid).child("age").setValue(Integer.parseInt(etUserAge.getText().toString()));
            }
            if (oSleep != toggleGroup.getCheckedButtonId()){
                    Boolean sleepPref = toggleGroup.getCheckedButtonId() == R.id.btnEarlyBird;
                    if (sleepPref){
                        oSleep = R.id.btnEarlyBird;
                    }
                    else{
                        oSleep = R.id.btnNightOwl;
                    }
                    itemsRef.child(uid).child("sleepPreference").setValue(sleepPref);
                }
            if (!oCaffeine.equals(etCaffeine.getText().toString()) && !etCaffeine.getText().toString().isEmpty()){
                itemsRef.child(uid).child("averageCaffeine").setValue(Integer.parseInt(etCaffeine.getText().toString()));
            }
            Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
        });

    }
    public void getUser(readFireBase reader){
          ValueEventListener valueEventListener = new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  for (DataSnapshot ds : snapshot.getChildren()){
                      String userName = ds.child("userName").getValue(String.class);
                      String age = ds.child("age").getValue().toString();
                      String earlyBird = ds.child("sleepPreference").getValue().toString();
                      String caffeine = ds.child("averageCaffeine").getValue().toString();
                      String uid = ds.child("uid").getValue().toString();
                      allUsers.put(uid, new User(userName, Integer.parseInt(age), Boolean.parseBoolean(earlyBird), Integer.parseInt(caffeine), uid));
                  }
                  reader.onCallBack(allUsers);
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {
              }
          };
          itemsRef.addListenerForSingleValueEvent(valueEventListener);
    }
    private void initializeViews() {
        tvEmail= findViewById(R.id.tvEmail);
        toggleGroup = findViewById(R.id.toggleGroup);
        btnEditProfile = findViewById(R.id.btUpdateProfile);
        appToolbar = findViewById(R.id.topAppBar);
        etUserName = findViewById(R.id.etUserName);
        etUserAge = findViewById(R.id.etUserAge);
        etCaffeine = findViewById(R.id.etCaffeine);
        btnNightOwl = findViewById(R.id.btnNightOwl);
        btnEarlyBird = findViewById(R.id.btnEarlyBird);
    }
}