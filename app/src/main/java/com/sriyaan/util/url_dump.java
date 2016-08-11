package com.sriyaan.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
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
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
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
    // To fetch userlogin
    public static String userlogin = "hcp_customer_login.php";
    // For SMS Verification Registration
    public static String sms_verification = "hcp_customer_registration_confirm.php";
    // For SMS Verification Login
    public static String sms_verification_login = "hcp_customer_otp_verify.php";

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

    public static HttpEntity resEntity;
    public static void Logthis(String key,String value)
    {
        Log.d(key, value);
    }
    public static void Toastthis(String message,Context context)
    {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
    public static ProgressDialog mProgressDialog;
    public static void startprogress(String message,String title,Context context,Boolean cancelable)
    {
        mProgressDialog = new ProgressDialog(context);
        // Set progressdialog title
        mProgressDialog.setTitle(title);
        // Set progressdialog message
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(cancelable);
        mProgressDialog.setIndeterminate(false);
        // Show progressdialog
        mProgressDialog.show();
    }
    public static void dismissprogress()
    {
        mProgressDialog.dismiss();
    }
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
    public static String LoginUser(String mobile) throws Exception {
        String url = main_header + userlogin;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
        Log.d("mobile",mobile);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }

    public static String OTPSender(String mobile_no,String sms_code) throws Exception {
        String url = main_header + sms_verification;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("mobile_no", mobile_no));
        nameValuePairs.add(new BasicNameValuePair("sms_code", sms_code));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }

    public static String OTPSenderLogin(String mobile_no,String sms_code) throws Exception {
        String url = main_header + sms_verification_login;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile_no));
        nameValuePairs.add(new BasicNameValuePair("otp", sms_code));
        Log.d("mobile_no", mobile_no);
        Log.d("otp", sms_code);
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


    public static String doFileUpload(String name,String mobile_no,String gender,String dob,String referral_code,String interests,String nationality,String city,String profile_pic) throws Exception {
        String urlString = main_header + userregistration;
        String sResponse;
        StringBuilder s = new StringBuilder();
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urlString);

            MultipartEntity reqEntity = new MultipartEntity();
            if(profile_pic!=null)
            {
                File fmain = new File(profile_pic);
                FileBody binmain = new FileBody(fmain);
                reqEntity.addPart("profile_pic", binmain);
                Log.d("image", profile_pic);
            }
            reqEntity.addPart("name", new StringBody(name));
            reqEntity.addPart("mobile_no", new StringBody(mobile_no));
            reqEntity.addPart("gender", new StringBody(gender));
            reqEntity.addPart("dob", new StringBody(dob));
            reqEntity.addPart("referral_code", new StringBody(referral_code));
            reqEntity.addPart("interests", new StringBody(interests));
            reqEntity.addPart("nationality", new StringBody(nationality));
            reqEntity.addPart("city", new StringBody(city));

            post.setEntity(reqEntity);
            HttpResponse response = client.execute(post);
            resEntity = response.getEntity();
            jsonvalues = EntityUtils.toString(resEntity);
            if (resEntity != null) {
                Log.i(" RESPONSE", jsonvalues);
            }
        }
        catch (Exception ex) {
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        }
        return getDecode(jsonvalues);
    }
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
}
