package com.sriyaan.qureco;

import android.app.Dialog;
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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sriyaan.adapter.RecyclerAdapterSearch;
import com.sriyaan.modal.ListData;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchListPage extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    String json_response;
    JSONArray object;
    String lat = "", longt = "";
    ArrayList results;
    LatLng latLng;
    private RecyclerView.LayoutManager mLayoutManager1;
    //List of type books this list will store type Book which is our data model
    private List<ListData> data;
    LinearLayout filter;
    SwipeRefreshLayout swipeRefreshLayout;
    Context con;
    String sort_by="",str_loyalty="",strGender="";
    LinearLayout llcompare,llmap;
    Switch loyalty;
    TextView sort,filters,tvCompare,tvMapView,clearfilter;
    RadioGroup gendergroup;
    RadioButton male,female;
    Toolbar toolbar;
    public static boolean onRefresh = false;
    public static String open_hours="",fees="",open="",service_type="",open_days="";
    String fontPath = "fonts/Montserrat-Regular.ttf";
    LinearLayout llclearfilter;
    public static String value = "";
    // Loading Font Face
    //
    Typeface tf;
    public static ArrayList<String> array;
    public static ArrayList<String> arrayID;
    SharedPreferences prefs;
    String user_id;
    GoogleApiClient mGoogleApiClient;

    TextView tvHome,tvNotification,tvChat,tvFavourites,tvAccounts;
    LinearLayout llhome,llnotification,llchat,llfavorites,llacounts;

    public static ArrayList<String> arr_lat;
    public static ArrayList<String> arr_long;
    public static ArrayList<String> arr_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list_page);
        init();
        buildGoogleApiClient();

        mGoogleApiClient.connect();
        prefs = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);


        user_id = prefs.getString("cust_id","");
        value = getIntent().getStringExtra("value");
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
        mTitle.setText("Search Page");
        setFont();
        array       = new ArrayList<>();
        arrayID     = new ArrayList<>();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager1 = new GridLayoutManager(con, 1);
        mRecyclerView.setLayoutManager(mLayoutManager1);
        swipeRefreshLayout.setOnRefreshListener(this);


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchListPage.this,Filters.class);
                startActivity(i);
            }
        });
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSorting();
            }
        });
        //attach a listener to check for changes in state
        loyalty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    str_loyalty = "1";
                    runThis();
                }else{
                    str_loyalty = "0";
                    runThis();
                }
            }
        });

        gendergroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                if(checkedId == male.getId())
                {
                    strGender = "1";
                    runThis();
                }
                else if(checkedId == female.getId())
                {
                    strGender = "2";
                    runThis();
                }
            }
        });
        llclearfilter.setVisibility(View.GONE);
        llclearfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFilter();
                runThis();
            }
        });
        llcompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(array.size()<1)
                {
                    Toast.makeText(SearchListPage.this, "Please select two HCP to compaare", Toast.LENGTH_SHORT).show();
                }
                else{
                    for(int i=0;i<array.size();i++)
                    {
                        Log.d("Messages","Item "+ arrayID.get(i));
                    }
                    String hcp_id1 = arrayID.get(0);
                    String hcp_id2 = arrayID.get(1);
                    Intent i = new Intent(SearchListPage.this,CompareScreen.class);
                    i.putExtra("hcp_id1",hcp_id1);
                    i.putExtra("hcp_id2",hcp_id2);
                    i.putExtra("value",value);
                    startActivity(i);

                }
            }
        });

        llhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchListPage.this,Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        llnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchListPage.this,Notification.class);
                startActivity(i);
            }
        });
        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchListPage.this,Chat.class);
                startActivity(i);
            }
        });
        llfavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchListPage.this,Favourite.class);
                startActivity(i);
            }
        });
        llacounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SearchListPage.this,MyAccount.class);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onPause(){
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }

    public void init()
    {
        con = SearchListPage.this;

        arr_lat             = new ArrayList<>();
        arr_long            = new ArrayList<>();
        arr_name            = new ArrayList<>();

        toolbar             = (Toolbar)             findViewById(R.id.toolbar);
        filter              = (LinearLayout)        findViewById(R.id.llfilter);
        loyalty             = (Switch)              findViewById(R.id.loyalty);
        llmap               = (LinearLayout)        findViewById(R.id.llmap);
        llcompare           = (LinearLayout)        findViewById(R.id.llcompare);
        gendergroup         = (RadioGroup)          findViewById(R.id.gendergroup);
        male                = (RadioButton)         findViewById(R.id.male);
        female              = (RadioButton)         findViewById(R.id.female);
        mRecyclerView       = (RecyclerView)        findViewById(R.id.my_recycler_view1);
        swipeRefreshLayout  = (SwipeRefreshLayout)  findViewById(R.id.swiperefreshlayout);
        llclearfilter       = (LinearLayout)        findViewById(R.id.llclearfilter);

        clearfilter         = (TextView)            findViewById(R.id.clearfilter);
        sort                = (TextView)            findViewById(R.id.sort);
        filters             = (TextView)            findViewById(R.id.filters);
        tvCompare           = (TextView)            findViewById(R.id.tvCompare);
        tvMapView           = (TextView)            findViewById(R.id.tvMapView);

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
        clearfilter         .setTypeface(tf);
        sort                .setTypeface(tf);
        filters             .setTypeface(tf);
        tvCompare           .setTypeface(tf);
        tvMapView           .setTypeface(tf);

        tvHome          .setTypeface(tf);
        tvNotification  .setTypeface(tf);
        tvChat          .setTypeface(tf);
        tvFavourites    .setTypeface(tf);
        tvAccounts      .setTypeface(tf);
    }

    @Override
    public void onRefresh() {
        runThis();
    }

    public void runThis()
    {
        Log.d("Coming","Here");
        swipeRefreshLayout.setRefreshing(true);
        if(loyalty.isChecked()){
            str_loyalty = "1";
        }
        else {
            str_loyalty = "0";
        }
        new Type().execute();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public class Type extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            arr_long.clear();
            arr_lat.clear();
            arr_name.clear();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                results = new ArrayList<ListData>();
                json_response = url_dump.getSearchCategory(user_id,value,sort_by,open_hours,fees,open,str_loyalty,
                service_type,open_days,strGender,lat,longt);
                JSONArray array = new JSONArray(json_response);

                object = array.getJSONArray(2);
                for (int i = 0; i < object.length(); i++) {
                    JSONObject jsonObject = object.getJSONObject(i);
                    JSONObject object1 = jsonObject.getJSONObject("services");

                    String distance = object1.getString("distance");
                    String hs_oid = object1.getString("hs_oid");
                    String hl_oid = object1.getString("hl_oid");
                    String hcp_user_oid = object1.getString("hcp_user_oid");
                    String hcp_cat_oid = object1.getString("hcp_cat_oid");
                    String service_name = object1.getString("service_name");
                    String location_name = object1.getString("location_name");
                    String location_contacts = object1.getString("location_contacts");
                    String city = object1.getString("city");
                    String state = object1.getString("state");
                    String geo_lat = object1.getString("geo_lat");
                    String geo_long = object1.getString("geo_longi");
                    String photo_path = object1.getString("photo_path");
                    String charges = object1.getString("charges");
                    String final_rating = object1.getString("final_rating");
                    String no_of_followers = object1.getString("no_of_followers");
                    String no_of_likes = object1.getString("no_of_likes");
                    String offer_caption = object1.getString("offer_caption");

                    arr_lat.add(geo_lat);
                    arr_long.add(geo_long);
                    arr_name.add(service_name);

                    ListData data = new ListData(distance,hs_oid,hl_oid,hcp_user_oid,hcp_cat_oid,
                            service_name,location_name,city,state,geo_lat,
                            geo_long,photo_path,charges,final_rating,
                            no_of_followers,no_of_likes,location_contacts,offer_caption,false);
                    results.add(i, data);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            swipeRefreshLayout.setRefreshing(false);
            mAdapter = new RecyclerAdapterSearch(results,con);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            llmap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(SearchListPage.this,MapsViewPage.class);
                    startActivity(i);
                }
            });
        }
    }
    public void initSorting()
    {
        // Create custom dialog object
        final Dialog dialog = new Dialog(SearchListPage.this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.sorting_dialog);
        // Set dialog title
        dialog.setTitle("Sort By");

        TextView sortprice1 = (TextView) dialog.findViewById(R.id.sortprice1);
        TextView sortprice2 = (TextView) dialog.findViewById(R.id.sortprice2);
        TextView sortdistance1 = (TextView) dialog.findViewById(R.id.sortdistance1);
        TextView sortdistance2 = (TextView) dialog.findViewById(R.id.sortdistance2);
        TextView sortratings1 = (TextView) dialog.findViewById(R.id.sortratings1);
        TextView sortratings2 = (TextView) dialog.findViewById(R.id.sortratings2);

        sortprice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_by = "1";
                runThis();
                dialog.dismiss();
            }
        });
        sortprice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_by = "2";
                runThis();
                dialog.dismiss();
            }
        });
        sortdistance1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_by = "3";
                runThis();
                dialog.dismiss();
            }
        });
        sortdistance2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_by = "4";
                runThis();
                dialog.dismiss();
            }
        });
        sortratings1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_by = "5";
                runThis();
                dialog.dismiss();
            }
        });
        sortratings2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_by = "6";
                runThis();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public void onResume()
    {
        super.onResume();
        if(onRefresh)
        {
            llclearfilter.setVisibility(View.VISIBLE);
            runThis();
            onRefresh = false;
        }
    }

    public void clearFilter()
    {
        open_hours="";
        fees="";
        open="";
        service_type="";
        open_days="";
        llclearfilter.setVisibility(View.GONE);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        clearFilter();
    }
    @Override
    public void onStop()
    {
        super.onStop();
        clearFilter();
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
            swipeRefreshLayout.post(new Runnable(){
                                        @Override
                                        public void run(){
                                            runThis();
                                        }
                                    }
            );
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
