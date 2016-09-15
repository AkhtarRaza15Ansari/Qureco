package com.sriyaan.qureco;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
    public static ArrayList<String> review_id,review_ratings;
    private RecyclerView.LayoutManager mLayoutManager1;
    String fontPath = "fonts/Montserrat-Regular.ttf";
    // Loading Font Face
    Typeface tf;
    String user_id,hcp_id;
    String comments,ids,stars,promo;
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
        try {
            user_id = getIntent().getStringExtra("user_id");
            hcp_id = getIntent().getStringExtra("hcp_id");
            promo = getIntent().getStringExtra("promo");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        setTitle("");
        review_id = new ArrayList();
        review_ratings = new ArrayList();
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
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("RAtings",android.text.TextUtils.join(",", review_ratings));
                Log.d("ids",android.text.TextUtils.join(",", review_id));
                ids = android.text.TextUtils.join(",", review_id);
                stars = android.text.TextUtils.join(",", review_ratings);
                comments = etComments.getText().toString();
                new Review().execute();
            }
        });
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
                json_response = url_dump.getKPIs(user_id,hcp_id);
                JSONArray array = new JSONArray(json_response);

                object = array.getJSONArray(2);
                for (int i = 0; i < object.length(); i++) {
                    JSONObject jsonObject = object.getJSONObject(i);
                    String hcp_rating_kpi_id = jsonObject.getString("hcp_rating_kpi_id");
                    String hcp_rating_kpi_title = jsonObject.getString("hcp_rating_kpi_title");
                    review_id.add(hcp_rating_kpi_id+"");
                    review_ratings.add(""+1);
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
    public class Review extends AsyncTask<Void,Void,Void> {
        String str_Code,str_Message;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                json_response = url_dump.saveRatings(user_id,hcp_id,comments,ids,stars,promo);
                JSONArray array = new JSONArray(json_response);
                str_Code = array.get(0).toString();
                str_Message = array.get(1).toString();
                Log.d("Code",str_Code);
                Log.d("Mesg",str_Message);
                if(str_Code.equals("HCPC1400"))
                {
                    finish();
                }
                else{
                    Toast.makeText(ReviewFinal.this, ""+str_Message, Toast.LENGTH_SHORT).show();
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
