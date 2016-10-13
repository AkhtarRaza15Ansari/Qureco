package com.sriyaan.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by akhtarraza on 23/07/16.
 */
public class url_dump {


    public static String IMEI_Number_Holder;

    public static TelephonyManager telephonyManager;
    // Url For Terms and Conditions Page
    public static String tncHeader = "http://qureco.com/terms-conditions.php";
    // Url For About Page
    public static String aboutHeader = "http://qureco.com/aboutus.php";
    public static String main_header = "http://54.153.69.73/~hcp/hcp_customer_webservices/";
    // To fetch deviceregistration
    public static String cust_categories = "hcp_get_all_cust_categories.php";
    // To fetch userregistration
    public static String userregistration = "hcp_customer_registration.php";
    // To fetch userlogin
    public static String userlogin = "hcp_customer_login.php";
    // To resend sms otp
    public static String sms_resend = "hcp_customer_resend_otp.php";
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
    // For Fetching Review KPIs
    public static String fetchReviewKPI = "hcp_get_hcp_rating_kpis.php";
    // For Details Page
    public static String detailsPage = "hcp_get_hcp_details.php";
    // For Send Follow Request
    public static String sendFollow = "hcp_do_follow.php";
    // For Send UnFollow Request
    public static String sendUnFollow = "hcp_do_unfollow.php";
    // For Compare Two HCP
    public static String comparingHCP = "hcp_get_compare_results.php";
    // For HCP Review
    public static String hcp_review = "hcp_save_hcp_rating.php";
    // For Getting Deals
    public static String fetch_deals = "hcp_get_deals_by_category.php";
    // For Total Points
    public static String total_points = "hcp_get_customer_current_balance.php";
    // For Get Redeem Points
    public static String redeem_points = "hcp_get_redeem_accumulate_details.php";
    // For Redeem Request
    public static String redeem_request = "hcp_redeem_accumulate_request.php";
    // For LifeSavers List
    public static String lifesaver_list = "hcp_get_lifesaver_list.php";
    // For Dashboard Page
    public static String geteDashboard = "hcp_customer_dashboard.php";
    // For Notification list
    public static String getNotList = "hcp_get_customer_notifications.php";
    // For Following List
    public static String getFolList = "hcp_get_customer_following_list.php";
    // For Review List
    public static String getRevList = "hcp_get_customer_write_review_list";
    // For Points Earned
    public static String ptsEarnedList = "hcp_get_customer_earned_points_list.php";
    // For Points Redeemed
    public static String ptsRedeemedList = "hcp_get_customer_redeem_points_list.php";


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
    public static String LoginUser(String mobile,String gcm_code) throws Exception {
        String url = main_header + userlogin;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile));
        nameValuePairs.add(new BasicNameValuePair("gsm_code",gcm_code));
        Log.d("mobile",mobile);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }

    public static String OTPResend(String mobile_no) throws Exception {
        String url = main_header + sms_resend;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("mobile", mobile_no));
        Log.d("mobile_no",mobile_no+"");
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


    public static String doFileUpload(String name,String mobile_no,String gender,String dob,String referral_code,String interests,String map_lat,String map_long,String profile_pic,String life_saver,String blood_group,String gcm_code) throws Exception {
        String urlString = main_header + userregistration;
        String sResponse;
        StringBuilder s = new StringBuilder();
        try
        {
            interests = removeLastChar(interests);
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(urlString);

            MultipartEntity reqEntity = new MultipartEntity();
            if(!profile_pic.equals(""))
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
            reqEntity.addPart("gsm_code",new StringBody(gcm_code));

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
                    String open_days,String gender,String lat,String longt) throws Exception {
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
        nameValuePairs.add(new BasicNameValuePair("lat", lat));
        nameValuePairs.add(new BasicNameValuePair("long", longt));
        Log.d("lat",lat+" ");
        Log.d("lon",longt+" ");
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String getSearchDeals(String hcp_id,String user_id,String cat_id,String sort_by,
                                           String page_no) throws Exception {
        String url = main_header + fetch_deals;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("hcp_id", hcp_id));
        nameValuePairs.add(new BasicNameValuePair("cat_id", cat_id));
        nameValuePairs.add(new BasicNameValuePair("sort_by", sort_by));
        nameValuePairs.add(new BasicNameValuePair("page_no", page_no));
        Log.d("user_id", user_id+" :: Append");
        Log.d("hcp_id", hcp_id+" :: Append");
        Log.d("cat_id", cat_id+" :: Append");
        Log.d("sort_by", sort_by+" :: Append");
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

    public static String getKPIs(String user_id,String hcp_id) throws Exception {
        String url = main_header + fetchReviewKPI;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("hcp_id", hcp_id));
        Log.d("user_id", user_id);
        Log.d("qr_code", hcp_id);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String getDetails(String user_id,String hcp_id) throws Exception {
        String url = main_header + detailsPage;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("hcp_id", hcp_id));
        Log.d("user_id", user_id);
        Log.d("hcp_id", hcp_id);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String sendFollow(String user_id,String hcp_id) throws Exception {
        String url = main_header + sendFollow;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("hcp_id", hcp_id));
        Log.d("user_id", user_id);
        Log.d("hcp_id", hcp_id);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String sendUnFollow(String user_id,String hcp_id) throws Exception {
        String url = main_header + sendUnFollow;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("hcp_id", hcp_id));
        Log.d("user_id", user_id);
        Log.d("hcp_id", hcp_id);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String compareTwoHCP(String user_id,String hcp_id1,String hcp_id2,String lat,String longt) throws Exception
    {
        String url = main_header + comparingHCP;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("hcp_id1", hcp_id1));
        nameValuePairs.add(new BasicNameValuePair("hcp_id2", hcp_id2));
        nameValuePairs.add(new BasicNameValuePair("lat", lat));
        nameValuePairs.add(new BasicNameValuePair("long", longt));
        Log.d("user_id", user_id);
        Log.d("hcp_id1", hcp_id1);
        Log.d("hcp_id2", hcp_id2);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String saveRatings(String user_id,String hcp_id,String review_text,String review_id,String review_star,String promo_code) throws Exception
    {
        String url = main_header + hcp_review;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("hcp_id", hcp_id));
        nameValuePairs.add(new BasicNameValuePair("review_text", review_text));
        nameValuePairs.add(new BasicNameValuePair("review_id", review_id));
        nameValuePairs.add(new BasicNameValuePair("review_star", review_star));
        nameValuePairs.add(new BasicNameValuePair("promo_code", promo_code));
        Log.d("user_id", user_id);
        Log.d("hcp_id1", hcp_id);
        Log.d("review_text", review_text);
        Log.d("review_id", review_id);
        Log.d("review_star", review_star);
        Log.d("promo_code", promo_code);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String getTotalPoints(String user_id) throws Exception
    {
        String url = main_header + total_points;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        Log.d("user_id", user_id);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String RequestRedeemPoints(String user_id,String hcp_id,String price_total) throws Exception
    {
        String url = main_header + redeem_points;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("hcp_id", hcp_id));
        nameValuePairs.add(new BasicNameValuePair("price_total", price_total));
        Log.d("user_id", user_id);
        Log.d("hcp_id", hcp_id);
        Log.d("price_total", price_total);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String RequestRedeem(String user_id,String hcp_id,String price_total,String redeem_point,String accumulate_point,String request_option,String verify_pin) throws Exception
    {
        String url = main_header + redeem_request;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("hcp_id", hcp_id));
        nameValuePairs.add(new BasicNameValuePair("price_total", price_total));
        nameValuePairs.add(new BasicNameValuePair("redeem_point", redeem_point));
        nameValuePairs.add(new BasicNameValuePair("accumulate_point", accumulate_point));
        nameValuePairs.add(new BasicNameValuePair("request_option", request_option));
        nameValuePairs.add(new BasicNameValuePair("verify_pin", verify_pin));

        Log.d("user_id", user_id);
        Log.d("hcp_id", hcp_id);
        Log.d("price_total", price_total);
        Log.d("redeem_point", redeem_point);
        Log.d("accumulate_point", accumulate_point);
        Log.d("request_option", request_option);
        Log.d("verify_pin", verify_pin);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }

    public static String getLifeSaverList(String user_id,String lat,String longt) throws Exception
    {
        String url = main_header + lifesaver_list  ;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        nameValuePairs.add(new BasicNameValuePair("lat", lat));
        nameValuePairs.add(new BasicNameValuePair("long", longt));
        Log.d("user_id", user_id);
        Log.d("lat", lat);
        Log.d("long", longt);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }

    public static String getDashboardDetails(String user_id) throws Exception
    {
        String url = main_header + geteDashboard;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        Log.d("user_id", user_id);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String getNotification(String user_id) throws Exception
    {
        String url = main_header + getNotList;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        Log.d("user_id", user_id);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String getFollowingList(String user_id) throws Exception
    {
        String url = main_header + getFolList;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        Log.d("user_id", user_id);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String getReviewList(String user_id) throws Exception
    {
        String url = main_header + getRevList;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        Log.d("user_id", user_id);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String getPointsEarned(String user_id) throws Exception
    {
        String url = main_header + ptsEarnedList;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        Log.d("user_id", user_id);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }
    public static String getPointsRedeemed(String user_id) throws Exception
    {
        String url = main_header + ptsRedeemedList;
        function1(url);
        // add your data
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("user_id", user_id));
        Log.d("user_id", user_id);
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
        function2();
        return getDecode(jsonvalues);
    }

    public static String compressImage(String imageUri,Context con) {

        String filePath = getRealPathFromURI(imageUri,con);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

//      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//      you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

//      max Height and width values of the compressed image is taken as 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

//      width and height values are set maintaining the aspect ratio of the image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

//      setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//      inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

//      this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
//          load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//      check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);
//          write the compressed bitmap at the destination specified by filename.
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public static String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "Qureco/Images");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    public static String getRealPathFromURI(String contentURI, Context con) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = con.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }
    public static boolean isTimeBetweenTwoTime(String initialTime, String finalTime, String currentTime) throws ParseException {
        String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";

        if (initialTime.matches(reg) && finalTime.matches(reg) && currentTime.matches(reg)) {
            boolean valid = false;
            //Start Time
            java.util.Date inTime = new SimpleDateFormat("HH:mm:ss").parse(initialTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(inTime);

            //Current Time
            java.util.Date checkTime = new SimpleDateFormat("HH:mm:ss").parse(currentTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(checkTime);

            //End Time
            java.util.Date finTime = new SimpleDateFormat("HH:mm:ss").parse(finalTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(finTime);

            if (finalTime.compareTo(initialTime) < 0) {
                calendar2.add(Calendar.DATE, 1);
                calendar3.add(Calendar.DATE, 1);
            }

            java.util.Date actualTime = calendar3.getTime();
            if ((actualTime.after(calendar1.getTime()) || actualTime.compareTo(calendar1.getTime()) == 0)
                    && actualTime.before(calendar2.getTime())) {
                valid = true;
            }
            return valid;
        } else {
            throw new IllegalArgumentException("Not a valid time, expecting HH:MM:SS format");
        }

    }
}
