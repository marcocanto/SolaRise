package com.example.solarise.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.solarise.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etUserEmail, etPassword;
    private MaterialButton btnSignup;
    private TextView tvLogin;
    private FirebaseAuth firebaseAuth;
//    private FirebaseDatabase firebaseDatabase; //Root Node
//    private DatabaseReference firebaseReference; //Reference to sub root levels

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initializeViews();

        firebaseAuth = FirebaseAuth.getInstance(); //Creates firebase auth object

        btnSignup.setOnClickListener(v -> {
            if (completed()){
                // upload to database
                String user_email = etUserEmail.getText().toString();
                String user_pass = etPassword.getText().toString();

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

        tvLogin.setOnClickListener(v -> {
//                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                });
    }

    private void initializeViews(){
        etUserEmail = findViewById(R.id.etUserEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignup = findViewById(R.id.btnSignUp);
        tvLogin = findViewById(R.id.tvLogin);

    }

    private boolean completed(){
        String pass = etPassword.getText().toString();
        String remail = etUserEmail.getText().toString();
        if (remail.isEmpty() || pass.isEmpty()){
            Toast.makeText(this,"Please complete all forms", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }
}