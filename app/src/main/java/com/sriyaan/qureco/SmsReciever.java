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

import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.sriyaan.util.url_dump.Logthis;
import static com.sriyaan.util.url_dump.OTPSender;
import static com.sriyaan.util.url_dump.OTPSenderLogin;
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
    Context context;
    String json;
    String str_Code,str_Message,str_UserID;
    String hcp_cust_id,hcp_cust_name,hcp_cust_mobile_no,hcp_cust_gender,hcp_cust_dob,hcp_cust_referral_code,hcp_cust_profile_pic,hcp_cust_interests,hcp_cust_map_lat,hcp_cust_map_long;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_reciever);
        context     = SmsReciever.this;
        prefs       = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);
        type        = prefs.getString("type","");
        mobile      = prefs.getString("mobile","");

        codeotp     = (EditText)    findViewById(R.id.inputOtp);
        resend      = (TextView)    findViewById(R.id.resend);
        text        = (TextView)    this.findViewById(R.id.timer);
        btnEnter    = (Button)      findViewById(R.id.btn_verify_otp);

        code        = codeotp.getText().toString();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("OTP Reciever Page");

        btnEnter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String message  = codeotp.getText().toString();
                code            = message;
                new UserRegister().execute();
            }
        });
        timerfunction();
    }

    public void timerfunction()
    {
        new CountDownTimer(60000, 1000){
            public void onTick(long millisUntilFinished){
                text.setText("Please wait till " + millisUntilFinished / 1000 + " seconds");
            }
            public void onFinish(){
                text.setText("Please enter the otp in the box below");
                try {
                    resend.setVisibility(View.VISIBLE);
                    resend.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view){
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
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url_dump.startprogress("Fetching Data","Please wait",SmsReciever.this,false);
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
                url_dump.dismissprogress();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            url_dump.dismissprogress();
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
                            JSONObject object1 = object.getJSONObject(3);
                            hcp_cust_id = object1.getString("hcp_cust_id");
                            hcp_cust_name = object1.getString("hcp_cust_name");
                            hcp_cust_mobile_no = object1.getString("hcp_cust_mobile_no");
                            hcp_cust_profile_pic = object1.getString("hcp_cust_profile_pic");
                            hcp_cust_gender = object1.getString("hcp_cust_gender");
                            hcp_cust_dob = object1.getString("hcp_cust_dob");
                            hcp_cust_referral_code = object1.getString("hcp_cust_referral_code");
                            hcp_cust_interests = object1.getString("hcp_cust_interests");
                            hcp_cust_map_lat = object1.getString("hcp_cust_map_lat");
                            hcp_cust_map_long = object1.getString("hcp_cust_map_long");

                            Logthis("hcp_cust_profile_pic",hcp_cust_profile_pic);

                            prefs.edit().putString("cust_id",hcp_cust_id).apply();
                            prefs.edit().putString("cust_name",hcp_cust_name).apply();
                            prefs.edit().putString("cust_mobile_no",hcp_cust_mobile_no).apply();
                            prefs.edit().putString("cust_profile_pic",hcp_cust_profile_pic).apply();
                            prefs.edit().putString("cust_gender",hcp_cust_gender).apply();
                            prefs.edit().putString("cust_dob",hcp_cust_dob).apply();
                            prefs.edit().putString("cust_referral_code",hcp_cust_referral_code).apply();
                            prefs.edit().putString("cust_interests",hcp_cust_interests).apply();
                            prefs.edit().putString("cust_map_lat",hcp_cust_map_lat).apply();
                            prefs.edit().putString("cust_map_long",hcp_cust_map_long).apply();
                            prefs.edit().putString("login","yes").apply();

                            Intent i = new Intent(context,Home.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                            finish();
                        }
                        else if(str_Code.equals("HCPC501"))
                        {
                            //Some Parameters are Missing
                            Toastthis(str_Message,context);
                        }
                        else if(str_Code.equals("HCPC502"))
                        {
                            //User Already Registered
                            Toastthis(str_Message,context);
                        }
                        else if(str_Code.equals("HCPC503"))
                        {
                            //User Already Registered
                            Toastthis(str_Message,context);
                        }
                    }
                    else {
                        if(str_Code.equals("HCPC300"))
                        {
                            //Successfull
                            JSONObject object1 = object.getJSONObject(3);
                            hcp_cust_id = object1.getString("hcp_cust_id");
                            hcp_cust_name = object1.getString("hcp_cust_name");
                            hcp_cust_mobile_no = object1.getString("hcp_cust_mobile_no");
                            hcp_cust_profile_pic = object1.getString("hcp_cust_profile_pic");
                            hcp_cust_gender = object1.getString("hcp_cust_gender");
                            hcp_cust_dob = object1.getString("hcp_cust_dob");
                            hcp_cust_referral_code = object1.getString("hcp_cust_referral_code");
                            hcp_cust_interests = object1.getString("hcp_cust_interests");
                            hcp_cust_map_lat = object1.getString("hcp_cust_map_lat");
                            hcp_cust_map_long = object1.getString("hcp_cust_map_long");

                            Logthis("hcp_cust_profile_pic",hcp_cust_profile_pic);

                            prefs.edit().putString("cust_id",hcp_cust_id).apply();
                            prefs.edit().putString("cust_name",hcp_cust_name).apply();
                            prefs.edit().putString("cust_mobile_no",hcp_cust_mobile_no).apply();
                            prefs.edit().putString("cust_profile_pic",hcp_cust_profile_pic).apply();
                            prefs.edit().putString("cust_gender",hcp_cust_gender).apply();
                            prefs.edit().putString("cust_dob",hcp_cust_dob).apply();
                            prefs.edit().putString("cust_referral_code",hcp_cust_referral_code).apply();
                            prefs.edit().putString("cust_interests",hcp_cust_interests).apply();
                            prefs.edit().putString("cust_map_lat",hcp_cust_map_lat).apply();
                            prefs.edit().putString("cust_map_long",hcp_cust_map_long).apply();
                            prefs.edit().putString("login","yes").apply();
                            Intent i = new Intent(context,Home.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                            finish();
                        }
                        else if(str_Code.equals("HCPC301"))
                        {
                            //Some Parameters are Missing
                            Toastthis(str_Message,context);
                        }
                        else if(str_Code.equals("HCPC302"))
                        {
                            //User Already Registered
                            Toastthis(str_Message,context);
                        }
                        else if(str_Code.equals("HCPC303"))
                        {
                            //User Already Registered
                            Toastthis(str_Message,context);
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
    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }
}
