package com.sriyaan.qureco;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sriyaan.qr.IntentIntegrator;
import com.sriyaan.qr.IntentResult;
import com.sriyaan.qr.QrCode;
import com.sriyaan.util.url_dump;

import org.w3c.dom.Text;

public class Loyalty extends AppCompatActivity {
    ImageView qrcodescanner;
    TextView textQr,nameandaddress;
    Button btnConfirm;
    String message = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalty);
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
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Loyalty");
        init();
        qrcodescanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Loyalty.this, QrCode.class);
                startActivityForResult(i, 2);
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public void init(){
        qrcodescanner   = (ImageView)   findViewById(R.id.qrcodescanner);
        textQr          = (TextView)    findViewById(R.id.textQr);
        nameandaddress  = (TextView)    findViewById(R.id.nameandaddress);
        btnConfirm      = (Button)      findViewById(R.id.confirm);


        btnConfirm.setVisibility(View.GONE);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            if (null != intent) {
                // fetch the message String
                message = intent.getStringExtra("MESSAGE");
                // Set the message string in textView
                try {
                    if(message.equals("") || message.equals("null") || message == null)
                    {
                        //do nothing
                    }
                    else {
                        textQr.setText("Hi XXXX: We see you are in California");//+ message
                        nameandaddress.setText("Name: Ansari Akhtar's GYM \nAddress: A - 103, Oasis Park, Beverly Hills California.");
                        btnConfirm.setVisibility(View.VISIBLE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }
}
