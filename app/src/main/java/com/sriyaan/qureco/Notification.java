package com.sriyaan.qureco;

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
import com.sriyaan.modal.DetailsData;
import com.sriyaan.modal.ListData;
import com.sriyaan.modal.NotificationsData;
import com.sriyaan.util.url_dump;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{
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
    String fontPath = "fonts/Montserrat-Regular.ttf";
    // Loading Font Face
    Typeface tf;
    static ArrayList<String> array,arrayID;
    SharedPreferences prefs;
    String user_id,hcp_id="";
    TextView tvHome, tvNotification, tvChat, tvFavourites, tvAccounts;
    LinearLayout llhome, llnotification, llchat, llfavorites, llacounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        init();

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
        mTitle.setText("Notification");
        setFonts();
        prefs = getSharedPreferences("QurecoOne", Context.MODE_PRIVATE);

        try {
            hcp_id = prefs.getString("hcp_id", "");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        user_id = prefs.getString("cust_id","");

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



        llhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Notification.this, Home.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }
        });
        llnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        llchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Notification.this, Chat.class);
                startActivity(i);
            }
        });
        llfavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Notification.this, Favourite.class);
                startActivity(i);
            }
        });
        llacounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Notification.this, MyAccount.class);
                startActivity(i);
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        url_dump.deleteCache(getApplicationContext());
    }
    public void setFonts() {

        tvHome.setTypeface(tf);
        tvNotification.setTypeface(tf);
        tvChat.setTypeface(tf);
        tvFavourites.setTypeface(tf);
        tvAccounts.setTypeface(tf);
    }
    public void init()
    {
        con = Notification.this;
        mRecyclerView       = (RecyclerView)        findViewById(R.id.my_recycler_view1);
        swipeRefreshLayout  = (SwipeRefreshLayout)  findViewById(R.id.swiperefreshlayout);

        tvHome = (TextView) findViewById(R.id.tvHome);
        tvNotification = (TextView) findViewById(R.id.tvNotification);
        tvChat = (TextView) findViewById(R.id.tvChat);
        tvFavourites = (TextView) findViewById(R.id.tvFavourites);
        tvAccounts = (TextView) findViewById(R.id.tvAccounts);
        llhome = (LinearLayout) findViewById(R.id.homell);
        llnotification = (LinearLayout) findViewById(R.id.notificationll);
        llchat = (LinearLayout) findViewById(R.id.chatll);
        llfavorites = (LinearLayout) findViewById(R.id.favouritesll);
        llacounts = (LinearLayout) findViewById(R.id.accountsll);
    }

    public class RecyclerAdapterNotification extends RecyclerView
            .Adapter<RecyclerAdapterNotification
            .DataObjectHolder> {
        public String LOG_TAG = "MyRecyclerViewAdapter";
        public ArrayList<NotificationsData> mDataset;
        Context context;
        Typeface tf;
        String fontPath = "fonts/Montserrat-Regular.ttf";

        public class DataObjectHolder extends RecyclerView.ViewHolder
                implements View
                .OnClickListener {
            ImageView imageView;
            LinearLayout body;
            TextView msg;
            TextView msg_date;

            public DataObjectHolder(View itemView) {
                super(itemView);
                tf = Typeface.createFromAsset(context.getAssets(), fontPath);

                msg = (TextView) itemView.findViewById(R.id.msg);
                msg_date = (TextView) itemView.findViewById(R.id.msg_date);
                Log.i(LOG_TAG, "Adding Listener");
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

            }
        }



        public RecyclerAdapterNotification(ArrayList<NotificationsData> myDataset, Context context) {
            mDataset = myDataset;
            this.context = context;
        }

        @Override
        public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.notification_item, parent, false);

            DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
            return dataObjectHolder;
        }

        @Override
        public void onBindViewHolder(final DataObjectHolder holder, final int position) {
            holder.msg.setText("" + mDataset.get(position).getMsg());
            holder.msg_date.setText(mDataset.get(position).getMsgDate());

            holder.msg.setTypeface(tf);
            holder.msg_date.setTypeface(tf);
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

        public void addItem(NotificationsData dataObj, int index) {
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
                results = new ArrayList<NotificationsData>();
                json_response = url_dump.getNotification(user_id);
                Log.d("json_response",json_response);
                JSONArray array = new JSONArray(json_response);
                String code = array.getString(0);
                String message = array.getString(1);
                object = array.getJSONArray(2);
                for (int i = 0; i < object.length(); i++) {
                    JSONObject jsonObject = object.getJSONObject(i);

                    String msg = jsonObject.getString("msg");
                    String msg_type = jsonObject.getString("msg_type");
                    String msg_date = jsonObject.getString("msg_date");

                    NotificationsData data = new NotificationsData(msg,msg_type,msg_date);
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
            mAdapter = new RecyclerAdapterNotification(results,con);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }
}
