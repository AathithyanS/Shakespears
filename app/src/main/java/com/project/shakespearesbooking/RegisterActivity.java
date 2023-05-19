package com.project.shakespearesbooking;

import androidx.appcompat.app.AppCompatActivity;

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

public class RegisterActivity extends AppCompatActivity {

    TextView logTv;
    MaterialButton regBtn;
    TextInputEditText regUsernameEt, regEmailET, regPasswordEt;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();

        logTv = findViewById(R.id.loginTv);
        regBtn = findViewById(R.id.registerBtn);

        regUsernameEt = findViewById(R.id.regUsernameEt);
        regEmailET = findViewById(R.id.regEmailET);
        regPasswordEt = findViewById(R.id.regPasswordEt);

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel user = new UserModel();
                user.username = regUsernameEt.getText().toString();
                user.email = regEmailET.getText().toString();
                user.password = regPasswordEt.getText().toString();
                if(!TextUtils.isEmpty(user.username) && !TextUtils.isEmpty(user.email) && !TextUtils.isEmpty(user.password)){
                    MyDatabaseHelper dbHelper = new MyDatabaseHelper(getApplicationContext());
                    dbHelper.insertUser(user);
                    SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isLogin", true); // Set the value to true
                    editor.putString("email", user.email); // Set the value to true
                    editor.apply();
                    Intent homeIntent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(homeIntent);
                    finish();
                    Toast.makeText(RegisterActivity.this, "Successfully registered!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Fields can't be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        logTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent regIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(regIntent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent regIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(regIntent);
        finish();
    }
}