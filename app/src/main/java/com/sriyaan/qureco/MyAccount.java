package com.sriyaan.qureco;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.sriyaan.util.url_dump;

public class MyAccount extends AppCompatActivity {
    ImageView person;
    TextView name,Mobile,gender,dob;
    SharedPreferences prefs;
    String user_name,mobile_no,profile_pic,sgender,sdob;
    Context con;
    Toolbar toolbar;
    String fontPath = "fonts/Montserrat-Regular.ttf";
    // Loading Font Face
    Typeface tf;
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

        user_name = prefs.getString("cust_name","");
        mobile_no = prefs.getString("cust_mobile_no","");
        profile_pic = prefs.getString("cust_profile_pic","");
        sgender = prefs.getString("cust_gender","");
        sdob = prefs.getString("cust_dob","");

        name.setText(user_name);
        Mobile.setText(mobile_no);
        gender.setText(sgender);
        dob.setText(sdob);
        Picasso.with(con).load(profile_pic).placeholder(R.drawable.person).into(person);

    }
    public void init()
    {
        toolbar = (Toolbar)     findViewById(R.id.toolbar);
        person  = (ImageView)   findViewById(R.id.person);
        name    = (TextView)    findViewById(R.id.name);
        Mobile  = (TextView)    findViewById(R.id.Mobile);
        gender  = (TextView)    findViewById(R.id.gender);
        dob     = (TextView)    findViewById(R.id.dob);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.editPreference) {
            Intent i = new Intent(con,EditPreference.class);
            startActivity(i);
            //url_dump.Toastthis("Coming soon",con);
            return true;
        }
        if (id == R.id.editProfile) {
            Intent i = new Intent(con,EditProfile.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }

}
