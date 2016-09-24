package com.sriyaan.qureco;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sriyaan.adapter.RecyclerAdapterSearch;
import com.sriyaan.modal.ListData;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchListPage1 extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    ListView listView;
    String json_response;
    JSONArray object;
    ArrayList results;
    MyCustomAdapter dataAdapter = null;
    private RecyclerView.LayoutManager mLayoutManager1;
    //List of type books this list will store type Book which is our data model
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
    public static String value;
    // Loading Font Face
    //
    Typeface tf;
    public static ArrayList<String> array;
    public static ArrayList<String> arrayID;
    SharedPreferences prefs;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list_page);
        init();

        prefs = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);

        Toast.makeText(SearchListPage1.this, "Coming ", Toast.LENGTH_SHORT).show();
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

        mLayoutManager1 = new GridLayoutManager(con, 1);
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
                Intent i = new Intent(SearchListPage1.this,Filters.class);
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
                if(array.size()<0)
                {

                }
                else{
                    for(int i=0;i<array.size();i++)
                    {
                        Log.d("Messages","Item "+ arrayID.get(i));
                    }
                }
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
        con = SearchListPage1.this;
        toolbar             = (Toolbar)             findViewById(R.id.toolbar);
        filter              = (LinearLayout)        findViewById(R.id.llfilter);
        loyalty             = (Switch)              findViewById(R.id.loyalty);
        llmap               = (LinearLayout)        findViewById(R.id.llmap);
        llcompare           = (LinearLayout)        findViewById(R.id.llcompare);
        gendergroup         = (RadioGroup)          findViewById(R.id.gendergroup);
        male                = (RadioButton)         findViewById(R.id.male);
        female              = (RadioButton)         findViewById(R.id.female);
        listView            = (ListView)            findViewById(R.id.listview);
        swipeRefreshLayout  = (SwipeRefreshLayout)  findViewById(R.id.swiperefreshlayout);
        llclearfilter       = (LinearLayout)        findViewById(R.id.llclearfilter);

        clearfilter         = (TextView)            findViewById(R.id.clearfilter);
        sort                = (TextView)            findViewById(R.id.sort);
        filters             = (TextView)            findViewById(R.id.filters);
        tvCompare           = (TextView)            findViewById(R.id.tvCompare);
        tvMapView           = (TextView)            findViewById(R.id.tvMapView);
    }

    public void setFont()
    {
        clearfilter         .setTypeface(tf);
        sort                .setTypeface(tf);
        filters             .setTypeface(tf);
        tvCompare           .setTypeface(tf);
        tvMapView           .setTypeface(tf);
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

    public class Type extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                results = new ArrayList<ListData>();
                /*json_response = url_dump.getSearchCategory(user_id,value,sort_by,open_hours,fees,open,str_loyalty,
                service_type,open_days,strGender);*/
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
                    String city = object1.getString("city");
                    String state = object1.getString("state");
                    String geo_lat = object1.getString("geo_lat");
                    String geo_long = object1.getString("geo_longi");
                    String photo_path = object1.getString("photo_path");
                    String charges = object1.getString("charges");
                    String final_rating = object1.getString("final_rating");
                    String no_of_followers = object1.getString("no_of_followers");
                    String no_of_likes = object1.getString("no_of_likes");
                    String location_contacts = "";
                    /*ListData data = new ListData(distance,hs_oid,hl_oid,hcp_user_oid,hcp_cat_oid,
                            service_name,location_name,city,state,geo_lat,
                            geo_long,photo_path,charges,final_rating,
                            no_of_followers,no_of_likes,location_contacts,false);
                    results.add(i, data);*/
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
            dataAdapter = new MyCustomAdapter(SearchListPage1.this,
                    R.layout.list_item, results);
            listView.setAdapter(dataAdapter);
        }
    }
    public void initSorting()
    {
        // Create custom dialog object
        final Dialog dialog = new Dialog(SearchListPage1.this);
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
    private class MyCustomAdapter extends ArrayAdapter<ListData> {

        private ArrayList<ListData> countryList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<ListData> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<ListData>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {
            ImageView imageView;
            LinearLayout llcall,lldirection,body;
            TextView name;
            CheckBox chkcompare;
            TextView followers;
            TextView address;
            TextView cash;
            TextView likes;
            TextView distance;
            TextView call;
            TextView direction;
            TextView offers;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_item, null);

                holder = new ViewHolder();
                tf = Typeface.createFromAsset(getAssets(), fontPath);

                holder.imageView   = (ImageView)    convertView.findViewById(R.id.list_image);
                holder.llcall      = (LinearLayout) convertView.findViewById(R.id.llcall);
                holder.lldirection = (LinearLayout) convertView.findViewById(R.id.lldirection);
                holder.body        = (LinearLayout) convertView.findViewById(R.id.body);

                holder.name        = (TextView)    convertView.findViewById(R.id.name);
                holder.followers   = (TextView)    convertView.findViewById(R.id.followers);
                holder.address     = (TextView)    convertView.findViewById(R.id.address);
                holder.cash        = (TextView)    convertView.findViewById(R.id.cash);
                holder.likes       = (TextView)    convertView.findViewById(R.id.likes);
                holder.distance    = (TextView)    convertView.findViewById(R.id.distance);
                holder.call        = (TextView)    convertView.findViewById(R.id.call);
                holder.direction   = (TextView)    convertView.findViewById(R.id.direction);
                holder.offers      = (TextView)    convertView.findViewById(R.id.offers);

                holder.chkcompare  = (CheckBox)    convertView.findViewById(R.id.chkcompare);
                convertView.setTag(holder);

                holder.chkcompare.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        ListData country = (ListData) cb.getTag();
                        /*Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();*/
                        country.setIsChecked(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            ListData data = countryList.get(position);

            holder.name.setText(""+ data.getServiceName());
            holder.followers.setText(""+ data.getNoFollowers() + " Followers");
            holder.address.setText(""+ data.getLocationName());
            holder.cash.setText(""+ data.getCharges());
            holder.likes.setText(""+ data.getLikes() +" Likes");
            String dist = String.format("%.2f", Double.valueOf(data.getDistance()));
            holder.distance.setText(""+ dist + " Km");

            if(!data.getPhotoPath().equals(""))
            {
                Picasso.with(SearchListPage1.this).load(data.getPhotoPath()).placeholder(R.drawable.hosp).into(holder.imageView);
            }
            holder.name        .setTypeface(tf);
            holder.followers   .setTypeface(tf);
            holder.address     .setTypeface(tf);
            holder.cash        .setTypeface(tf);
            holder.likes       .setTypeface(tf);
            holder.distance    .setTypeface(tf);
            holder.call        .setTypeface(tf);
            holder.direction   .setTypeface(tf);
            holder.offers      .setTypeface(tf);

            holder.chkcompare.setChecked(data.getIsChecked());
            holder.name.setTag(data);

            return convertView;

        }

    }
}
