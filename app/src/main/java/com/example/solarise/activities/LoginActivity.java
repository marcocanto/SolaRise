package com.example.solarise.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.solarise.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etEmail, etPassword;
    private MaterialButton btnLogin;
    private MaterialTextView tvSignup;

    private ProgressDialog progress;
    private FirebaseAuth firebaseAuth;

    private FirebaseDatabase firebaseDatabase; //Root Node
    private DatabaseReference firebaseReference; //Reference to sub root levels

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etUserEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnSignUp);
        tvSignup = findViewById(R.id.tvLogin);
        progress = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser current_user = firebaseAuth.getCurrentUser();

        if (current_user != null){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        btnLogin.setOnClickListener((View v) -> {
            authentication(etEmail.getText().toString(), etPassword.getText().toString());
        });

        tvSignup.setOnClickListener((View v) -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void authentication(String user, String pass){
        if (user.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please provide login information", Toast.LENGTH_SHORT).show();
            return;
        }
        progress.setMessage("Verifying Credentials");
        firebaseAuth.signInWithEmailAndPassword(user, pass).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                progress.dismiss();
                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
            else{
                Toast.makeText(LoginActivity.this, "Login Failed Incorrect Credentials", Toast.LENGTH_SHORT).show();
            }
        });

    }

}