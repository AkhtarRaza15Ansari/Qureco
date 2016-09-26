package com.sriyaan.qureco;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.sriyaan.gcm.QuickstartPreferences;
import com.sriyaan.gcm.RegistrationIntentService;
import com.sriyaan.util.url_dump;

import java.util.ArrayList;

public class Splash extends AppCompatActivity {
    static Context context;
    SharedPreferences prefs;
    private static int SPLASH_TIME_OUT = 4000;
    private static final int PERMISSION_REQUEST_CODE = 1;
    String permission1 = Manifest.permission.INTERNET;
    String permission2 = Manifest.permission.RECEIVE_SMS;
    String permission3 = Manifest.permission.READ_SMS;
    String permission4 = Manifest.permission.READ_PHONE_STATE;
    String permission5 = Manifest.permission.CAMERA;
    String permission6 = Manifest.permission.READ_EXTERNAL_STORAGE;
    String permission7 = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    String permission8 = Manifest.permission.ACCESS_COARSE_LOCATION;
    String permission9 = Manifest.permission.ACCESS_FINE_LOCATION;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "MainActivity";

    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ImageView img = (ImageView) findViewById(R.id.image);


        prefs = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);
        context = Splash.this;


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    //success condition

                } else {
                    //error condition

                }
            }
        };

        // Registering BroadcastReceiver
        registerReceiver();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            // only for gingerbread and newer versions
            requestPermission();
        }
        else{
            allpermission();
        }



    }
    private boolean checkPermission(String permission){
        int result = checkSelfPermission(permission);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission(){

        if (checkPermission(permission1)&&checkPermission(permission2)&&checkPermission(permission3)&&checkPermission(permission4)
                &&checkPermission(permission5)&&checkPermission(permission6)&&checkPermission(permission7)&&checkPermission(permission8)&&checkPermission(permission9)){
            allpermission();
        } else {
            ArrayList<String> per = new ArrayList<String>();
            if(!checkPermission(permission1))
            {
                per.add(permission1);
            }
            if(!checkPermission(permission2))
            {
                per.add(permission2);
            }
            if(!checkPermission(permission3))
            {
                per.add(permission3);
            }
            if(!checkPermission(permission4))
            {
                per.add(permission4);
            }
            if(!checkPermission(permission5))
            {
                per.add(permission5);
            }
            if(!checkPermission(permission6))
            {
                per.add(permission6);
            }
            if(!checkPermission(permission7))
            {
                per.add(permission7);
            }
            if(!checkPermission(permission8))
            {
                per.add(permission8);
            }
            if(!checkPermission(permission9))
            {
                per.add(permission9);
            }
            String[] permissionarray = per.toArray(new String[per.size()]);
            requestPermissions(permissionarray, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("permission", "granted");
                    allpermission();
                } else {
                    Log.d("permission", "denied");
                    allpermission();
                }
                break;
            default:
                break;
        }
    }

    public void allpermission()
    {
        String login = prefs.getString("login","");
        if(login.equals("yes"))
        {
            SplashTimer(Splash.this,Home.class);
        }
        else {
            SplashTimer(Splash.this,MainActivity.class);
        }
    }
    public static void SplashTimer(final Context con, final Class class1)
    {
        Thread timer= new Thread()
        {
            public void run()
            {
                try
                {
                    //Display for 3 seconds
                    sleep(5000);
                }
                catch (InterruptedException e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                finally
                {
                    Intent i = null;
                    i = new Intent(con,class1);
                    i.putExtra("name","Register");
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    con.startActivity(i);
                }
            }
        };
        timer.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        url_dump.deleteCache(getApplicationContext());
        super.onPause();
    }

    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
