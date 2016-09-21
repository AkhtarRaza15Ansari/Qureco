package com.sriyaan.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sriyaan.modal.ListData;
import com.sriyaan.qureco.DetailsPage;
import com.sriyaan.qureco.R;
import com.sriyaan.qureco.SearchListPage;

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
    Typeface tf;
    String fontPath = "fonts/Montserrat-Regular.ttf";

    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        ImageView imageView;
        ImageView one,two,three,four,five;
        LinearLayout llcall,lldirection,body;
        TextView name;
        CheckBox chkcompare;
        TextView followers;
        TextView address;
        TextView cash;
        TextView likes;
        TextView distance;
        TextView call;
        TextView direction;
        TextView offers;

        public DataObjectHolder(View itemView) {
            super(itemView);


            tf = Typeface.createFromAsset(context.getAssets(), fontPath);

            one         = (ImageView) itemView.findViewById(R.id.one);
            two         = (ImageView) itemView.findViewById(R.id.two);
            three       = (ImageView) itemView.findViewById(R.id.three);
            four        = (ImageView) itemView.findViewById(R.id.four);
            five        = (ImageView) itemView.findViewById(R.id.five);

            imageView   = (ImageView) itemView.findViewById(R.id.list_image);

            llcall      = (LinearLayout) itemView.findViewById(R.id.llcall);
            lldirection = (LinearLayout) itemView.findViewById(R.id.lldirection);
            body        = (LinearLayout) itemView.findViewById(R.id.body);

            name        = (TextView)    itemView.findViewById(R.id.name);
            followers   = (TextView)    itemView.findViewById(R.id.followers);
            address     = (TextView)    itemView.findViewById(R.id.address);
            cash        = (TextView)    itemView.findViewById(R.id.cash);
            likes       = (TextView)    itemView.findViewById(R.id.likes);
            distance    = (TextView)    itemView.findViewById(R.id.distance);
            call        = (TextView)    itemView.findViewById(R.id.call);
            direction   = (TextView)    itemView.findViewById(R.id.direction);
            offers      = (TextView)    itemView.findViewById(R.id.offers);

            chkcompare  = (CheckBox)    itemView.findViewById(R.id.chkcompare);
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
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {
        holder.name.setText(""+ mDataset.get(position).getServiceName());
        holder.followers.setText(""+ mDataset.get(position).getNoFollowers() + " Followers");
        holder.address.setText(""+ mDataset.get(position).getLocationName());
        if (! mDataset.get(position).getCharges() .equals("null"))
        {
            holder.cash.setText(""+ mDataset.get(position).getCharges());
        }
        holder.likes.setText(""+ mDataset.get(position).getLikes() +" Likes");
        String dist = String.format("%.2f", Double.valueOf(mDataset.get(position).getDistance()));
        holder.distance.setText(""+ dist + " Km");

        if(!mDataset.get(position).getPhotoPath().equals(""))
        {
            Picasso.with(context).load(mDataset.get(position).getPhotoPath()).placeholder(R.drawable.hosp).into(holder.imageView);
        }
        holder.name        .setTypeface(tf);
        holder.followers   .setTypeface(tf);
        holder.address     .setTypeface(tf);
        holder.cash        .setTypeface(tf);
        holder.likes       .setTypeface(tf);
        holder.distance    .setTypeface(tf);
        holder.call        .setTypeface(tf);
        holder.direction   .setTypeface(tf);
        holder.offers      .setTypeface(tf);

        if(mDataset.get(position).getIsChecked())
        {
            holder.chkcompare.setChecked(true);
        }
        else{
            holder.chkcompare.setChecked(false);
        }

        String str_rating = mDataset.get(position).getFinalRating();

        if(str_rating.equals("1"))
        {
            holder.one.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            holder.two.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
            holder.three.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
            holder.four.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
            holder.five.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
        }
        else if(str_rating.equals("2"))
        {
            holder.one.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            holder.two.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            holder.three.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
            holder.four.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
            holder.five.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
        }
        else if(str_rating.equals("3"))
        {
            holder.one.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            holder.two.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            holder.three.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            holder.four.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
            holder.five.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
        }
        else if(str_rating.equals("4"))
        {
            holder.one.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            holder.two.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            holder.three.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            holder.four.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            holder.five.setImageDrawable(context.getResources().getDrawable(R.drawable.outlinestar));
        }
        else if(str_rating.equals("5"))
        {
            holder.one.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            holder.two.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            holder.three.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            holder.four.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
            holder.five.setImageDrawable(context.getResources().getDrawable(R.drawable.goldstar));
        }

        /*holder.chkcompare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    if(SearchListPage.array.size()<=1)
                    {
                        SearchListPage.array.add(""+position);
                        SearchListPage.arrayID.add(""+mDataset.get(position).getHcpUserOid());
                        //holder.body.setBackgroundColor(context.getResources().getColor(R.color.backgroundColor));
                    }
                    else{
                        Toast.makeText(context, "Cannot compare more than 2 items", Toast.LENGTH_SHORT).show();
                        holder.chkcompare.setChecked(false);
                    }
                }
                else{
                    if(SearchListPage.array.size()<=0)
                    {

                    }
                    else{
                        Log.d("Coming","here 1");
                        if(SearchListPage.array.contains(""+position))
                        {
                            int pos = SearchListPage.array.indexOf(""+position);
                            SearchListPage.array.remove(pos);
                            SearchListPage.arrayID.remove(pos);
                            //holder.body.setBackgroundColor(context.getResources().getColor(R.color.white));
                        }
                    }
                }

            }
        });
*/
        holder.chkcompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(holder.chkcompare.isChecked()){
                    if(SearchListPage.array.size()<=1)
                    {
                        SearchListPage.array.add(""+position);
                        SearchListPage.arrayID.add(""+mDataset.get(position).getHcpUserOid());
                        //holder.body.setBackgroundColor(context.getResources().getColor(R.color.backgroundColor));
                    }
                    else{
                        Toast.makeText(context, "Cannot compare more than 2 items", Toast.LENGTH_SHORT).show();
                        holder.chkcompare.setChecked(false);
                    }
                }
                else{
                    Log.d("Coming","here 1");
                    if(SearchListPage.array.contains(""+position))
                    {
                        int pos = SearchListPage.array.indexOf(""+position);
                        SearchListPage.array.remove(pos);
                        SearchListPage.arrayID.remove(pos);
                        //holder.body.setBackgroundColor(context.getResources().getColor(R.color.white));
                    }
                }*/
                if(holder.chkcompare.isChecked()) {
                    if(SearchListPage.array.size()<=1)
                    {
                        mDataset.get(position).setIsChecked(true);
                        SearchListPage.array.add(""+position);
                        SearchListPage.arrayID.add(""+mDataset.get(position).getHsOid());
                        //holder.body.setBackgroundColor(context.getResources().getColor(R.color.backgroundColor));
                    }
                    else{
                        Toast.makeText(context, "Cannot compare more than 2 items", Toast.LENGTH_SHORT).show();
                        holder.chkcompare.setChecked(false);
                    }
                }
                else{
                    mDataset.get(position).setIsChecked(false);
                    if(SearchListPage.array.contains(""+position))
                    {
                        int pos = SearchListPage.array.indexOf(""+position);
                        SearchListPage.array.remove(pos);
                        SearchListPage.arrayID.remove(pos);
                        //holder.body.setBackgroundColor(context.getResources().getColor(R.color.white));
                    }
                }
            }
        });
        holder.body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Coming","here");
                Intent i =  new Intent(context,DetailsPage.class);
                i.putExtra("value",SearchListPage.value);
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
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
