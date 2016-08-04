package com.sriyaan.qureco;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static com.sriyaan.util.url_dump.DeviceRegistration;
import static com.sriyaan.util.url_dump.SplashTimer;

public class Splash extends AppCompatActivity {
    static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context = Splash.this;
        new DeviceRegister().execute();
    }
    public class DeviceRegister extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String json = DeviceRegistration("akhtar15raza",context);
                Log.d("json","This is it: "+json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            SplashTimer(context,MainActivity.class);
        }
    }
}
