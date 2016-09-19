package com.sriyaan.qureco;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.sriyaan.adapter.RecyclerAdapterDeals;
import com.sriyaan.adapter.RecyclerAdapterSearch;
import com.sriyaan.modal.DetailsData;
import com.sriyaan.modal.ListData;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DealsOffers extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    String json_response;
    JSONArray object;
    ArrayList results;
    private RecyclerView.LayoutManager mLayoutManager1;
    //List of type books this list will store type Book which is our data model
    private List<ListData> data;
    LinearLayout filter;
    SwipeRefreshLayout swipeRefreshLayout;
    Context con;
    String sort_by="";
    TextView sort,filters;
    Toolbar toolbar;
    String fontPath = "fonts/Montserrat-Regular.ttf";
    public static String value;
    // Loading Font Face
    //
    Typeface tf;
    static ArrayList<String> array,arrayID;
    SharedPreferences prefs;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals_offers);
        init();
        prefs = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);
        user_id = prefs.getString("cust_id","");
        value = getIntent().getStringExtra("value");
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
        mTitle.setText("Deals and Offers");
        setFont();
        array       = new ArrayList<>();
        arrayID     = new ArrayList<>();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager1 = new GridLayoutManager(con, 1);
        mRecyclerView.setLayoutManager(mLayoutManager1);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable(){
                                    @Override
                                    public void run(){
                                        runThis();
                                    }
                                }
        );

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DealsOffers.this,Filters.class);
                startActivity(i);
            }
        });
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initSorting();
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }
    public void init()
    {
        con = DealsOffers.this;
        toolbar             = (Toolbar)             findViewById(R.id.toolbar);
        filter              = (LinearLayout)        findViewById(R.id.llfilter);
        mRecyclerView       = (RecyclerView)        findViewById(R.id.my_recycler_view1);
        swipeRefreshLayout  = (SwipeRefreshLayout)  findViewById(R.id.swiperefreshlayout);
        sort                = (TextView)            findViewById(R.id.sort);
        filters             = (TextView)            findViewById(R.id.filters);
    }

    public void setFont()
    {
        sort                .setTypeface(tf);
        filters             .setTypeface(tf);
    }

    @Override
    public void onRefresh() {
        runThis();
    }

    public void runThis()
    {
        Log.d("Coming","Here");
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
                results = new ArrayList<DetailsData>();
                json_response = url_dump.getSearchDeals(user_id,value,sort_by,"1");
                JSONArray array = new JSONArray(json_response);

                object = array.getJSONArray(2);
                for (int i = 0; i < object.length(); i++) {
                    JSONObject jsonObject = object.getJSONObject(i);
                    JSONObject object1 = jsonObject.getJSONObject("deals");

                    String hdo_oid = object1.getString("hdo_oid");
                    String hl_oid = object1.getString("hl_oid");
                    String hcp_user_oid = object1.getString("hcp_user_oid");
                    String hcp_cat_oid = object1.getString("hcp_cat_oid");

                    String offer_caption = object1.getString("offer_caption");
                    String deal_image = object1.getString("deal_image");
                    String from_date = object1.getString("from_date");
                    String to_date = object1.getString("to_date");

                    String offer_flat = object1.getString("offer_flat");
                    String offer_discount = object1.getString("offer_discount");
                    String description = object1.getString("description");
                    String terms_condition = object1.getString("terms_condition");
                    String location_name = object1.getString("location_name");
                    String city = object1.getString("city");
                    String state = object1.getString("state");
                    String geo_lat = object1.getString("geo_lat");
                    String geo_longi = object1.getString("geo_longi");
                    String location_contacts = object1.getString("location_contacts");
                    String final_rating = object1.getString("final_rating");
                    String no_of_followers = object1.getString("no_of_followers");
                    String no_of_likes = object1.getString("no_of_likes");

                    DetailsData data = new DetailsData(hdo_oid,hl_oid,hcp_user_oid,hcp_cat_oid,
                            offer_caption,deal_image,from_date,to_date,offer_flat,offer_discount,
                            description,terms_condition,location_name,city,state,geo_lat,
                            geo_longi,location_contacts,final_rating,
                            no_of_followers,no_of_likes);
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
            swipeRefreshLayout.setRefreshing(false);
            mAdapter = new RecyclerAdapterDeals(results,con);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }
    public void initSorting()
    {
        // Create custom dialog object
        final Dialog dialog = new Dialog(DealsOffers.this);
        // Include dialog.xml file
        dialog.setContentView(R.layout.sorting_dialog);
        // Set dialog title
        dialog.setTitle("Sort By");

        TextView sortprice1 = (TextView) dialog.findViewById(R.id.sortprice1);
        TextView sortprice2 = (TextView) dialog.findViewById(R.id.sortprice2);
        TextView sortdistance1 = (TextView) dialog.findViewById(R.id.sortdistance1);
        TextView sortdistance2 = (TextView) dialog.findViewById(R.id.sortdistance2);
        TextView sortratings1 = (TextView) dialog.findViewById(R.id.sortratings1);
        TextView sortratings2 = (TextView) dialog.findViewById(R.id.sortratings2);

        sortprice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_by = "1";
                runThis();
                dialog.dismiss();
            }
        });
        sortprice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_by = "2";
                runThis();
                dialog.dismiss();
            }
        });
        sortdistance1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_by = "3";
                runThis();
                dialog.dismiss();
            }
        });
        sortdistance2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_by = "4";
                runThis();
                dialog.dismiss();
            }
        });
        sortratings1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_by = "5";
                runThis();
                dialog.dismiss();
            }
        });
        sortratings2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sort_by = "6";
                runThis();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public class RecyclerAdapterDeals extends RecyclerView
            .Adapter<RecyclerAdapterDeals
            .DataObjectHolder> {
        public String LOG_TAG = "MyRecyclerViewAdapter";
        public ArrayList<DetailsData> mDataset;
        Context context;
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
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                    LayoutInflater inflater = getLayoutInflater();
                    View convertView = (View) inflater.inflate(R.layout.deals_layout, null);
                    alertDialog.setView(convertView);
                    TextView tv = (TextView) convertView.findViewById(R.id.text);
                    TextView tvName = (TextView) convertView.findViewById(R.id.Name);
                    TextView tvLimit = (TextView) convertView.findViewById(R.id.Limit);
                    TextView tvOfferPc = (TextView) convertView.findViewById(R.id.OfferPc);
                    TextView tvTnC = (TextView) convertView.findViewById(R.id.TnC);
                    TextView tvDetails = (TextView) convertView.findViewById(R.id.Details);
                    tv.setText("Deals");
                    tvName.setText(mDataset.get(position).getOfferCaption());
                    tvLimit.setText("");
                    tvOfferPc.setText(mDataset.get(position).getOfferDiscount()+" %");
                    tvTnC.setText(mDataset.get(position).getTermsCondition());
                    tvDetails.setText(mDataset.get(position).getDescription());
                    alertDialog.show();
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

    }
}
