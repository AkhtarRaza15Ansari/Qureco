package com.sriyaan.qureco;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sriyaan.adapter.RecyclerAdapterDeals;
import com.sriyaan.adapter.RecyclerAdapterSearch;
import com.sriyaan.modal.DetailsData;
import com.sriyaan.modal.ListData;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DealsOffers extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    String json_response;
    JSONArray object;
    ArrayList results;
    private RecyclerView.LayoutManager mLayoutManager1;
    //List of type books this list will store type Book which is our data model
    private List<ListData> data;
    LinearLayout filter;
    SwipeRefreshLayout swipeRefreshLayout;
    Context con;
    String sort_by="";
    TextView sort,filters;
    Toolbar toolbar;
    String fontPath = "fonts/Montserrat-Regular.ttf";
    public static String value;
    // Loading Font Face
    //
    Typeface tf;
    static ArrayList<String> array,arrayID;
    SharedPreferences prefs;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals_offers);
        init();
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
        mTitle.setText("Deals and Offers");
        setFont();
        array       = new ArrayList<>();
        arrayID     = new ArrayList<>();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager1 = new GridLayoutManager(con, 1);
        mRecyclerView.setLayoutManager(mLayoutManager1);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable(){
                                    @Override
                                    public void run(){
                                        runThis();
                                    }
                                }
        );

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DealsOffers.this,Filters.class);
                startActivity(i);
            }
        });
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSorting();
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }
    public void init()
    {
        con = DealsOffers.this;
        toolbar             = (Toolbar)             findViewById(R.id.toolbar);
        filter              = (LinearLayout)        findViewById(R.id.llfilter);
        mRecyclerView       = (RecyclerView)        findViewById(R.id.my_recycler_view1);
        swipeRefreshLayout  = (SwipeRefreshLayout)  findViewById(R.id.swiperefreshlayout);
        sort                = (TextView)            findViewById(R.id.sort);
        filters             = (TextView)            findViewById(R.id.filters);
    }

    public void setFont()
    {
        sort                .setTypeface(tf);
        filters             .setTypeface(tf);
    }

    @Override
    public void onRefresh() {
        runThis();
    }

    public void runThis()
    {
        Log.d("Coming","Here");
        swipeRefreshLayout.setRefreshing(true);
        new Type().execute();
    }

    public class Type extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                results = new ArrayList<DetailsData>();
                json_response = url_dump.getSearchDeals(user_id,value,sort_by,"1");
                JSONArray array = new JSONArray(json_response);

                object = array.getJSONArray(2);
                for (int i = 0; i < object.length(); i++) {
                    JSONObject jsonObject = object.getJSONObject(i);
                    JSONObject object1 = jsonObject.getJSONObject("deals");

                    String hdo_oid = object1.getString("hdo_oid");
                    String hl_oid = object1.getString("hl_oid");
                    String hcp_user_oid = object1.getString("hcp_user_oid");
                    String hcp_cat_oid = object1.getString("hcp_cat_oid");

                    String offer_caption = object1.getString("offer_caption");
                    String deal_image = object1.getString("deal_image");
                    String from_date = object1.getString("from_date");
                    String to_date = object1.getString("to_date");

                    String offer_flat = object1.getString("offer_flat");
                    String offer_discount = object1.getString("offer_discount");
                    String description = object1.getString("description");
                    String terms_condition = object1.getString("terms_condition");
                    String location_name = object1.getString("location_name");
                    String city = object1.getString("city");
                    String state = object1.getString("state");
                    String geo_lat = object1.getString("geo_lat");
                    String geo_longi = object1.getString("geo_longi");
                    String location_contacts = object1.getString("location_contacts");
                    String final_rating = object1.getString("final_rating");
                    String no_of_followers = object1.getString("no_of_followers");
                    String no_of_likes = object1.getString("no_of_likes");

                    DetailsData data = new DetailsData(hdo_oid,hl_oid,hcp_user_oid,hcp_cat_oid,
                            offer_caption,deal_image,from_date,to_date,offer_flat,offer_discount,
                            description,terms_condition,location_name,city,state,geo_lat,
                            geo_longi,location_contacts,final_rating,
                            no_of_followers,no_of_likes);
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
            mAdapter = new RecyclerAdapterDeals(results,con);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }
    public void initSorting()
    {
        // Create custom dialog object
        final Dialog dialog = new Dialog(DealsOffers.this);
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
}
