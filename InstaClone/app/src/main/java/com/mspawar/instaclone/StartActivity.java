package com.mspawar.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(FirebaseAuth.getInstance().getCurrentUser()==null) {
                    Intent i = new Intent(StartActivity.this, SignUpActivity.class);
                    startActivity(i);
                }
                else
                    startActivity(new Intent(StartActivity.this, HomeActivity.class));
                finish();
            }
        }, 3000);
    }
}