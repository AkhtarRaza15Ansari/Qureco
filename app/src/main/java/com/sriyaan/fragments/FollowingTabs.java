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

import com.sriyaan.modal.FollowData;
import com.sriyaan.modal.ListData;
import com.sriyaan.modal.FollowData;
import com.sriyaan.qureco.DetailsPage;
import com.sriyaan.qureco.R;
import com.sriyaan.qureco.SearchListPage;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingTabs extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
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

    public FollowingTabs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_following_tabs, container, false);
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
        public ArrayList<FollowData> mDataset;
        Context context;
        Typeface tf;
        String fontPath = "fonts/Montserrat-Regular.ttf";

        public class DataObjectHolder extends RecyclerView.ViewHolder
                implements View
                .OnClickListener {
            LinearLayout body,lldirection,llcall;
            TextView name;
            TextView address;
            TextView follow;

            public DataObjectHolder(View itemView) {
                super(itemView);
                tf = Typeface.createFromAsset(context.getAssets(), fontPath);
                body = (LinearLayout) itemView.findViewById(R.id.body);
                lldirection = (LinearLayout) itemView.findViewById(R.id.lldirection);
                llcall = (LinearLayout) itemView.findViewById(R.id.llcall);

                name = (TextView) itemView.findViewById(R.id.name);
                address = (TextView) itemView.findViewById(R.id.address);
                follow = (TextView) itemView.findViewById(R.id.follow);



                Log.i(LOG_TAG, "Adding Listener");
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

            }
        }



        public RecyclerAdapterNotification(ArrayList<FollowData> myDataset, Context context) {
            mDataset = myDataset;
            this.context = context;
        }

        @Override
        public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.following_item, parent, false);

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
        }

        private void callShareIntent(String text) {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            shareIntent.setType("text/*");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // Launch sharing dialog for image
            context.startActivity(Intent.createChooser(shareIntent, "Share Via"));
        }

        public void addItem(FollowData dataObj, int index) {
            mDataset.add(dataObj);
            notifyItemInserted(index);
        }

        public void deleteItem(int index) {
            mDataset.remove(index);
            notifyItemRemoved(index);
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
                results = new ArrayList<FollowData>();
                json_response = url_dump.getFollowingList(user_id);
                Log.d("json_response",json_response);
                JSONArray array = new JSONArray(json_response);
                String code = array.getString(0);
                String message = array.getString(1);
                object = array.getJSONArray(2);
                for (int i = 0; i < object.length(); i++) {
                    JSONObject jsonObject = object.getJSONObject(i);

                    String hs_oid = jsonObject.getString("hs_oid");
                    String hl_oid = jsonObject.getString("hl_oid");
                    String service_name = jsonObject.getString("service_name");
                    String location_name = jsonObject.getString("location_name");
                    String city = jsonObject.getString("city");
                    String state = jsonObject.getString("state");
                    String geo_lat = jsonObject.getString("geo_lat");
                    String geo_longi = jsonObject.getString("geo_longi");
                    String location_contacts = jsonObject.getString("location_contacts");

                    FollowData data = new FollowData(hs_oid,hl_oid,service_name,location_name,city,state,geo_lat,geo_longi,location_contacts);
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
