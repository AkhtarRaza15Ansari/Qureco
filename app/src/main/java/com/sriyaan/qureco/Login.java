package com.sriyaan.qureco;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sriyaan.util.url_dump;

public class Login extends AppCompatActivity {
    Button btnLogin;
    Toolbar toolbar;
    Context con;
    TextView tvSignUp,tvForgotPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Login");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(con,Home.class);
                startActivity(i);
                finish();
            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(con,Register.class);
                startActivity(i);
            }
        });
    }

    public void init(){
        con = Login.this;
        tvSignUp    = (TextView)findViewById(R.id.tvSignUp);
        toolbar     = (Toolbar) findViewById(R.id.toolbar);
        btnLogin    = (Button)  findViewById(R.id.btnLogin);
    }
    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }
}
