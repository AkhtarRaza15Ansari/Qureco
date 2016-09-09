package com.sriyaan.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sriyaan.modal.KPIData;
import com.sriyaan.modal.ListData;
import com.sriyaan.qureco.R;
import com.sriyaan.qureco.SearchListPage;

import java.util.ArrayList;

/**
 * Created by Akhtar on 25-08-2016.
 */
public class RecyclerAdapterRatings extends RecyclerView
        .Adapter<RecyclerAdapterRatings
        .DataObjectHolder> {
    public static String LOG_TAG = "MyRecyclerViewAdapter";
    static public ArrayList<KPIData> mDataset;
    static Context context;
    public static MyClickListener myClickListener;
    Typeface tf;
    String fontPath = "fonts/Montserrat-Regular.ttf";

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        ImageView star1,star2,star3,star4,star5;
        TextView text;

        public DataObjectHolder(View itemView) {
            super(itemView);
            tf = Typeface.createFromAsset(context.getAssets(), fontPath);

            star1       = (ImageView) itemView.findViewById(R.id.star1);
            star2       = (ImageView) itemView.findViewById(R.id.star2);
            star3       = (ImageView) itemView.findViewById(R.id.star3);
            star4       = (ImageView) itemView.findViewById(R.id.star4);
            star5       = (ImageView) itemView.findViewById(R.id.star5);

            text        = (TextView)    itemView.findViewById(R.id.text);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public RecyclerAdapterRatings(ArrayList<KPIData> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ratings_layout, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.text.setText(""+ mDataset.get(position).getHCPRatingTitle());
        holder.text        .setTypeface(tf);

        holder.star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
            }
        });
        holder.star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
            }
        });
        holder.star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
                holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
            }
        });
        holder.star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
            }
        });
        holder.star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.star1.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.star2.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.star3.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.star4.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
                holder.star5.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
