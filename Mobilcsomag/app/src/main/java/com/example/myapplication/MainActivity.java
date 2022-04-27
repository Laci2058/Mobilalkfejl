package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    private static final String PREF = MainActivity.class.getPackage().toString();
    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int RES = 2;

    private SharedPreferences prefs;
    private FirebaseAuth mAuth;
    private GoogleSignInClient googlesignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.name);
        password = findViewById(R.id.pwd);

        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googlesignin = GoogleSignIn.getClient(this, gso);

        prefs = getSharedPreferences(PREF, MODE_PRIVATE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", username.getText().toString());
        editor.putString("password", password.getText().toString());
        editor.apply();
    }

    public void register(View view) {
        Intent intent = new Intent(this, Reg.class);
        startActivity(intent);
    }

    private void startShopping() {
        Intent intent = new Intent(this, Shop.class);
        startActivity(intent);
    }

    public void login(View view) {
        String Name = username.getText().toString();
        String Password = password.getText().toString();
        mAuth.signInWithEmailAndPassword(Name, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startShopping();
                } else {
                    Toast.makeText(MainActivity.this, "Nem jött össze. " + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RES) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            } catch (ApiException e) {
                Log.w(LOG_TAG, "Baj van:", e);
            }
        }

    }

    public void firebaseAuth(String idToken) {
        AuthCredential kredenc = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(kredenc).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startShopping();
                } else {
                    Log.w(LOG_TAG, "Én se értem ne nézz rám: ", task.getException());
                }
            }
        });
    }

    public void loginWithGoogle(View view) {
        Intent intent = googlesignin.getSignInIntent();
        startActivityForResult(intent, RES);
    }

    public void loginAsGuest(View view) {
        mAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startShopping();
                } else {
                    Toast.makeText(MainActivity.this, "Nem jött össze. " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}