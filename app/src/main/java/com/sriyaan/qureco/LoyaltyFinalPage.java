package com.sriyaan.qureco;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class LoyaltyFinalPage extends AppCompatActivity {

    String fontPath = "fonts/Montserrat-Regular.ttf";
    // Loading Font Face
    Typeface tf;
    EditText etbillamount;
    RadioGroup one;
    RadioButton oneaccumulate,bothaccred;
    TextView qurecopoints,tvqurecopoints,currentbillamount,shownext,makeyourselection,request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalty_final_page);

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
        mTitle.setText("Loyalty");

        init();

        setFont();
    }

    public void init(){

        etbillamount        =   (EditText)      findViewById(R.id.etbillamount);
        qurecopoints        =   (TextView)      findViewById(R.id.qurecopoints);
        tvqurecopoints      =   (TextView)      findViewById(R.id.tvqurecopoints);
        currentbillamount   =   (TextView)      findViewById(R.id.currentbillamount);
        currentbillamount   =   (TextView)      findViewById(R.id.currentbillamount);
        shownext            =   (TextView)      findViewById(R.id.shownext);
        makeyourselection   =   (TextView)      findViewById(R.id.makeyourselection);
        request             =   (TextView)      findViewById(R.id.request);
        one                 =   (RadioGroup)    findViewById(R.id.one);
        oneaccumulate       =   (RadioButton)   findViewById(R.id.oneaccumulate);
        bothaccred          =   (RadioButton)   findViewById(R.id.bothaccred);

    }

    public void setFont()
    {

        etbillamount        .setTypeface(tf);
        qurecopoints        .setTypeface(tf);
        tvqurecopoints      .setTypeface(tf);
        currentbillamount   .setTypeface(tf);
        currentbillamount   .setTypeface(tf);
        shownext            .setTypeface(tf);
        makeyourselection   .setTypeface(tf);
        request             .setTypeface(tf);
        oneaccumulate       .setTypeface(tf);
        bothaccred          .setTypeface(tf);

    }

}
