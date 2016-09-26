package com.sriyaan.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sriyaan.modal.ReviewData;
import com.sriyaan.modal.ListData;
import com.sriyaan.modal.NotificationsData;
import com.sriyaan.modal.ReviewData;
import com.sriyaan.qureco.DetailsPage;
import com.sriyaan.qureco.R;
import com.sriyaan.qureco.Search;
import com.sriyaan.qureco.SearchListPage;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewTab extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

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
    static ArrayList<String> array,arrayID;
    SharedPreferences prefs;
    String user_id,hcp_id="";

    public ReviewTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_review_tab, container, false);
        init(rootview);

        prefs = getActivity().getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);

        try {
            hcp_id = prefs.getString("hcp_id", "");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        user_id = prefs.getString("cust_id","");

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
        return rootview;
    }
    public class RecyclerAdapterNotification extends RecyclerView
            .Adapter<RecyclerAdapterNotification
            .DataObjectHolder> {
        public String LOG_TAG = "MyRecyclerViewAdapter";
        public ArrayList<ReviewData> mDataset;
        Context context;
        Typeface tf;
        String fontPath = "fonts/Montserrat-Regular.ttf";

        public class DataObjectHolder extends RecyclerView.ViewHolder
                implements View
                .OnClickListener {
            LinearLayout body,lldirection,llcall;
            ImageView one,two,three,four,five;
            TextView name;
            TextView address;
            TextView follow;

            public DataObjectHolder(View itemView) {
                super(itemView);
                tf = Typeface.createFromAsset(context.getAssets(), fontPath);

                one         = (ImageView) itemView.findViewById(R.id.one);
                two         = (ImageView) itemView.findViewById(R.id.two);
                three       = (ImageView) itemView.findViewById(R.id.three);
                four        = (ImageView) itemView.findViewById(R.id.four);
                five        = (ImageView) itemView.findViewById(R.id.five);

                body = (LinearLayout) itemView.findViewById(R.id.body);
                lldirection = (LinearLayout) itemView.findViewById(R.id.lldirection);
                llcall = (LinearLayout) itemView.findViewById(R.id.llcall);

                name = (TextView) itemView.findViewById(R.id.name);
                address = (TextView) itemView.findViewById(R.id.address);


                Log.i(LOG_TAG, "Adding Listener");
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

            }
        }



        public RecyclerAdapterNotification(ArrayList<ReviewData> myDataset, Context context) {
            mDataset = myDataset;
            this.context = context;
        }

        @Override
        public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.review_item, parent, false);

            DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
            return dataObjectHolder;
        }

        @Override
        public void onBindViewHolder(final DataObjectHolder holder, final int position) {
            holder.name.setText("" + mDataset.get(position).getServiceName());
            holder.address.setText(mDataset.get(position).getLocationName());

            holder.body.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Coming","here");
                    Intent i =  new Intent(context,DetailsPage.class);
                    i.putExtra("value", SearchListPage.value);
                    i.putExtra("hcp_id",mDataset.get(position).getHsOid());
                    Log.d("hcp_id",mDataset.get(position).getHsOid());
                    context.startActivity(i);
                }
            });
            holder.lldirection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String geo_lat = mDataset.get(position).getGeoLat();
                    String geo_longi = mDataset.get(position).getGeoLong();
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?daddr=" + geo_lat + "," + geo_longi));
                    context.startActivity(intent);
                }
            });
            holder.llcall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String contacts = mDataset.get(position).getMobileNo();

                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+contacts));
                    context.startActivity(intent);
                }
            });
            String str_rating = mDataset.get(position).getRating();
            try {
                double value = Double.parseDouble(str_rating);
                int int_value = (int) Math.ceil(value);
                str_rating = String.valueOf(int_value);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            if(str_rating.equals("1"))
            {
                holder.one.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.two.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                holder.three.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                holder.four.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                holder.five.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
            }
            else if(str_rating.equals("2"))
            {
                holder.one.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.two.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.three.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                holder.four.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                holder.five.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
            }
            else if(str_rating.equals("3"))
            {
                holder.one.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.two.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.three.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.four.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                holder.five.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
            }
            else if(str_rating.equals("4"))
            {
                holder.one.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.two.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.three.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.four.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.five.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
            }
            else if(str_rating.equals("5"))
            {
                holder.one.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.two.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.three.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.four.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.five.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            }
        }

        @Override
        public int getItemCount() {
            return mDataset.size();
        }

    }

    public void init(View v)
    {
        con = getActivity();
        mRecyclerView       = (RecyclerView)        v.findViewById(R.id.my_recycler_view1);
        swipeRefreshLayout  = (SwipeRefreshLayout)  v.findViewById(R.id.swiperefreshlayout);
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
                results = new ArrayList<ReviewData>();
                json_response = url_dump.getReviewList(user_id);
                Log.d("json_response",json_response);
                JSONArray array = new JSONArray(json_response);
                String code = array.getString(0);
                String message = array.getString(1);
                object = array.getJSONArray(2);
                for (int i = 0; i < object.length(); i++) {
                    JSONObject jsonObject = object.getJSONObject(i);

                    String hs_oid = jsonObject.getString("hs_oid");
                    String rating = jsonObject.getString("rating");
                    String rating_review = jsonObject.getString("rating_review");
                    String service_name = jsonObject.getString("service_name");
                    String location_name = jsonObject.getString("location_name");
                    String city = jsonObject.getString("city");
                    String state = jsonObject.getString("state");
                    String geo_lat = jsonObject.getString("geo_lat");
                    String geo_longi = jsonObject.getString("geo_longi");
                    String location_contacts = jsonObject.getString("location_contacts");

                    ReviewData data = new ReviewData(hs_oid,rating,rating_review,service_name,location_name,city,state,geo_lat,geo_longi,location_contacts);
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
            mAdapter = new RecyclerAdapterNotification(results,con);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }
}
