package com.sriyaan.qureco;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ReviewFinal extends AppCompatActivity {
    LinearLayout ratingslayout;
    EditText etComments;
    Button btnPost;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_final);
        init();

    }
    public void init()
    {
        context         = ReviewFinal.this;
        ratingslayout   = (LinearLayout)    findViewById(R.id.ratingslayout);
        etComments      = (EditText)        findViewById(R.id.etComments);
        btnPost         = (Button)          findViewById(R.id.postreview);
    }
}
