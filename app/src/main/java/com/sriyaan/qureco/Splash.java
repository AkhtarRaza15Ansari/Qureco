package com.sriyaan.qureco;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static com.sriyaan.util.url_dump.SplashTimer;

public class Splash extends AppCompatActivity {
    static Context context;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        prefs = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);
        context = Splash.this;
        String login = prefs.getString("login","");
        if(login.equals("yes"))
        {
            SplashTimer(context,Home.class);
        }
        else {
            SplashTimer(context,MainActivity.class);
        }

    }

}
