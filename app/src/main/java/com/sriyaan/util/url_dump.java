package com.sriyaan.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akhtarraza on 23/07/16.
 */
public class url_dump {


    public static String IMEI_Number_Holder;

    public static TelephonyManager telephonyManager;

    public static String main_header = "http://sriyaan.com/hcp/hcp_customer_webservices/";
    // To fetch deviceregistration
    public static String deviceregistration = "hcp_device_registration.php";
    // To fetch userregistration
    public static String userregistration = "hcp_customer_registration.php";

    public static void SplashTimer(final Context con, final Class class1)
    {
        Thread timer= new Thread()
        {
            public void run()
            {
                try
                {
                    //Display for 3 seconds
                    sleep(3000);
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
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    con.startActivity(i);
                }
            }
        };
        timer.start();

    }

    public static String jsonvalues;
    public static HttpClient httpClient;
    public static HttpResponse response;
    public static HttpPost httpPost;
    public static void Logthis(String key,String value)
    {
        Log.d(key, value);
    }
    public static void Toastthis(String message,Context context)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static ProgressDialog mProgressDialog;

    public static void function1(String url) throws Exception
    {
        String postReceiverUrl = url.trim();
        Log.v("TAG", "postURL: " + postReceiverUrl);

        // HttpClient
        httpClient = new DefaultHttpClient();

        // post header
        httpPost = new HttpPost(postReceiverUrl);
    }
    public static void function2() throws Exception
    {
        // execute HTTP post request
        response = httpClient.execute(httpPost);
        HttpEntity resEntity = response.getEntity();

        if (resEntity != null) {

            String responseStr = EntityUtils.toString(resEntity).trim();
            Log.v("TAG", "Response: " + responseStr);
            jsonvalues = responseStr;
            // you can add an if statement here and do other actions based on the response
        }
    }
    public static String DeviceRegistration(String email,Context context) throws Exception {
        String url = main_header + deviceregistration;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("imei", getImei(context)));
        nameValuePairs.add(new BasicNameValuePair("email", email));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String UserRegistration(String name,String mobile_no,String gender,String dob,String referral_code,String interests,String nationality,String profile_pic) throws Exception {
        String url = main_header + userregistration;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("name", name));
        nameValuePairs.add(new BasicNameValuePair("mobile_no", mobile_no));
        nameValuePairs.add(new BasicNameValuePair("gender", gender));
        nameValuePairs.add(new BasicNameValuePair("dob", dob));
        nameValuePairs.add(new BasicNameValuePair("referral_code", referral_code));
        nameValuePairs.add(new BasicNameValuePair("interests", interests));
        nameValuePairs.add(new BasicNameValuePair("nationality", nationality));
        nameValuePairs.add(new BasicNameValuePair("profile_pic", profile_pic));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String getImei(Context context) throws Exception
    {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        IMEI_Number_Holder = telephonyManager.getDeviceId();
        return IMEI_Number_Holder;
    }
    public static String getEncode(String text) throws Exception
    {
        // Sending side
        byte[] data = text.getBytes("UTF-8");
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    public static String getDecode(String base64) throws Exception
    {
        // decode
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        return new String(data, "UTF-8");
    }
}
