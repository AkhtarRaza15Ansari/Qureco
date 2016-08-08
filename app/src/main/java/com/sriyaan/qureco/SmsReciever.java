package com.sriyaan.qureco;

import android.content.BroadcastReceiver;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SmsReciever extends AppCompatActivity {
    private BroadcastReceiver registrationBroadcastReceiver;
    Button btnEnter;
    private static final String TAG = "SmsReciever";
    public TextView text;
    TextView title, resend;
    EditText codeotp;
    String code;
    String deviceToken;
    final SmsManager sms = SmsManager.getDefault();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_reciever);
        codeotp = (EditText) findViewById(R.id.inputOtp);
        code = codeotp.getText().toString();
        resend = (TextView) findViewById(R.id.resend);
        setTitle(" SMS Verification");
        text = (TextView) this.findViewById(R.id.timer);
        btnEnter = (Button) findViewById(R.id.btn_verify_otp);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = codeotp.getText().toString();
                code = message;
                //new PostDataAsyncTask().execute();
            }
        });
        timerfunction();
    }

    public void timerfunction()
    {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                text.setText("Please wait till " + millisUntilFinished / 1000 + " seconds");
            }

            public void onFinish() {
                text.setText("Please enter the otp in the box below");
                try {
                    resend.setVisibility(View.VISIBLE);
                    resend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            resend.setVisibility(View.GONE);

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
