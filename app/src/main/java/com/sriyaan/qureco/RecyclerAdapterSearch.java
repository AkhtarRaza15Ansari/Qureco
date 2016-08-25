package com.sriyaan.qureco;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sriyaan.modal.ListData;

import java.util.ArrayList;

/**
 * Created by Akhtar on 25-08-2016.
 */
public class RecyclerAdapterSearch extends RecyclerView
        .Adapter<RecyclerAdapterSearch
        .DataObjectHolder> {
    public static String LOG_TAG = "MyRecyclerViewAdapter";
    static public ArrayList<ListData> mDataset;
    static Context context;
    public static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        LinearLayout lldistance;
        ImageView imageView;
        ImageView imageCall;
        ImageView imageShare;
        TextView name;
        TextView address;
        TextView specialisation;
        TextView completeAddress;
        TextView workinghours;
        TextView distance;
        TextView offers;
        TextView text_ratings;

        public DataObjectHolder(View itemView) {
            super(itemView);

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

    public RecyclerAdapterSearch(ArrayList<ListData> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {

    }
    private void callShareIntent(String text){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, text);
        shareIntent.setType("text/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // Launch sharing dialog for image
        context.startActivity(Intent.createChooser(shareIntent, "Share Via"));
    }
    public void addItem(ListData dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
