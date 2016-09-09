package com.sriyaan.qureco;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.sriyaan.adapter.RecyclerAdapterRatings;
import com.sriyaan.modal.KPIData;
import com.sriyaan.modal.ListData;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReviewFinal extends AppCompatActivity {
    LinearLayout ratingslayout;
    EditText etComments;
    Button btnPost;
    Context context;
    RecyclerView listratings;
    private RecyclerView.Adapter mAdapter;
    String json_response;
    JSONArray object;
    ArrayList results;
    private RecyclerView.LayoutManager mLayoutManager1;
    String fontPath = "fonts/Montserrat-Regular.ttf";
    // Loading Font Face
    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_final);
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

        mTitle.setText("Review Page");
        init();
        setFont();
        listratings.setHasFixedSize(true);
        mLayoutManager1 = new GridLayoutManager(context, 1);
        listratings.setLayoutManager(mLayoutManager1);
        new Type().execute();
    }

    public void init()
    {
        context         = ReviewFinal.this;
        ratingslayout   = (LinearLayout)    findViewById(R.id.ratingslayout);
        etComments      = (EditText)        findViewById(R.id.etComments);
        btnPost         = (Button)          findViewById(R.id.postreview);
        listratings     = (RecyclerView)    findViewById(R.id.list_ratings);
    }

    public void setFont()
    {
        etComments         .setTypeface(tf);
        btnPost            .setTypeface(tf);
    }

    public class Type extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                results = new ArrayList<KPIData>();
                json_response = url_dump.getKPIs("1","7");
                JSONArray array = new JSONArray(json_response);

                object = array.getJSONArray(2);
                for (int i = 0; i < object.length(); i++) {
                    JSONObject jsonObject = object.getJSONObject(i);
                    String hcp_rating_kpi_id = jsonObject.getString("hcp_rating_kpi_id");
                    String hcp_rating_kpi_title = jsonObject.getString("hcp_rating_kpi_title");
                    KPIData data = new KPIData(hcp_rating_kpi_id,hcp_rating_kpi_title);
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
            mAdapter = new RecyclerAdapterRatings(results,context);
            listratings.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }
}
