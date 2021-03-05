package com.example.solarise.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.solarise.R;
import com.example.solarise.models.RetrieveDB;
import com.example.solarise.models.User;
import com.example.solarise.models.readFireBase;
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
  //  ArrayList<User> allUsers = new ArrayList<>();
    HashMap<String, User> allUsers = new HashMap();
    ArrayList<String> userInfo = new ArrayList();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference firebaseRootRef = firebaseDatabase.getReference();
    DatabaseReference itemsRef = firebaseRootRef.child("Users");
    private ListView listView;
    private TextView editName, editAge, editSleepPref, editCaffeiene;
    private String oName, oAge, oSleep, oCaffeiene;
    private Button editProfile, editHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        editProfile = (Button)findViewById(R.id.editProfile);
        editHome = (Button)findViewById(R.id.editHome);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        RetrieveDB retrieve = new RetrieveDB(uid);
        retrieve.getCurrentUser(new readFireBase() {
            @Override
            public void onCallBack(HashMap<String, User> users) {
                Log.d("Retrieved:", users.toString());
                User user = users.get(uid);
                editName = (TextView)findViewById(R.id.editName);
                editAge = (TextView)findViewById(R.id.editAge);
                editSleepPref= (TextView)findViewById(R.id.editSleepPref);
                editCaffeiene = (TextView)findViewById(R.id.editCaffeiene);
                editName.setText(user.getUserName());
                editAge.setText(String.valueOf(user.getAge()));
                if (user.getSleepPreference()) {
                    editSleepPref.setText("Early Bird");
                }
                else{
                    editSleepPref.setText("Night Owl");
                }
                editCaffeiene.setText(String.valueOf(user.getAverageCaffeine()));

                oName = user.getUserName();
                oAge = String.valueOf(user.getAge());
                if (user.getSleepPreference()){
                    oSleep = "Early Bird";
                }
                else{
                    oSleep = "Night Owl";
                }
                oCaffeiene = String.valueOf(user.getAverageCaffeine());




            }

        });

        editHome.setOnClickListener(v -> {
            startActivity(new Intent(EditProfile.this, MainActivity.class));
        });

        editProfile.setOnClickListener(v -> {
            if (!oName.equals(editName.getText().toString()) && !editName.getText().toString().isEmpty()){
                Log.d("TESTER:", "Success");
                itemsRef.child(uid).child("userName").setValue(editName.getText().toString());
            }
            if (!oAge.equals(editAge.getText().toString()) && !editAge.getText().toString().isEmpty()){
                itemsRef.child(uid).child("age").setValue(Integer.parseInt(editAge.getText().toString()));
            }
            if (!oSleep.equals(editSleepPref.getText().toString()) && !editSleepPref.getText().toString().isEmpty()){
                if (editSleepPref.getText().toString() == "Early Bird"){
                    itemsRef.child(uid).child("sleepPreference").setValue(true);

                }
                else{
                    itemsRef.child(uid).child("sleepPreference").setValue(false);

                }
            }
//            }
            if (!oCaffeiene.equals(editCaffeiene.getText().toString()) && !editCaffeiene.getText().toString().isEmpty()){
                itemsRef.child(uid).child("averageCaffeine").setValue(Integer.parseInt(editCaffeiene.getText().toString()));
            }

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

//                      allUsers.add(new User(userName, Integer.parseInt(age), Boolean.parseBoolean(earlyBird), Integer.parseInt(caffeine), uid));


                  }
                  reader.onCallBack(allUsers);
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          };
          itemsRef.addListenerForSingleValueEvent(valueEventListener);


    }
}