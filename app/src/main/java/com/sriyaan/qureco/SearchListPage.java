package com.sriyaan.qureco;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.sriyaan.modal.ListData;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchListPage extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    String json_response;
    JSONArray object;
    ArrayList results;
    private RecyclerView.LayoutManager mLayoutManager1;
    //List of type books this list will store type Book which is our data model
    private List<ListData> data;

    SwipeRefreshLayout swipeRefreshLayout;
    Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list_page);

        init();
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
        setTitle("Search Page");

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager1 = new GridLayoutManager(con, 1);
        mRecyclerView.setLayoutManager(mLayoutManager1);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        new Type().execute();
                                    }
                                }
        );
    }
    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }

    public void init()
    {
        con = SearchListPage.this;
    }

    @Override
    public void onRefresh() {
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
                results = new ArrayList<ListData>();
                /*
                json_response = url_dump.getSearchCategory("10","1");
                object = new JSONArray(json_response);
                for (int i = 0; i < object.length(); i++) {
                    JSONObject jsonObject = object.getJSONObject(i);
                    String RegisterID = jsonObject.getString("RegisterID");
                    String name = jsonObject.getString("Name");
                    String address = jsonObject.getString("Address");
                    String ratings = jsonObject.getString("ratings");
                    String specialisation = jsonObject.getString("Specialisation");
                    String completeaddress = jsonObject.getString("completeAddress");
                    String workinghours = jsonObject.getString("workinghours");
                    String phone = jsonObject.getString("ContactNo");
                    String distance = jsonObject.getString("distance");
                    String offers = jsonObject.getString("offers");
                    String about = jsonObject.getString("About");
                    String website = jsonObject.getString("Website");
                    String email = jsonObject.getString("Email");

                    ListData data = new ListData();
                    results.add(i, data);
                }*/
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
        }
    }
}
