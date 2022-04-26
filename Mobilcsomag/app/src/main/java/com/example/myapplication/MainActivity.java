package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    private static final String PREF=MainActivity.class.getPackage().toString();

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.name);
        password= findViewById(R.id.pwd);


        prefs=getSharedPreferences(PREF,MODE_PRIVATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor= prefs.edit();
        editor.putString("username",username.getText().toString());
        editor.putString("password",password.getText().toString());
        editor.apply();
    }

    public void register(View view) {
        Intent intent = new Intent(this,Reg.class);
        startActivity(intent);
    }

    public void login(View view) {
        String Name = username.getText().toString();
        String Password = password.getText().toString();
    }
}