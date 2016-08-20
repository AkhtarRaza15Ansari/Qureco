package com.sriyaan.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.sriyaan.qureco.Home;
import com.sriyaan.qureco.SmsReciever;
import com.sriyaan.util.RoutingActivity;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;

import static com.sriyaan.util.url_dump.DeviceRegistration;
import static com.sriyaan.util.url_dump.OTPSender;
import static com.sriyaan.util.url_dump.OTPSenderLogin;
import static com.sriyaan.util.url_dump.SplashTimer;
import static com.sriyaan.util.url_dump.Toastthis;

/**
 * Created by Akhtar on 09-08-2016.
 */
public class SmsBroadReciever extends BroadcastReceiver {
    final SmsManager sms = SmsManager.getDefault();
    Context con;
    public String msg = "Dear User, Your One Time Password (OTP) is: 2597 for Verify on Qureco";
    int startIndex = 44;
    int endIndex = 48;
    public String mobile;
    public String code;
    SharedPreferences prefs;
    String type;
    @Override
    public void onReceive(Context context, Intent intent) {
// Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();
        this.con = context;
        prefs = con.getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);
        type = prefs.getString("type","");
        Log.d("reciever","reciever");
        mobile = prefs.getString("mobile","");
        Log.d("cscc","coming");
        try {

            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");

                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                    if(senderNum.contains("TXTLCL"))
                    {
                        String substr = message.substring(startIndex,endIndex);
                        Log.d("cscc",substr);
                        code = substr;
                        new UserRegister().execute();
                    }
                } // end for loop
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
            e.printStackTrace();
        }
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
                            Intent i = new Intent(con,RoutingActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            con.startActivity(i);
                        }
                        else if(str_Code.equals("HCPC501"))
                        {
                            //Some Parameters are Missing
                            Toastthis(str_Message,con);
                        }
                        else if(str_Code.equals("HCPC502"))
                        {
                            //User Already Registered
                            Toastthis(str_Message,con);
                        }
                        else if(str_Code.equals("HCPC503"))
                        {
                            //User Already Registered
                            Toastthis(str_Message,con);
                        }
                    }
                    else {
                        if(str_Code.equals("HCPC300"))
                        {
                            //Successfull
                            prefs.edit().putString("type","login").apply();
                            Intent i = new Intent(con,RoutingActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            con.startActivity(i);
                        }
                        else if(str_Code.equals("HCPC301"))
                        {
                            //Some Parameters are Missing
                            Toastthis(str_Message,con);
                        }
                        else if(str_Code.equals("HCPC302"))
                        {
                            //User Already Registered
                            Toastthis(str_Message,con);
                        }
                        else if(str_Code.equals("HCPC303"))
                        {
                            //User Already Registered
                            Toastthis(str_Message,con);
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
}
