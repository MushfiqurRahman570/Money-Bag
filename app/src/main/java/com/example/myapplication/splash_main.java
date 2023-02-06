package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.sign.login;

public class splash_main extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        button = findViewById(R.id.button);

        SharedPreferences preferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE);
        String FirsTime = preferences.getString("FirstTimeInstall","");

        if (FirsTime.equals("yes")){
            Intent intent = new Intent(splash_main.this ,login.class );
            startActivity(intent);

        }else {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("FirstTimeInstall","yes");
            editor.apply();

        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(splash_main.this, login.class));
                finish();
            }
        });


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent i = new Intent(splash.this, login.class);
//                startActivity(i);
//                finish();
//            }
//        }, 3000);
    }
}