package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Reg extends AppCompatActivity {
    EditText username;
    EditText pwd;
    private static final String PREF = MainActivity.class.getPackage().toString();

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        username = findViewById(R.id.reg_name);
        pwd = findViewById(R.id.reg_pwd);

        prefs = getSharedPreferences(PREF, MODE_PRIVATE);
        String userName = prefs.getString("username","");
        String password = prefs.getString("password","");

        username.setText(userName);
        pwd.setText(password);
    }

    public void cancel(View view) {
        finish();
    }

    public void register(View view) {
    }
}