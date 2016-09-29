package com.sriyaan.qureco;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sriyaan.adapter.RecyclerAdapterRatings;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoyaltyFinalPage extends AppCompatActivity {

    String fontPath = "fonts/Montserrat-Regular.ttf";
    // Loading Font Face
    Typeface tf;
    EditText etbillamount;
    RadioGroup one;
    RadioButton oneaccumulate,bothaccred;
    String json_response;
    String user_id,hcp_id;
    String price_total="",redeem_point,accumulate_point,request_option;
    String total_points,finalbill;
    TextView qurecopoints,tvqurecopoints,currentbillamount,shownext,makeyourselection,request;
    TextView tvHome,tvNotification,tvChat,tvFavourites,tvAccounts;
    LinearLayout llhome,llnotification,llchat,llfavorites,llacounts;
    String verify_pin = "";
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

        try {
            user_id = getIntent().getStringExtra("user_id");
            hcp_id = getIntent().getStringExtra("hcp_id");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        setFont();

        makeyourselection.setVisibility(View.GONE);
        one.setVisibility(View.GONE);
        request.setVisibility(View.GONE);

        shownext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bill = etbillamount.getText().toString();
                if(bill.equals(""))
                {
                    Toast.makeText(LoyaltyFinalPage.this, "Please enter something", Toast.LENGTH_SHORT).show();
                }
                else{
                    price_total = bill;
                    makeyourselection.setVisibility(View.VISIBLE);
                    one.setVisibility(View.VISIBLE);
                    request.setVisibility(View.VISIBLE);
                    Log.d("price",price_total);
                    new RedeemPoints().execute();
                }
            }
        });
        new getTotalPoints().execute();

        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int redeem_option = one.getCheckedRadioButtonId();
                if(redeem_option == oneaccumulate.getId())
                {
                    request_option = "0";
                }
                else if(redeem_option == bothaccred.getId())
                {
                    request_option = "1";
                }

                final Dialog dialog = new Dialog(LoyaltyFinalPage.this);
                // Include dialog.xml file
                dialog.setContentView(R.layout.enterpasscode);
                // Set dialog title
                dialog.setTitle("Custom Dialog");

                // set values for custom dialog components - text, image and button
                final EditText passcode = (EditText) dialog.findViewById(R.id.passcode);

                dialog.show();

                Button submit= (Button) dialog.findViewById(R.id.submit);
                // if decline button is clicked, close the custom dialog
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Close dialog
                        verify_pin = passcode.getText().toString();
                        if(verify_pin.length()<4)
                        {
                            Toast.makeText(LoyaltyFinalPage.this, "Please enter 4 digit code to proceed", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            new SubmitAsync().execute();
                            dialog.dismiss();
                        }
                    }
                });

            }
        });
        llhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoyaltyFinalPage.this,Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        llnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoyaltyFinalPage.this,Notification.class);
                startActivity(i);
            }
        });
        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoyaltyFinalPage.this,Chat.class);
                startActivity(i);
            }
        });
        llfavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoyaltyFinalPage.this,Favourite.class);
                startActivity(i);
            }
        });
        llacounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoyaltyFinalPage.this,MyAccount.class);
                startActivity(i);
            }
        });
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

        tvHome          .setTypeface(tf);
        tvNotification  .setTypeface(tf);
        tvChat          .setTypeface(tf);;
        tvFavourites    .setTypeface(tf);
        tvAccounts      .setTypeface(tf);

    }
    public class getTotalPoints extends AsyncTask<Void,Void,Void> {
        String str_Code,str_Message;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                json_response = url_dump.getTotalPoints(user_id);
                Log.d("response",json_response);
                JSONArray array = new JSONArray(json_response);
                str_Code = array.get(0).toString();
                str_Message = array.get(1).toString();
                total_points = array.get(2).toString();
                Log.d("Code",str_Code);
                Log.d("Mesg",str_Message);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(str_Code.equals("HCPC2300"))
            {
                tvqurecopoints.setText(total_points+" Points");
            }
            else{
                Toast.makeText(LoyaltyFinalPage.this, ""+str_Message, Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class RedeemPoints extends AsyncTask<Void,Void,Void> {
        String str_Code,str_Message;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                json_response = url_dump.RequestRedeemPoints(user_id,hcp_id,price_total);
                Log.d("response",json_response);
                JSONArray array = new JSONArray(json_response);
                str_Code = array.get(0).toString();
                str_Message = array.get(1).toString();
                Log.d("Code",str_Code);
                Log.d("Mesg",str_Message);
                JSONObject object = array.getJSONObject(2);
                redeem_point=  object.getString("redeem_point");
                accumulate_point =  object.getString("accumulate_point");

            } catch (Exception ex) {
                ex.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if(str_Code.equals("HCPC2400"))
            {
                bothaccred.setText("Redeem "+redeem_point+" points for a discount of Rs."+price_total+" and accumulate "+accumulate_point+" points");
                oneaccumulate.setText("Accumulate "+accumulate_point+" points");
            }
            else{
                Toast.makeText(LoyaltyFinalPage.this, ""+str_Message, Toast.LENGTH_SHORT).show();
            }
            oneaccumulate.setChecked(true);
            etbillamount.setEnabled(false);
        }
    }
    public class SubmitAsync extends AsyncTask<Void,Void,Void>
    {
        String json_values;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try{
                json_values = url_dump.RequestRedeem(user_id,hcp_id,price_total,redeem_point,accumulate_point,request_option,verify_pin);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                JSONArray array = new JSONArray(json_values);
                String code  =  array.getString(0);
                String message = array.getString(1);
                if(code.equals("HCPC2500"))
                {
                    Toast.makeText(LoyaltyFinalPage.this, ""+message, Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoyaltyFinalPage.this, ""+message, Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
