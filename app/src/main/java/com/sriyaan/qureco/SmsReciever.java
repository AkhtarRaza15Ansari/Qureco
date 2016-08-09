package com.sriyaan.qureco;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;

import static com.sriyaan.util.url_dump.OTPSender;
import static com.sriyaan.util.url_dump.OTPSenderLogin;
import static com.sriyaan.util.url_dump.SplashTimer;
import static com.sriyaan.util.url_dump.Toastthis;

public class SmsReciever extends AppCompatActivity {
    private BroadcastReceiver registrationBroadcastReceiver;
    Button btnEnter;
    private static final String TAG = "SmsReciever";
    public TextView text;
    TextView title, resend;
    EditText codeotp;
    String code;
    String deviceToken;
    final SmsManager sms = SmsManager.getDefault();
    public String msg = "Dear User, Your One Time Password (OTP) is: 2597 for Verify on Qureco";
    int startIndex = 44;
    int endIndex = 48;
    String mobile;
    SharedPreferences prefs;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_reciever);
        prefs = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);
        type = prefs.getString("type","");
        mobile = prefs.getString("mobile","");
        codeotp = (EditText) findViewById(R.id.inputOtp);
        code = codeotp.getText().toString();
        resend = (TextView) findViewById(R.id.resend);
        setTitle("SMS Verification");
        text = (TextView) this.findViewById(R.id.timer);
        btnEnter = (Button) findViewById(R.id.btn_verify_otp);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = codeotp.getText().toString();
                code = message;
                new UserRegister().execute();
            }
        });
        timerfunction();
        String substr = msg.substring(startIndex,endIndex);
        Log.d("cscc",substr);
    }

    public void timerfunction()
    {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                text.setText("Please wait till " + millisUntilFinished / 1000 + " seconds");
            }

            public void onFinish() {
                text.setText("Please enter the otp in the box below");
                try {
                    resend.setVisibility(View.VISIBLE);
                    resend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            resend.setVisibility(View.GONE);

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public class UserRegister extends AsyncTask<Void,Void,Void> {
        String json;
        String str_Code,str_Message,str_UserID;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                if(type.equals("login"))
                {
                    json = OTPSenderLogin(mobile,code);
                }
                else {
                    json = OTPSender(mobile,code);
                }

                Log.d("json","This is it: "+json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try{
                if(!json.equals(""))
                {
                    JSONArray object = new JSONArray(json);
                    str_Code = object.get(0).toString();
                    str_Message = object.get(1).toString();
                    str_UserID = object.get(2).toString();
                    Log.d("Code",str_Code);
                    Log.d("Mesg",str_Message);
                    Log.d("UsID",str_UserID);
                    if(type.equals("login"))
                    {
                        if(str_Code.equals("HCPC500"))
                        {
                            //Successfull
                            prefs.edit().putString("type","login").apply();
                            Intent i = new Intent(SmsReciever.this,Home.class);
                            startActivity(i);
                            finish();
                        }
                        else if(str_Code.equals("HCPC501"))
                        {
                            //Some Parameters are Missing
                            Toastthis(str_Message,SmsReciever.this);
                        }
                        else if(str_Code.equals("HCPC502"))
                        {
                            //User Already Registered
                            Toastthis(str_Message,SmsReciever.this);
                            onBackPressed();
                        }
                        else if(str_Code.equals("HCPC503"))
                        {
                            //User Already Registered
                            Toastthis(str_Message,SmsReciever.this);
                        }
                    }
                    else {
                        if(str_Code.equals("HCPC300"))
                        {
                            //Successfull
                            prefs.edit().putString("type","login").apply();
                            Intent i = new Intent(SmsReciever.this,Home.class);
                            startActivity(i);
                            finish();
                        }
                        else if(str_Code.equals("HCPC301"))
                        {
                            //Some Parameters are Missing
                            Toastthis(str_Message,SmsReciever.this);
                        }
                        else if(str_Code.equals("HCPC302"))
                        {
                            //User Already Registered
                            Toastthis(str_Message,SmsReciever.this);
                            onBackPressed();
                        }
                        else if(str_Code.equals("HCPC303"))
                        {
                            //User Already Registered
                            Toastthis(str_Message,SmsReciever.this);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public class UserLogin extends AsyncTask<Void,Void,Void> {
        String json;
        String str_Code,str_Message,str_UserID;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                json = OTPSenderLogin(mobile,code);
                Log.d("json","This is it: "+json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try{
                if(!json.equals(""))
                {
                    JSONArray object = new JSONArray(json);
                    str_Code = object.get(0).toString();
                    str_Message = object.get(1).toString();
                    str_UserID = object.get(2).toString();
                    Log.d("Code",str_Code);
                    Log.d("Mesg",str_Message);
                    Log.d("UsID",str_UserID);

                    if(str_Code.equals("HCPC500"))
                    {
                        //Successfull
                        prefs.edit().putString("type","login").apply();
                        Intent i = new Intent(SmsReciever.this,Home.class);
                        startActivity(i);
                        finish();
                    }
                    else if(str_Code.equals("HCPC501"))
                    {
                        //Some Parameters are Missing
                        Toastthis(str_Message,SmsReciever.this);
                    }
                    else if(str_Code.equals("HCPC502"))
                    {
                        //User Already Registered
                        Toastthis(str_Message,SmsReciever.this);
                        onBackPressed();
                    }
                    else if(str_Code.equals("HCPC503"))
                    {
                        //User Already Registered
                        Toastthis(str_Message,SmsReciever.this);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }
}
