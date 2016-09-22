package com.sriyaan.qureco;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sriyaan.adapter.DividerItemDecoration;
import com.sriyaan.adapter.ListAdapter;
import com.sriyaan.adapter.RecyclerAdapterSearch;
import com.sriyaan.adapter.RecyclerTouchListener;
import com.sriyaan.modal.DataObject;
import com.sriyaan.modal.ListData;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LifeSavers extends AppCompatActivity implements  GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    String fontPath = "fonts/Montserrat-Regular.ttf";
    // Loading Font Face
    Typeface tf;
    LatLng latLng;
    String lat = "", longt = "";
    Context con;
    private List<DataObject> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListAdapter mAdapter;
    SharedPreferences prefs;
    String user_id;
    GoogleApiClient mGoogleApiClient;

    TextView tvHome,tvNotification,tvChat,tvFavourites,tvAccounts;
    LinearLayout llhome,llnotification,llchat,llfavorites,llacounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_savers);
        con = LifeSavers.this;

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
        mTitle.setText("Life Savers");

        tvHome          .setTypeface(tf);
        tvNotification  .setTypeface(tf);
        tvChat          .setTypeface(tf);;
        tvFavourites    .setTypeface(tf);
        tvAccounts      .setTypeface(tf);

        prefs = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);
        user_id = prefs.getString("cust_id","");

        buildGoogleApiClient();

        mGoogleApiClient.connect();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new ListAdapter(movieList,con);

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(con, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        llhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LifeSavers.this,Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        llnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LifeSavers.this,Notification.class);
                startActivity(i);
            }
        });
        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LifeSavers.this,Chat.class);
                startActivity(i);
            }
        });
        llfavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LifeSavers.this,Favourite.class);
                startActivity(i);
            }
        });
        llacounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LifeSavers.this,MyAccount.class);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
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

            new Type().execute();
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

    public class Type extends AsyncTask<Void,Void,Void> {
        String json_response;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {

                json_response = url_dump.getLifeSaverList(user_id,lat,longt);
                Log.d("Read",json_response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                JSONArray array = new JSONArray(json_response);
                String code = array.getString(0);
                String message = array.getString(1);
                if (code.equals("HCPC2600")) {
                    JSONArray inner = array.getJSONArray(2);
                    for (int i = 0; i < inner.length(); i++) {
                        JSONObject object = inner.getJSONObject(i);
                        String distance = object.getString("distance");
                        String hcp_cust_id = object.getString("hcp_cust_id");
                        String hcp_cust_name = object.getString("hcp_cust_name");
                        String hcp_cust_mobile_no = object.getString("hcp_cust_mobile_no");
                        String hcp_cust_map_lat = object.getString("hcp_cust_map_lat");
                        String hcp_cust_map_long = object.getString("hcp_cust_map_long");
                        String hcp_cust_blood_group = object.getString("hcp_cust_blood_group");

                        DataObject movie = new DataObject(distance, hcp_cust_id, hcp_cust_name, hcp_cust_mobile_no, hcp_cust_map_lat, hcp_cust_map_long, hcp_cust_blood_group);
                        movieList.add(movie);
                    }

                    mAdapter.notifyDataSetChanged();
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
