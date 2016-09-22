package com.sriyaan.qureco;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.sriyaan.modal.DetailsData;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompareScreen extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

    TextView text1,text2,headPrice,price1,price2,headAmenities,list1,list2,headDistance,distance1,distance2
            ,headReview,headPoints,points1,points2;
    ImageView img1,img2,one1,one2,two1,two2,three1,three2,four1,four2,five1,five2;
    Typeface tf;
    String fontPath = "fonts/Montserrat-Regular.ttf";
    String user_id,hcp_id1,hcp_id2;
    Toolbar toolbar;
    JSONArray array;

    TextView tvHome,tvNotification,tvChat,tvFavourites,tvAccounts;
    LinearLayout llhome,llnotification,llchat,llfavorites,llacounts;

    GoogleApiClient mGoogleApiClient;
    String lat = "", longt = "";
    LatLng latLng;

    String name1,dis1,ratings1,refer_friend_points1,ams1,charges1;
    String name2,dis2,ratings2,refer_friend_points2,ams2,charges2;

    String scat1="1";//clinics
    String scat2="2";//hospitals
    String scat3="3";//pathlab
    String scat4="4";//fitness
    String scat5="5";//bloodbanks
    String scat6="6";//salon
    String scat7="7";//pharmacy
    String scat8="8";//doctors
    String scat9="9";//spa
    String value = "";
    SharedPreferences prefs;
    Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_screen);
        init();

        buildGoogleApiClient();

        mGoogleApiClient.connect();

        con = CompareScreen.this;
        prefs = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);

        user_id = prefs.getString("cust_id","1");
        hcp_id1 = getIntent().getStringExtra("hcp_id1");
        hcp_id2 = getIntent().getStringExtra("hcp_id2");
        value   = getIntent().getStringExtra("value");


        if(value.equals(scat1))
        {
            Picasso.with(con).load(R.drawable.clinic).into(img1);
            Picasso.with(con).load(R.drawable.clinic).into(img2);
        }
        else if(value.equals(scat2))
        {
            Picasso.with(con).load(R.drawable.hospital).into(img1);
            Picasso.with(con).load(R.drawable.hospital).into(img2);
        }
        else if(value.equals(scat3))
        {
            Picasso.with(con).load(R.drawable.pathlab).into(img1);
            Picasso.with(con).load(R.drawable.pathlab).into(img2);
        }
        else if(value.equals(scat4))
        {
            Picasso.with(con).load(R.drawable.fitness).into(img1);
            Picasso.with(con).load(R.drawable.fitness).into(img2);
        }
        else if(value.equals(scat5))
        {
            Picasso.with(con).load(R.drawable.bloodbank).into(img1);
            Picasso.with(con).load(R.drawable.bloodbank).into(img2);
        }
        else if(value.equals(scat6))
        {
            Picasso.with(con).load(R.drawable.salon).into(img1);
            Picasso.with(con).load(R.drawable.salon).into(img2);
        }
        else if(value.equals(scat7))
        {
            Picasso.with(con).load(R.drawable.pharmacy).into(img1);
            Picasso.with(con).load(R.drawable.pharmacy).into(img2);
        }
        else if(value.equals(scat8))
        {
            Picasso.with(con).load(R.drawable.doctor).into(img1);
            Picasso.with(con).load(R.drawable.doctor).into(img2);
        }
        else if(value.equals(scat9))
        {
            Picasso.with(con).load(R.drawable.spa).into(img1);
            Picasso.with(con).load(R.drawable.spa).into(img2);
        }

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
        mTitle.setText("Compare HCP");

        setFonts();
        llhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompareScreen.this,Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        llnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompareScreen.this,Notification.class);
                startActivity(i);
            }
        });
        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompareScreen.this,Chat.class);
                startActivity(i);
            }
        });
        llfavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompareScreen.this,Favourite.class);
                startActivity(i);
            }
        });
        llacounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(CompareScreen.this,MyAccount.class);
                startActivity(i);
            }
        });
    }


    public void init()
    {
        text1       = (TextView)    findViewById(R.id.text1);
        text2       = (TextView)    findViewById(R.id.text2);
        headPrice       = (TextView)    findViewById(R.id.headPrice);
        price1       = (TextView)    findViewById(R.id.price1);
        price2       = (TextView)    findViewById(R.id.price2);
        headAmenities       = (TextView)    findViewById(R.id.headAmenities);
        list1       = (TextView)    findViewById(R.id.list1);
        list2       = (TextView)    findViewById(R.id.list2);
        headDistance       = (TextView)    findViewById(R.id.headDistance);
        distance1       = (TextView)    findViewById(R.id.distance1);
        distance2       = (TextView)    findViewById(R.id.distance2);
        headReview       = (TextView)    findViewById(R.id.headReview);
        headPoints       = (TextView)    findViewById(R.id.headPoints);
        points1       = (TextView)    findViewById(R.id.points1);
        points2       = (TextView)    findViewById(R.id.points2);
        toolbar       = (Toolbar)     findViewById(R.id.toolbar);


        img1       = (ImageView)    findViewById(R.id.img1);
        img2       = (ImageView)    findViewById(R.id.img2);

        one1       = (ImageView)    findViewById(R.id.one1);
        two1       = (ImageView)    findViewById(R.id.two1);
        three1       = (ImageView)    findViewById(R.id.three1);
        four1       = (ImageView)    findViewById(R.id.four1);
        five1       = (ImageView)    findViewById(R.id.five1);

        one2       = (ImageView)    findViewById(R.id.one2);
        two2       = (ImageView)    findViewById(R.id.two2);
        three2       = (ImageView)    findViewById(R.id.three2);
        four2       = (ImageView)    findViewById(R.id.four2);
        five2       = (ImageView)    findViewById(R.id.five2);

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

    public void setFonts()
    {
        text1       .setTypeface(tf);
        text2       .setTypeface(tf);
        headPrice    .setTypeface(tf);
        price1       .setTypeface(tf);
        price2       .setTypeface(tf);
        headAmenities       .setTypeface(tf);
        list1       .setTypeface(tf);
        list2       .setTypeface(tf);
        headDistance       .setTypeface(tf);
        distance1       .setTypeface(tf);
        distance2       .setTypeface(tf);
        headReview       .setTypeface(tf);
        headPoints       .setTypeface(tf);
        points1       .setTypeface(tf);
        points2       .setTypeface(tf);

        tvHome          .setTypeface(tf);
        tvNotification  .setTypeface(tf);
        tvChat          .setTypeface(tf);;
        tvFavourites    .setTypeface(tf);
        tvAccounts      .setTypeface(tf);
    }
    public class Compare extends AsyncTask<Void,Void,Void> {
        String json_response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

                json_response = url_dump.compareTwoHCP(user_id,hcp_id1,hcp_id2,lat,longt);
                JSONArray jsonArray = new JSONArray(json_response);

                array = jsonArray.getJSONArray(2);
                for (int i = 0; i < array.length(); i++) {
                    if(i==0)
                    {

                        JSONObject jsonObject = array.getJSONObject(i);
                        JSONObject services = jsonObject.getJSONObject("services");
                        name1 = services.getString("service_name");
                        JSONArray location = jsonObject.getJSONArray("location");
                        for(int l=0;l<location.length();l++)
                        {
                            JSONObject LocationObject = location.getJSONObject(l);
                            dis1 = LocationObject.getString("distance");
                            refer_friend_points1 = LocationObject.getString("refer_friend_points");

                            JSONArray amenities = LocationObject.getJSONArray("amenities");
                            for(int a=0;a<amenities.length();a++)
                            {
                                JSONObject object = amenities.getJSONObject(a);
                                ArrayList<String> arrAmnts = new ArrayList<>();
                                arrAmnts.add(object.getString("amenities_name"));

                                ams1 = android.text.TextUtils.join(",", arrAmnts);
                            }
                            JSONArray availability = LocationObject.getJSONArray("availability");
                            for(int j = 0;j<availability.length();j++)
                            {
                                JSONObject object = availability.getJSONObject(j);

                                charges1 = object.getString("charges");
                            }
                            try {
                                ratings1 = LocationObject.getString("ratings");
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                    if(i==1)
                    {
                        JSONObject jsonObject = array.getJSONObject(i);
                        JSONObject services = jsonObject.getJSONObject("services");
                        name2 = services.getString("service_name");
                        JSONArray location = jsonObject.getJSONArray("location");
                        for(int l=0;l<location.length();l++)
                        {
                            JSONObject LocationObject = location.getJSONObject(l);
                            dis2 = LocationObject.getString("distance");
                            refer_friend_points2 = LocationObject.getString("refer_friend_points");

                            JSONArray amenities = LocationObject.getJSONArray("amenities");
                            for(int a=0;a<amenities.length();a++)
                            {
                                JSONObject object = amenities.getJSONObject(a);
                                ArrayList<String> arrAmnts = new ArrayList<>();
                                arrAmnts.add(object.getString("amenities_name"));

                                ams2 = android.text.TextUtils.join(",", arrAmnts);
                            }
                            JSONArray availability = LocationObject.getJSONArray("availability");
                            for(int j = 0;j<availability.length();j++)
                            {
                                JSONObject object = availability.getJSONObject(j);

                                charges2 = object.getString("charges");
                            }

                            try {
                                ratings2 = LocationObject.getString("ratings");
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }


                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setTextHere();
        }
    }
    public void setTextHere()
    {
        text1.setText(name1);
        text2.setText(name2);
        price1.setText(charges1);
        price2.setText(charges2);
        list1.setText(ams1);
        list2.setText(ams2);
        String dist1 = String.format("%.2f", Double.valueOf(dis1));
        String dist2 = String.format("%.2f", Double.valueOf(dis2));
        distance1.setText(dist1+" Kms");
        distance2.setText(dist2+" Kms");
        points1.setText(refer_friend_points1);
        points2.setText(refer_friend_points2);
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    protected synchronized void buildGoogleApiClient() {
        //Toast.makeText(this,"buildGoogleApiClient",Toast.LENGTH_SHORT).show();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }
    @Override
    public void onConnected(Bundle bundle) {
        //Toast.makeText(this,"onConnected",Toast.LENGTH_SHORT).show();
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            //place marker at current position
            latLng = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng).zoom(14).build();

            Log.d("arg0", latLng.latitude + "-" + latLng.longitude);
            lat     = String.valueOf(latLng.latitude);
            longt   = String.valueOf(latLng.longitude);

            new Compare().execute();
        }
        else{
            new AlertDialog.Builder(con)
                    .setTitle("Location Disabled")
                    .setMessage("Please switch on your location to proceed")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                            onBackPressed();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}

