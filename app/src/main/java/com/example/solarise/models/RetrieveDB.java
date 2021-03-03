package com.example.solarise.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RetrieveDB {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference firebaseRootRef = firebaseDatabase.getReference();
    DatabaseReference itemsRef = firebaseRootRef.child("Users");
    HashMap<String, User> allUsers = new HashMap();
    private String uid;

    public RetrieveDB(){}

    public RetrieveDB(String uid){
        this.uid = uid;
    }

    public void getCurrentUser(readFireBase reader){
        Query currUser = itemsRef.orderByChild("uid").equalTo(uid);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String userName = snapshot.child(uid).child("userName").getValue(String.class);
                    String age = snapshot.child(uid).child("age").getValue().toString();
                    String earlyBird = snapshot.child(uid).child("sleepPreference").getValue().toString();
                    String caffeine = snapshot.child(uid).child("averageCaffeine").getValue().toString();
                    String auid = snapshot.child(uid).child("uid").getValue().toString();
                    allUsers.put(uid, new User(userName, Integer.parseInt(age), Boolean.parseBoolean(earlyBird), Integer.parseInt(caffeine), auid));
                    reader.onCallBack(allUsers);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        itemsRef.addListenerForSingleValueEvent(valueEventListener);




    }


}
