package com.sriyaan.qr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.sriyaan.qureco.R;

public class QrCode extends AppCompatActivity {
    Button btnQRCode;
    String contantsString;
    String re ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new com.google.zxing.integration.android.IntentIntegrator(QrCode.this).initiateScan();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            re = scanResult.getContents();
        }
        // else continue with any other code you need in the method
        Intent intentMessage=new Intent();

        // put the message in Intent
        intentMessage.putExtra("MESSAGE",re);
        // Set The Result in Intent
        setResult(2,intentMessage);
        // finish The activity
        finish();
    }
}
