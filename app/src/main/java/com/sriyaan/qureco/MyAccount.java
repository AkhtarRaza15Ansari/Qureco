package com.sriyaan.qureco;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriyaan.util.url_dump;

public class MyAccount extends AppCompatActivity {
    ImageView person;
    ImageView editPreferences,editProfile;
    TextView name,hcp_followers,followers,hcp_review,review,hcp_points,points;
    TextView tvName,tvMobile,tvGender,tvDob;
    SharedPreferences prefs;
    String user_name,mobile_no,profile_pic,sgender,sdob;
    Context con;
    Toolbar toolbar;
    String fontPath = "fonts/Montserrat-Regular.ttf";
    // Loading Font Face
    Typeface tf;

    TextView tvHome,tvNotification,tvChat,tvFavourites,tvAccounts;
    LinearLayout llhome,llnotification,llchat,llfavorites,llacounts;
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

        user_name = prefs.getString("cust_name","");
        mobile_no = prefs.getString("cust_mobile_no","");
        profile_pic = prefs.getString("cust_profile_pic","");
        sgender = prefs.getString("cust_gender","");
        sdob = prefs.getString("cust_dob","");
        String cap = user_name.substring(0, 1).toUpperCase() + user_name.substring(1);

        name.setText(cap);
        try {
            Picasso.with(con).load(profile_pic).placeholder(R.drawable.my_profile).into(person);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(con,EditProfile.class);
                startActivity(i);
            }
        });
        editPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(con,EditPreference.class);
                startActivity(i);
            }
        });

        llhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyAccount.this,Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        llnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyAccount.this,Notification.class);
                startActivity(i);
            }
        });
        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyAccount.this,Chat.class);
                startActivity(i);
            }
        });
        llfavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyAccount.this,Favourite.class);
                startActivity(i);
            }
        });
        llacounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyAccount.this,MyAccount.class);
                startActivity(i);
            }
        });
    }
    public void init()
    {
        toolbar = (Toolbar)     findViewById(R.id.toolbar);
        person  = (ImageView)   findViewById(R.id.person);

        name            = (TextView)    findViewById(R.id.name);
        hcp_followers   = (TextView) findViewById(R.id.hcp_followers);
        followers       = (TextView) findViewById(R.id.followers);
        hcp_review      = (TextView) findViewById(R.id.hcp_review);
        review          = (TextView) findViewById(R.id.review);
        hcp_points      = (TextView) findViewById(R.id.hcp_points);
        points          = (TextView) findViewById(R.id.points);

        editPreferences = (ImageView) findViewById(R.id.editPreference);
        editProfile     = (ImageView) findViewById(R.id.editProfile);

        tvHome          = (TextView)        findViewById(R.id.tvHome);
        tvNotification  = (TextView)        findViewById(R.id.tvNotification);
        tvChat          = (TextView)        findViewById(R.id.tvChat);
        tvFavourites    = (TextView)        findViewById(R.id.tvFavourites);
        tvAccounts      = (TextView)        findViewById(R.id.tvAccounts);
        llhome          = (LinearLayout)    findViewById(R.id.homell);
        llnotification  = (LinearLayout)    findViewById(R.id.notificationll);
        llchat          = (LinearLayout)    findViewById(R.id.chatll);
        llfavorites     = (LinearLayout)    findViewById(R.id.favouritesll);
        llacounts       = (LinearLayout)    findViewById(R.id.accountsll);

    }

    public void setFonts()
    {
        name            .setTypeface(tf);
        hcp_followers   .setTypeface(tf);
        followers       .setTypeface(tf);
        hcp_review      .setTypeface(tf);
        review          .setTypeface(tf);
        hcp_points      .setTypeface(tf);
        points          .setTypeface(tf);

        tvHome          .setTypeface(tf);
        tvNotification  .setTypeface(tf);
        tvChat          .setTypeface(tf);
        tvFavourites    .setTypeface(tf);
        tvAccounts      .setTypeface(tf);
    }


    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }

}
