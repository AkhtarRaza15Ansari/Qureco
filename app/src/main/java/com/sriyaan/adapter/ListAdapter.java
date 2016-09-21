package com.sriyaan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.sriyaan.modal.DataObject;
import com.sriyaan.qureco.R;

import java.util.List;

/**
 * Created by ansariakhtar on 19/09/16.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private List<DataObject> data;
    private Context con;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView namesub;
        public TextView location;
        public LinearLayout llcall;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            namesub = (TextView) view.findViewById(R.id.clinicaddress);
            location = (TextView) view.findViewById(R.id.address);
            llcall = (LinearLayout) view.findViewById(R.id.llcall);

        }
    }


    public ListAdapter(List<DataObject> data, Context con) {
        this.con = con;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.life_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DataObject item = data.get(position);
        holder.name.setText(item.hcp_cust_blood_group);
        holder.namesub.setText(item.getHcpCustName());
        String dist = String.format("%.2f", Double.valueOf(item.getDistance())) +" KM";
        holder.location.setText(dist);
        holder.llcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contacts = item.getHcpCustMob();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+contacts));
                con.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
