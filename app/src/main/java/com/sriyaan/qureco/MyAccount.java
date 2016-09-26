package com.sriyaan.qureco;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriyaan.fragments.FollowingTabs;
import com.sriyaan.fragments.PointsEarned;
import com.sriyaan.fragments.PointsRedeemed;
import com.sriyaan.fragments.ReviewTab;
import com.sriyaan.modal.DataObject;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyAccount extends AppCompatActivity {
    ImageView person;
    ImageView editPreferences, editProfile;
    TextView name, hcp_followers, followers, hcp_review, review, hcp_points, points;
    TextView tvName, tvMobile, tvGender, tvDob;
    String user_name, mobile_no, profile_pic, sgender, sdob;
    Context con;
    Toolbar toolbar;
    SharedPreferences prefs;
    String user_id;
    String fontPath = "fonts/Montserrat-Regular.ttf";
    // Loading Font Face
    Typeface tf;
    public static boolean resume = false;
    TextView tvHome, tvNotification, tvChat, tvFavourites, tvAccounts;
    LinearLayout llhome, llnotification, llchat, llfavorites, llacounts;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        con = MyAccount.this;
        prefs = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);
        init();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });
        setTitle("");
        tf = Typeface.createFromAsset(getAssets(), fontPath);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setTypeface(tf);
        mTitle.setText("My Profile");
        setFonts();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        user_id = prefs.getString("cust_id", "");
        loadData();

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(con, EditProfile.class);
                startActivity(i);
            }
        });
        editPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(con, EditPreference.class);
                startActivity(i);
            }
        });

        llhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyAccount.this, Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        llnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyAccount.this, Notification.class);
                startActivity(i);
            }
        });
        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyAccount.this, Chat.class);
                startActivity(i);
            }
        });
        llfavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyAccount.this, Favourite.class);
                startActivity(i);
            }
        });
        llacounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        new Type().execute();
    }

    public void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        person = (ImageView) findViewById(R.id.person);

        name = (TextView) findViewById(R.id.name);
        hcp_followers = (TextView) findViewById(R.id.hcp_followers);
        followers = (TextView) findViewById(R.id.followers);
        hcp_review = (TextView) findViewById(R.id.hcp_review);
        review = (TextView) findViewById(R.id.review);
        hcp_points = (TextView) findViewById(R.id.hcp_points);
        points = (TextView) findViewById(R.id.points);

        editPreferences = (ImageView) findViewById(R.id.editPreference);
        editProfile = (ImageView) findViewById(R.id.editProfile);

        tvHome = (TextView) findViewById(R.id.tvHome);
        tvNotification = (TextView) findViewById(R.id.tvNotification);
        tvChat = (TextView) findViewById(R.id.tvChat);
        tvFavourites = (TextView) findViewById(R.id.tvFavourites);
        tvAccounts = (TextView) findViewById(R.id.tvAccounts);
        llhome = (LinearLayout) findViewById(R.id.homell);
        llnotification = (LinearLayout) findViewById(R.id.notificationll);
        llchat = (LinearLayout) findViewById(R.id.chatll);
        llfavorites = (LinearLayout) findViewById(R.id.favouritesll);
        llacounts = (LinearLayout) findViewById(R.id.accountsll);

    }

    public void setFonts() {
        name.setTypeface(tf);
        hcp_followers.setTypeface(tf);
        followers.setTypeface(tf);
        hcp_review.setTypeface(tf);
        review.setTypeface(tf);
        hcp_points.setTypeface(tf);
        points.setTypeface(tf);

        tvHome.setTypeface(tf);
        tvNotification.setTypeface(tf);
        tvChat.setTypeface(tf);
        tvFavourites.setTypeface(tf);
        tvAccounts.setTypeface(tf);
    }


    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (resume) {
            loadData();
        }
    }

    public void loadData() {
        user_name = prefs.getString("cust_name", "");
        mobile_no = prefs.getString("cust_mobile_no", "");
        profile_pic = prefs.getString("cust_profile_pic", "");
        sgender = prefs.getString("cust_gender", "");
        sdob = prefs.getString("cust_dob", "");
        String cap = user_name.substring(0, 1).toUpperCase() + user_name.substring(1);

        name.setText(cap);
        try {
            Picasso.with(con).load(profile_pic).placeholder(R.drawable.my_profile).into(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class Type extends AsyncTask<Void, Void, Void> {
        String json_response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

                json_response = url_dump.getDashboardDetails(user_id);
                Log.d("Read", json_response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                JSONArray array = new JSONArray(json_response);
                String code = array.getString(0);
                String message = array.getString(1);
                if (code.equals("HCPC800")) {


                    JSONObject object = array.getJSONObject(2);
                    String msg_count = object.getString("msg_count");
                    String following_count = object.getString("following_count");
                    String review_count = object.getString("review_count");
                    String total_points = object.getString("total_points");
                    String cust_name = object.getString("cust_name");
                    String profile_pic = object.getString("profile_pic");
                    followers.setText(following_count);
                    review.setText(review_count);
                    points.setText(total_points);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FollowingTabs(), "Following");
        adapter.addFragment(new ReviewTab(), "Review");
        adapter.addFragment(new PointsEarned(), "Pts Earned");
        adapter.addFragment(new PointsRedeemed(), "Pts Redeemed");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
