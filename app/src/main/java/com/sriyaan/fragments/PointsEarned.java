package com.sriyaan.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sriyaan.modal.ListData;
import com.sriyaan.modal.PointsData;
import com.sriyaan.modal.PointsData;
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
public class PointsEarned extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

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
    public PointsEarned() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_points_earned, container, false);
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
        public ArrayList<PointsData> mDataset;
        Context context;
        Typeface tf;
        String fontPath = "fonts/Montserrat-Regular.ttf";

        public class DataObjectHolder extends RecyclerView.ViewHolder
                implements View
                .OnClickListener {
            TextView name;
            TextView date;
            TextView amount;
            TextView transaction;

            public DataObjectHolder(View itemView) {
                super(itemView);
                tf = Typeface.createFromAsset(context.getAssets(), fontPath);

                name = (TextView) itemView.findViewById(R.id.name);
                date     = (TextView) itemView.findViewById(R.id.date);
                amount = (TextView) itemView.findViewById(R.id.amount);
                transaction = (TextView) itemView.findViewById(R.id.transaction);

                Log.i(LOG_TAG, "Adding Listener");
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

            }
        }



        public RecyclerAdapterNotification(ArrayList<PointsData> myDataset, Context context) {
            mDataset = myDataset;
            this.context = context;
        }

        @Override
        public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.points_item, parent, false);

            DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
            return dataObjectHolder;
        }

        @Override
        public void onBindViewHolder(final DataObjectHolder holder, final int position) {
            holder.name.setText("" + mDataset.get(position).getActionName());

            String trans_str = "Points Earned: " + mDataset.get(position).getPoints();
            String amount_str = "Transaction amount: " + mDataset.get(position).getAmount();

            holder.transaction.setText(getSpan(trans_str,15,trans_str.length()));
            holder.amount.setText(getSpan(amount_str,19,amount_str.length()));

            holder.date.setText("" + mDataset.get(position).getDate());
            
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
                results = new ArrayList<PointsData>();
                json_response = url_dump.getPointsEarned(user_id);
                Log.d("json_response",json_response);
                JSONArray array = new JSONArray(json_response);
                String code = array.getString(0);
                String message = array.getString(1);
                object = array.getJSONArray(2);
                for (int i = 0; i < object.length(); i++) {
                    JSONObject jsonObject = object.getJSONObject(i);

                    String points = jsonObject.getString("points");
                    String amount = jsonObject.getString("amount");
                    String action_name = jsonObject.getString("action_name");
                    String date = jsonObject.getString("date");

                    PointsData data = new PointsData(points,action_name,amount,date);
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

    public Spannable getSpan(String str, int start, int end)
    {
        Spannable wordtoSpan = new SpannableString(str);

        wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return wordtoSpan;
    }
}
