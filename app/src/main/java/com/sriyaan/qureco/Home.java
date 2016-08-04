package com.sriyaan.qureco;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Home extends AppCompatActivity {

    Toolbar toolbar;
    Context con;
    LinearLayout llsearch,llloyalty,lldeals,llfeedback,llreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo);
        setTitle("");

        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Home");

        llsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,Search.class);
                startActivity(i);
            }
        });
        llfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,Feedback.class);
                startActivity(i);
            }
        });
        llloyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this,Loyalty.class);
                startActivity(i);
            }
        });
    }
    public void init(){
        con         = Home.this;
        toolbar     = (Toolbar)     findViewById(R.id.toolbar);
        llsearch    = (LinearLayout)findViewById(R.id.search);
        llloyalty   = (LinearLayout)findViewById(R.id.loyalty);
        lldeals     = (LinearLayout)findViewById(R.id.deals);
        llfeedback  = (LinearLayout)findViewById(R.id.feedback);
        llreview    = (LinearLayout)findViewById(R.id.reviews);
    }
}
