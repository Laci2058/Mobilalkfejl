package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegActivity extends AppCompatActivity {
    EditText username;
    EditText email;
    EditText pwd;
    EditText pwdagain;
    private static final String PREF = MainActivity.class.getPackage().toString();

    private SharedPreferences prefs;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        username = findViewById(R.id.reg_name);
        pwd = findViewById(R.id.reg_pwd);
        email = findViewById(R.id.email);
        pwdagain = findViewById(R.id.reg_pwd_again);

        prefs = getSharedPreferences(PREF, MODE_PRIVATE);
        String userName = prefs.getString("username", "");
        String password = prefs.getString("password", "");

        username.setText(userName);
        pwd.setText(password);

        mAuth = FirebaseAuth.getInstance();
    }

    public void cancel(View view) {
        finish();
    }

    public void register(View view) {
        String mail = email.getText().toString();
        String passwd = pwd.getText().toString();
        String passwda = pwdagain.getText().toString();
        if (passwd.equals(passwda)) {
            mAuth.createUserWithEmailAndPassword(mail, passwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startShopping();
                    } else {
                        Toast.makeText(RegActivity.this, "Nem jött össze. " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }else {
            Toast.makeText(RegActivity.this,"Nem egyeznek a jelszók",Toast.LENGTH_SHORT).show();
        }
    }

    private void startShopping() {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }
}