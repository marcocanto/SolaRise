package com.example.solarise.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.solarise.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText email, userName, passWord;
    private Button registerButton;
    private TextView Login;
    private FirebaseAuth firebaseAuth;
//    private FirebaseDatabase firebaseDatabase; //Root Node
//    private DatabaseReference firebaseReference; //Reference to sub root levels

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializeViews();

        firebaseAuth = FirebaseAuth.getInstance(); //Creates firebase auth object

        registerButton.setOnClickListener(v -> {
            if (completed()){
                // upload to database
                String user_email = email.getText().toString();
                String user_pass = passWord.getText().toString();
//                    test_User aUser = new test_User(user_email,user_pass);
//                    firebaseDatabase  = FirebaseDatabase.getInstance();
//                    firebaseReference = firebaseDatabase.getReference("Users");
//                    firebaseReference.child(user_email).setValue(aUser);

                firebaseAuth.createUserWithEmailAndPassword(user_email, user_pass).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        firebaseAuth.signInWithEmailAndPassword(user_email, user_pass);
                        String uid = firebaseAuth.getUid();
                        Log.d("TAG", uid);
                        Intent intent = new Intent(this, UserInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("uid", uid);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Login.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void initializeViews(){
        email = findViewById(R.id.etRegisterEmail);
        userName = findViewById(R.id.etRegisterUsername);
        passWord = findViewById(R.id.etRegisterPassword);
        registerButton = findViewById(R.id.btnRegister);
        Login = findViewById(R.id.tvReturnLogin);

    }

    private boolean completed(){
        String user = userName.getText().toString();
        String pass = passWord.getText().toString();
        String remail = email.getText().toString();
        if (remail.isEmpty() || user.isEmpty() || pass.isEmpty()){
            Toast.makeText(this,"Please complete all forms", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }

    }
}