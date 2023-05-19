package com.project.shakespearesbooking;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.project.shakespearesbooking.Database.MyDatabaseHelper;
import com.project.shakespearesbooking.models.UserModel;

public class LoginActivity extends AppCompatActivity {

    TextView regTv;

    TextInputEditText emailEt, passwordEt;
    MaterialButton logBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        regTv = findViewById(R.id.registerTv);
        logBtn = findViewById(R.id.loginBtn);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);

        logBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel user = new UserModel();
                user.email = emailEt.getText().toString();
                user.password = passwordEt.getText().toString();
                if(!TextUtils.isEmpty(user.email) && !TextUtils.isEmpty(user.password)){
                    MyDatabaseHelper dbHelper = new MyDatabaseHelper(getApplicationContext());
                    if (dbHelper.isLogin(getApplicationContext(), user)){
                        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLogin", true); // Set the value to true
                        editor.putString("email", user.email); // Set the value to true
                        editor.apply();
                        Intent homeIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(homeIntent);
                        finish();
                        Toast.makeText(LoginActivity.this, "Successfully Logged!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginActivity.this, "Your entered wrong credential.", Toast.LENGTH_SHORT).show();
                    }
                    
                } else {
                    Toast.makeText(LoginActivity.this, "Fields can't be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        regTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(regIntent);
                finish();
            }
        });

    }
}