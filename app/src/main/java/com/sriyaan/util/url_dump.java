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
    public static String cust_categories = "hcp_get_all_cust_categories.php";
    // To fetch userregistration
    public static String userregistration = "hcp_customer_registration.php";
    // To fetch userlogin
    public static String userlogin = "hcp_customer_login.php";
    // For SMS Verification Registration
    public static String sms_verification = "hcp_customer_registration_confirm.php";
    // For SMS Verification Login
    public static String sms_verification_login = "hcp_customer_otp_verify.php";
    // For Update in Preference
    public static String update_preference= "hcp_customer_edit_preferences.php";
    // For Update in Profile
    public static String update_profile= "hcp_customer_edit_profile.php";
    // For Getting Search List
    public static String search_by_category = "hcp_search_by_category.php";
    // For Loyalty QR
    public static String loyaltyQr = "hcp_get_hcp_id_qrcode.php";

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
    public static String removeLastChar(String str) {
        Log.d("previous",str);
        String str_return= "";
        Log.d("final",str.substring(0,str.length()-1));
        String lastWord = str.substring(str.lastIndexOf(" ")+1);
        if(lastWord.equals(","))
        {
            str_return = str.substring(0,str.length()-1);
        }
        else{
            str_return = str;
        }
        return str_return;
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
    public static String GetCategories() throws Exception {
        String url = main_header + cust_categories;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
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

    public static String updatePreference(String user_id,String interests) throws Exception {
        String url = main_header + update_preference;
        function1(url);
        // add your data
        interests = interests.trim();
        interests = removeLastChar(interests);
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        Log.d("user_id", user_id+" ::UserID");
        Log.d("interests", interests+" ::Interest");
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("interests", interests));
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


    public static String doFileUpload(String name,String mobile_no,String gender,String dob,String referral_code,String interests,String map_lat,String map_long,String profile_pic,String life_saver,String blood_group) throws Exception {
        String urlString = main_header + userregistration;
        String sResponse;
        StringBuilder s = new StringBuilder();
        try
        {
            interests = removeLastChar(interests);
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
            reqEntity.addPart("map_lat", new StringBody(map_lat));
            reqEntity.addPart("map_long", new StringBody(map_long));
            reqEntity.addPart("life_saver",new StringBody(life_saver));
            reqEntity.addPart("blood_group",new StringBody(blood_group));

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

    public static String updateProfile(String user_id,String name,String dob,String old_profile_pic,String map_lat,String map_long,String profile_pic,String life_saver,String blood_group) throws Exception {
        String urlString = main_header + update_profile;
        String sResponse;
        StringBuilder s = new StringBuilder();
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urlString);

            MultipartEntity reqEntity = new MultipartEntity();
            Log.d("prf",profile_pic+" Akhtar");
            if(!profile_pic.trim().equals(""))
            {
                File fmain = new File(profile_pic);
                FileBody binmain = new FileBody(fmain);
                reqEntity.addPart("profile_pic", binmain);
                Log.d("image", profile_pic);
            }
            reqEntity.addPart("user_id", new StringBody(user_id));
            reqEntity.addPart("old_profile_pic", new StringBody(old_profile_pic));
            reqEntity.addPart("name", new StringBody(name));
            reqEntity.addPart("dob", new StringBody(dob));
            reqEntity.addPart("map_lat", new StringBody(map_lat));
            reqEntity.addPart("map_long", new StringBody(map_long));
            reqEntity.addPart("life_saver",new StringBody(life_saver));
            reqEntity.addPart("blood_group",new StringBody(blood_group));

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
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
    public static String getSearchCategory(String user_id,String cat_id,String sort_by,
                    String open_hours,String fees,String open_now,String loyalty, String service_type,
                    String open_days,String gender) throws Exception {
        String url = main_header + search_by_category;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("cat_id", cat_id));
        nameValuePairs.add(new BasicNameValuePair("sort_by", sort_by));
        nameValuePairs.add(new BasicNameValuePair("open_hours", open_hours));
        nameValuePairs.add(new BasicNameValuePair("fees", fees));
        nameValuePairs.add(new BasicNameValuePair("open_now", open_now));
        nameValuePairs.add(new BasicNameValuePair("loyalty", loyalty));
        nameValuePairs.add(new BasicNameValuePair("service_type", service_type));
        nameValuePairs.add(new BasicNameValuePair("open_days", open_days));
        nameValuePairs.add(new BasicNameValuePair("gender", gender));
        nameValuePairs.add(new BasicNameValuePair("lat", "19.186418"));
        nameValuePairs.add(new BasicNameValuePair("long", "73.021341"));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String LoyaltyQR(String user_id,String qr_code) throws Exception {
        String url = main_header + loyaltyQr;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("qr_code", qr_code));
        Log.d("user_id", user_id);
        Log.d("qr_code", qr_code);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
}
