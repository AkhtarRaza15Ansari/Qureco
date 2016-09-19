package com.sriyaan.qureco;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sriyaan.adapter.DividerItemDecoration;
import com.sriyaan.adapter.ListAdapter;
import com.sriyaan.adapter.RecyclerTouchListener;
import com.sriyaan.modal.DataObject;
import com.sriyaan.util.url_dump;

import java.util.ArrayList;
import java.util.List;

public class LifeSavers extends AppCompatActivity {
    String fontPath = "fonts/Montserrat-Regular.ttf";
    // Loading Font Face
    Typeface tf;


    Context con;
    private List<DataObject> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_savers);
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

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new ListAdapter(movieList,con);

        recyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(con, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                DataObject movie = movieList.get(position);
                Toast.makeText(getApplicationContext(), movie.getName() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareMovieData();
        
    }
    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }
    private void prepareMovieData() {

        DataObject movie = new DataObject("A +ve","Jupiter Hospital");
            movieList.add(movie);

            movie = new DataObject("AB +ve", "Criticare Hospital");
            movieList.add(movie);

            movie = new DataObject("O-ve", "HealthCare Hospital");
            movieList.add(movie);

            movie = new DataObject("AB +ve", "Vedant Hospital");
            movieList.add(movie);

            movie = new DataObject("A +ve","Jupiter Hospital");
            movieList.add(movie);

            movie = new DataObject("AB +ve", "Criticare Hospital");
            movieList.add(movie);

            movie = new DataObject("O-ve", "HealthCare Hospital");
            movieList.add(movie);

            movie = new DataObject("AB +ve", "Vedant Hospital");
            movieList.add(movie);

            movie = new DataObject("O-ve", "HealthCare Hospital");
            movieList.add(movie);

            movie = new DataObject("AB +ve", "Vedant Hospital");
            movieList.add(movie);

            movie = new DataObject("A +ve","Jupiter Hospital");
            movieList.add(movie);

            movie = new DataObject("AB +ve", "Criticare Hospital");
            movieList.add(movie);
        

        mAdapter.notifyDataSetChanged();
    }
}
