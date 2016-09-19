package com.sriyaan.qureco;

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
    String price_total,redeem_point,accumulate_point,request_option;
    String total_points,finalbill;
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
                    finalbill = bill;
                    makeyourselection.setVisibility(View.VISIBLE);
                    one.setVisibility(View.VISIBLE);
                    request.setVisibility(View.VISIBLE);
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
                new SubmitAsync().execute();
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
                bothaccred.setText("Redeem "+redeem_point+" points for a discount of Rs."+finalbill+" and accumulate "+accumulate_point+" points");
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
                json_values = url_dump.RequestRedeem(user_id,hcp_id,price_total,redeem_point,accumulate_point,request_option);
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

            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
