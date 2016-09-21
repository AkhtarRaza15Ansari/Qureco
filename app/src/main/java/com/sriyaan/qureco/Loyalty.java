package com.sriyaan.qureco;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sriyaan.qr.IntentIntegrator;
import com.sriyaan.qr.IntentResult;
import com.sriyaan.qr.QrCode;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.sriyaan.util.url_dump.Toastthis;

public class Loyalty extends AppCompatActivity {
    ImageView qrcodescanner;
    TextView textQr,nameandaddress;
    Button btnConfirm;
    String message = "";
    String fontPath = "fonts/Montserrat-Regular.ttf";
    // Loading Font Face
    Typeface tf;
    SharedPreferences prefs;
    String cust_id,cust_name;
    Context con;
    String hcp_id,name,address,location;
    Spinner spinner;
    ArrayList<String> array,array_hcp,array_spinner;
    TextView tvHome,tvNotification,tvChat,tvFavourites,tvAccounts;
    LinearLayout llhome,llnotification,llchat,llfavorites,llacounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalty);
        prefs = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);
        con = Loyalty.this;

        cust_id = prefs.getString("cust_id","1");
        cust_name = prefs.getString("cust_name","");

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
        setFonts();
        
        qrcodescanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Loyalty.this, QrCode.class);
                startActivityForResult(i, 2);
            }
        });


        llhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Loyalty.this,Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        llnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Loyalty.this,Notification.class);
                startActivity(i);
            }
        });
        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Loyalty.this,Chat.class);
                startActivity(i);
            }
        });
        llfavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Loyalty.this,Favourite.class);
                startActivity(i);
            }
        });
        llacounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Loyalty.this,MyAccount.class);
                startActivity(i);
            }
        });
    }
    public void init(){
        qrcodescanner   = (ImageView)   findViewById(R.id.qrcodescanner);
        textQr          = (TextView)    findViewById(R.id.textQr);
        nameandaddress  = (TextView)    findViewById(R.id.nameandaddress);
        btnConfirm      = (Button)      findViewById(R.id.confirm);
        spinner         = (Spinner)     findViewById(R.id.spinner);
        
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
        
        btnConfirm.setVisibility(View.GONE);
    }
    public void setFonts() {
        tvHome          .setTypeface(tf);
        tvNotification  .setTypeface(tf);
        tvChat          .setTypeface(tf);;
        tvFavourites    .setTypeface(tf);
        tvAccounts      .setTypeface(tf);
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
                        textQr.setText("Hi "+cust_name+": We see you are in California");//+ message
                        //nameandaddress.setText("Name: Ansari Akhtar's GYM \nAddress: A - 103, Oasis Park, Beverly Hills California.");

                        btnConfirm.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.VISIBLE);
                        new Loyalty1().execute();
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

    public class Loyalty1 extends AsyncTask<Void,Void,Void> {
        String json;
        String str_Code;
        String str_Message;
        String str_UserID;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            array = new ArrayList<>();
            array_hcp = new ArrayList<>();
            array_spinner = new ArrayList<>();
            url_dump.startprogress("Fetching Data","Please wait",con,false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                json = url_dump.LoyaltyQR(cust_id,message);
            } catch (Exception e) {
                url_dump.dismissprogress();
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            url_dump.dismissprogress();
            try{
                if(!json.equals(""))
                {
                    JSONArray object = new JSONArray(json);
                    str_Code = object.get(0).toString();
                    str_Message = object.get(1).toString();
                    Log.d("Code",str_Code);
                    Log.d("Mesg",str_Message);
                    JSONArray object1 = object.getJSONArray(2);
                    if(str_Code.equals("HCPC1200"))
                    {
                        //Successfull
                        //prefs.edit().putString("cust_interests",object1.getString("hcp_cust_interests")).apply();

                        for(int i=0;i<object1.length();i++)
                        {
                            JSONObject intern = object1.getJSONObject(i);
                            hcp_id = intern.getString("hcp_id");
                            name = intern.getString("service_name");
                            location = intern.getString("location_name");
                            address = intern.getString("location_address");
                            array.add("Name: "+name+" \nLocation: "+location+" \nAddress: "+address);
                            array_hcp.add(hcp_id);
                            array_spinner.add(name+": "+location);
                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                                    Loyalty.this, android.R.layout.simple_spinner_item, array_spinner);
                            spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                            spinner.setAdapter(spinnerArrayAdapter);
                            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                                    nameandaddress.setText(array.get(position));
                                    hcp_id = array_hcp.get(position);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                            btnConfirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent i = new Intent(Loyalty.this,LoyaltyFinalPage.class);
                                    i.putExtra("user_id",cust_id);
                                    i.putExtra("hcp_id",hcp_id);
                                    startActivity(i);
                                    finish();
                                }
                            });
                        }
                        //onBackPressed();
                    }
                    else if(str_Code.equals("HCPC1201"))
                    {
                        //Some Parameters are Missing
                        Toastthis(str_Message,con);
                    }
                    else
                    {
                        Toastthis(str_Message,con);
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
}
