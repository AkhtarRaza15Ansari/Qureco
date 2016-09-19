package com.sriyaan.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
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
import com.sriyaan.modal.DetailsData;
import com.sriyaan.modal.ListData;
import com.sriyaan.qureco.DealsOffers;
import com.sriyaan.qureco.DetailsPage;
import com.sriyaan.qureco.R;
import com.sriyaan.qureco.SearchListPage;

import java.util.ArrayList;

/**
 * Created by Akhtar on 25-08-2016.
 */
public class RecyclerAdapterDeals extends RecyclerView
        .Adapter<RecyclerAdapterDeals
        .DataObjectHolder> {
    public static String LOG_TAG = "MyRecyclerViewAdapter";
    static public ArrayList<DetailsData> mDataset;
    static Context context;
    public static MyClickListener myClickListener;
    Typeface tf;
    String fontPath = "fonts/Montserrat-Regular.ttf";

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        ImageView imageView;
        LinearLayout body;
        TextView name;
        TextView validity;
        TextView description;
        TextView cash;
        TextView likes;
        TextView active;
        TextView offers;

        public DataObjectHolder(View itemView) {
            super(itemView);
            tf = Typeface.createFromAsset(context.getAssets(), fontPath);

            imageView = (ImageView) itemView.findViewById(R.id.list_image);
            body = (LinearLayout) itemView.findViewById(R.id.body);

            name = (TextView) itemView.findViewById(R.id.name);
            validity = (TextView) itemView.findViewById(R.id.validity);
            description = (TextView) itemView.findViewById(R.id.description);
            cash = (TextView) itemView.findViewById(R.id.cash);
            likes = (TextView) itemView.findViewById(R.id.likes);
            active = (TextView) itemView.findViewById(R.id.active);
            offers = (TextView) itemView.findViewById(R.id.offers);
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

    public RecyclerAdapterDeals(ArrayList<DetailsData> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.deals_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.name.setText("" + mDataset.get(position).getOfferCaption());
        holder.validity.setText("Expires on: " + mDataset.get(position).getToDate());
        holder.description.setText("" + mDataset.get(position).getLocationName());
        holder.cash.setText("" + mDataset.get(position).getOfferFlat());
        holder.likes.setText("" + mDataset.get(position).getLikes() + " Likes");

        if (!mDataset.get(position).getDealImage().equals("")) {
            Picasso.with(context).load(mDataset.get(position).getDealImage()).placeholder(R.drawable.hosp).into(holder.imageView);
        }
        holder.name.setTypeface(tf);
        holder.validity.setTypeface(tf);
        holder.description.setTypeface(tf);
        holder.cash.setTypeface(tf);
        holder.likes.setTypeface(tf);
        holder.offers.setTypeface(tf);
        holder.active.setTypeface(tf);

        holder.body.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Toast.makeText(context, "Long item clicked"+position, Toast.LENGTH_SHORT).show();
                if (SearchListPage.array.size() <= 1) {
                    SearchListPage.array.add("" + position);
                    SearchListPage.arrayID.add("" + mDataset.get(position).getHcpUserOid());
                    holder.body.setBackgroundColor(context.getResources().getColor(R.color.backgroundColor));
                } else {
                    Toast.makeText(context, "Cannot compare more than 2 items", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        holder.body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

    public void addItem(DetailsData dataObj, int index) {
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

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
