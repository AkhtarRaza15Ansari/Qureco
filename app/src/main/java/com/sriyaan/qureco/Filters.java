package com.sriyaan.qureco;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

public class Filters extends AppCompatActivity {
    TextView free,one,two,three;
    TextView book,call;
    TextView mon,tue,wed,thurs,fri,sat,sun;
    TextView beforenoon,afternoon,evening,night;
    Button apply;
    Switch open;
    int llone = 0,lltwo = 0,llthree = 0,llfour = 0;
    String fontPath = "fonts/Montserrat-Regular.ttf";
    // Loading Font Face
    Typeface tf;

    TextView tvHome,tvNotification,tvChat,tvFavourites,tvAccounts;
    LinearLayout llhome,llnotification,llchat,llfavorites,llacounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        mTitle.setText("Filters");
        init();
        setFont();
        //set the switch to ON
        open.setChecked(true);
        //attach a listener to check for changes in state
        open.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    SearchListPage.open = "1";
                }else{
                    SearchListPage.open = "0";
                }
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.onRefresh = true;
                onBackPressed();
            }
        });
        free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.fees = "0";
                free.setTextColor(getResources().getColor(R.color.colorPrimary));
                //free.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                one.setTextColor(getResources().getColor(R.color.black));
                //one.setBackgroundColor(getResources().getColor(R.color.white));

                two.setTextColor(getResources().getColor(R.color.black));
                //two.setBackgroundColor(getResources().getColor(R.color.white));

                three.setTextColor(getResources().getColor(R.color.black));
                //three.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.fees = "1";
                free.setTextColor(getResources().getColor(R.color.black));
                //free.setBackgroundColor(getResources().getColor(R.color.white));

                one.setTextColor(getResources().getColor(R.color.colorPrimary));
                //one.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                two.setTextColor(getResources().getColor(R.color.black));
                //two.setBackgroundColor(getResources().getColor(R.color.white));

                three.setTextColor(getResources().getColor(R.color.black));
                //three.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.fees = "2";
                free.setTextColor(getResources().getColor(R.color.black));
                //free.setBackgroundColor(getResources().getColor(R.color.white));

                one.setTextColor(getResources().getColor(R.color.black));
                //one.setBackgroundColor(getResources().getColor(R.color.white));

                two.setTextColor(getResources().getColor(R.color.colorPrimary));
                //two.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                three.setTextColor(getResources().getColor(R.color.black));
                //three.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.fees = "3";
                free.setTextColor(getResources().getColor(R.color.black));
                //free.setBackgroundColor(getResources().getColor(R.color.white));

                one.setTextColor(getResources().getColor(R.color.black));
                //one.setBackgroundColor(getResources().getColor(R.color.white));

                two.setTextColor(getResources().getColor(R.color.black));
                //two.setBackgroundColor(getResources().getColor(R.color.white));

                three.setTextColor(getResources().getColor(R.color.colorPrimary));
                //three.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.service_type = "1";
                book.setTextColor(getResources().getColor(R.color.colorPrimary));
                //book.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                call.setTextColor(getResources().getColor(R.color.black));
                //call.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.service_type = "2";
                book.setTextColor(getResources().getColor(R.color.black));
                //book.setBackgroundColor(getResources().getColor(R.color.white));

                call.setTextColor(getResources().getColor(R.color.colorPrimary));
                //call.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });

        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.open_days = "1";
                mon.setTextColor(getResources().getColor(R.color.colorPrimary));
                //mon.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                tue.setTextColor(getResources().getColor(R.color.black));
                //tue.setBackgroundColor(getResources().getColor(R.color.white));

                wed.setTextColor(getResources().getColor(R.color.black));
                //wed.setBackgroundColor(getResources().getColor(R.color.white));

                thurs.setTextColor(getResources().getColor(R.color.black));
                //thurs.setBackgroundColor(getResources().getColor(R.color.white));

                fri.setTextColor(getResources().getColor(R.color.black));
                //fri.setBackgroundColor(getResources().getColor(R.color.white));

                sat.setTextColor(getResources().getColor(R.color.black));
                //sat.setBackgroundColor(getResources().getColor(R.color.white));

                sun.setTextColor(getResources().getColor(R.color.black));
                //sun.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.open_days = "2";
                mon.setTextColor(getResources().getColor(R.color.black));
                //mon.setBackgroundColor(getResources().getColor(R.color.white));

                tue.setTextColor(getResources().getColor(R.color.colorPrimary));
                //tue.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                wed.setTextColor(getResources().getColor(R.color.black));
                //wed.setBackgroundColor(getResources().getColor(R.color.white));

                thurs.setTextColor(getResources().getColor(R.color.black));
                //thurs.setBackgroundColor(getResources().getColor(R.color.white));

                fri.setTextColor(getResources().getColor(R.color.black));
                //fri.setBackgroundColor(getResources().getColor(R.color.white));

                sat.setTextColor(getResources().getColor(R.color.black));
                //sat.setBackgroundColor(getResources().getColor(R.color.white));

                sun.setTextColor(getResources().getColor(R.color.black));
                //sun.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.open_days = "3";
                mon.setTextColor(getResources().getColor(R.color.black));
                //mon.setBackgroundColor(getResources().getColor(R.color.white));

                tue.setTextColor(getResources().getColor(R.color.black));
                //tue.setBackgroundColor(getResources().getColor(R.color.white));

                wed.setTextColor(getResources().getColor(R.color.colorPrimary));
                //wed.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                thurs.setTextColor(getResources().getColor(R.color.black));
                //thurs.setBackgroundColor(getResources().getColor(R.color.white));

                fri.setTextColor(getResources().getColor(R.color.black));
                //fri.setBackgroundColor(getResources().getColor(R.color.white));

                sat.setTextColor(getResources().getColor(R.color.black));
                //sat.setBackgroundColor(getResources().getColor(R.color.white));

                sun.setTextColor(getResources().getColor(R.color.black));
                //sun.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        thurs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.open_days = "4";
                mon.setTextColor(getResources().getColor(R.color.black));
                //mon.setBackgroundColor(getResources().getColor(R.color.white));

                tue.setTextColor(getResources().getColor(R.color.black));
                //tue.setBackgroundColor(getResources().getColor(R.color.white));

                wed.setTextColor(getResources().getColor(R.color.black));
                //wed.setBackgroundColor(getResources().getColor(R.color.white));

                thurs.setTextColor(getResources().getColor(R.color.colorPrimary));
                //thurs.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                fri.setTextColor(getResources().getColor(R.color.black));
                //fri.setBackgroundColor(getResources().getColor(R.color.white));

                sat.setTextColor(getResources().getColor(R.color.black));
                //sat.setBackgroundColor(getResources().getColor(R.color.white));

                sun.setTextColor(getResources().getColor(R.color.black));
                //sun.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.open_days = "5";
                mon.setTextColor(getResources().getColor(R.color.black));
                //mon.setBackgroundColor(getResources().getColor(R.color.white));

                tue.setTextColor(getResources().getColor(R.color.black));
                //tue.setBackgroundColor(getResources().getColor(R.color.white));

                wed.setTextColor(getResources().getColor(R.color.black));
                //wed.setBackgroundColor(getResources().getColor(R.color.white));

                thurs.setTextColor(getResources().getColor(R.color.black));
                //thurs.setBackgroundColor(getResources().getColor(R.color.white));

                fri.setTextColor(getResources().getColor(R.color.colorPrimary));
                //fri.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                sat.setTextColor(getResources().getColor(R.color.black));
                //sat.setBackgroundColor(getResources().getColor(R.color.white));

                sun.setTextColor(getResources().getColor(R.color.black));
                //sun.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.open_days = "6";
                mon.setTextColor(getResources().getColor(R.color.black));
                //mon.setBackgroundColor(getResources().getColor(R.color.white));

                tue.setTextColor(getResources().getColor(R.color.black));
                //tue.setBackgroundColor(getResources().getColor(R.color.white));

                wed.setTextColor(getResources().getColor(R.color.black));
                //wed.setBackgroundColor(getResources().getColor(R.color.white));

                thurs.setTextColor(getResources().getColor(R.color.black));
                //thurs.setBackgroundColor(getResources().getColor(R.color.white));

                fri.setTextColor(getResources().getColor(R.color.black));
                //fri.setBackgroundColor(getResources().getColor(R.color.white));

                sat.setTextColor(getResources().getColor(R.color.colorPrimary));
                //sat.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                sun.setTextColor(getResources().getColor(R.color.black));
                //sun.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.open_days = "7";
                mon.setTextColor(getResources().getColor(R.color.black));
                //mon.setBackgroundColor(getResources().getColor(R.color.white));

                tue.setTextColor(getResources().getColor(R.color.black));
                //tue.setBackgroundColor(getResources().getColor(R.color.white));

                wed.setTextColor(getResources().getColor(R.color.black));
                //wed.setBackgroundColor(getResources().getColor(R.color.white));

                thurs.setTextColor(getResources().getColor(R.color.black));
                //thurs.setBackgroundColor(getResources().getColor(R.color.white));

                fri.setTextColor(getResources().getColor(R.color.black));
                //fri.setBackgroundColor(getResources().getColor(R.color.white));

                sat.setTextColor(getResources().getColor(R.color.black));
                //sat.setBackgroundColor(getResources().getColor(R.color.white));

                sun.setTextColor(getResources().getColor(R.color.colorPrimary));
                //sun.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });


        beforenoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.open_hours = "1";
                beforenoon.setTextColor(getResources().getColor(R.color.colorPrimary));
                //beforenoon.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                afternoon.setTextColor(getResources().getColor(R.color.black));
                //afternoon.setBackgroundColor(getResources().getColor(R.color.white));

                evening.setTextColor(getResources().getColor(R.color.black));
                //evening.setBackgroundColor(getResources().getColor(R.color.white));

                night.setTextColor(getResources().getColor(R.color.black));
                //night.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        afternoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.open_hours = "2";
                afternoon.setTextColor(getResources().getColor(R.color.colorPrimary));
                //afternoon.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                evening.setTextColor(getResources().getColor(R.color.black));
                //evening.setBackgroundColor(getResources().getColor(R.color.white));

                night.setTextColor(getResources().getColor(R.color.black));
                //night.setBackgroundColor(getResources().getColor(R.color.white));

                beforenoon.setTextColor(getResources().getColor(R.color.black));
                //beforenoon.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.open_hours = "3";

                evening.setTextColor(getResources().getColor(R.color.colorPrimary));
                //evening.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                night.setTextColor(getResources().getColor(R.color.black));
                //night.setBackgroundColor(getResources().getColor(R.color.white));

                beforenoon.setTextColor(getResources().getColor(R.color.black));
                //beforenoon.setBackgroundColor(getResources().getColor(R.color.white));

                afternoon.setTextColor(getResources().getColor(R.color.black));
                //afternoon.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });
        night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchListPage.open_hours = "4";
                night.setTextColor(getResources().getColor(R.color.colorPrimary));
                //night.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                beforenoon.setTextColor(getResources().getColor(R.color.black));
                //beforenoon.setBackgroundColor(getResources().getColor(R.color.white));

                afternoon.setTextColor(getResources().getColor(R.color.black));
                //afternoon.setBackgroundColor(getResources().getColor(R.color.white));

                evening.setTextColor(getResources().getColor(R.color.black));
                //evening.setBackgroundColor(getResources().getColor(R.color.white));
            }
        });

        llhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Filters.this,Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        llnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Filters.this,Notification.class);
                startActivity(i);
            }
        });
        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Filters.this,Chat.class);
                startActivity(i);
            }
        });
        llfavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Filters.this,Favourite.class);
                startActivity(i);
            }
        });
        llacounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Filters.this,MyAccount.class);
                startActivity(i);
            }
        });
    }

    public void init()
    {
        open            = (Switch)      findViewById(R.id.open);
        free            = (TextView)    findViewById(R.id.free);
        one             = (TextView)    findViewById(R.id.one);
        two             = (TextView)    findViewById(R.id.two);
        three           = (TextView)    findViewById(R.id.three);

        book            = (TextView)    findViewById(R.id.book);
        call            = (TextView)    findViewById(R.id.call);

        mon             = (TextView)    findViewById(R.id.mon);
        tue             = (TextView)    findViewById(R.id.tue);
        wed             = (TextView)    findViewById(R.id.wed);
        thurs           = (TextView)    findViewById(R.id.thurs);
        fri             = (TextView)    findViewById(R.id.fri);
        sat             = (TextView)    findViewById(R.id.sat);
        sun             = (TextView)    findViewById(R.id.sun);

        beforenoon      = (TextView)    findViewById(R.id.beforenoon);
        afternoon       = (TextView)    findViewById(R.id.afternoon);
        evening         = (TextView)    findViewById(R.id.evening);
        night           = (TextView)    findViewById(R.id.night);

        apply           = (Button)      findViewById(R.id.apply);

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

    public void setFont()
    {
        tvHome          .setTypeface(tf);
        tvNotification  .setTypeface(tf);
        tvChat          .setTypeface(tf);;
        tvFavourites    .setTypeface(tf);
        tvAccounts      .setTypeface(tf);
    }
}

